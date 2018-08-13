package com.shawn.gateway.security.web.filter;

import com.shawn.gateway.security.core.JwtUserDetails;
import com.google.common.collect.Lists;
import com.shawn.common.RetCode;
import com.shawn.gateway.util.Constants;
import com.shawn.gateway.util.JwtTokenUtil;
import com.shawn.gateway.util.ResponseUtil;
import com.shawn.gateway.util.UserSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 解析 JWT Token
 * <p>
 * 认证用户，并设置 security context
 * <p>
 * Created by Shawn on 2017/8/15.
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header:xToken}")
    private String tokenName;

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String xToken = request.getParameter(this.tokenName);

        logger.info("JWT Token : " + xToken);

        UserSession session = jwtTokenUtil.parser(xToken);

        if (session.getLoginName() != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && refreshSession(xToken)) { // 解析成功

            // session -> userDetails
            List<GrantedAuthority> authorities = Lists.newArrayList();
            List<GrantedAuthority> userAuthorities = Lists.newArrayList();
            if (session.getRoleNames() != null) {
                session.getRoleNames().forEach((roleName) -> {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
                    userAuthorities.add(new SimpleGrantedAuthority(roleName));
                });
            }

            if(session.isDisabled()){
                ResponseUtil.write(response, RetCode.USERLOCKED);
                return;
            }

            JwtUserDetails userDetails = new JwtUserDetails(session.getId(), session.getLoginName(),session.getMobile(), null,
                    userAuthorities, session.isDisabled());
            userDetails.setMobile(session.getMobile());

            // 认证用户，并设置 security context
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            logger.info("认证用户：" + session.getLoginName() + ", 设置 security context。");
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        chain.doFilter(request, response);
    }

    private boolean refreshSession(String xToken) {

        // portal 门户
        if (redisTemplate.opsForHash().hasKey(Constants.KEY_PORTAL_TOKEN, xToken)) {
            redisTemplate.expire(Constants.KEY_PORTAL_TOKEN, Constants.TIME_KEY_PORTAL_TOKEN, TimeUnit.MINUTES);
            return true;
        }

        return false;
    }
}