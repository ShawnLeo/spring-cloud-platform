package com.shawn.gateway.security.core;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.shawn.common.Response;
import com.shawn.common.RetCode;
import com.shawn.gateway.util.JwtTokenUtil;
import com.shawn.gateway.util.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * Created by Shawn on 2017/8/15.
 */
@RestController
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RememberMeServices rememberMeServices;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Response login(@RequestParam String username, @RequestParam String password) {
        logger.info("用户登陆：{}",username);
        Response resp = new Response();
        Authentication token = new UsernamePasswordAuthenticationToken(username, password);
        authentication(token, resp);
        logger.info("用户登陆完成，返回码：{}。返回消息；{}",
                resp.getHeader().getCode(),resp.getHeader().getMessage());

        return resp;
    }


    @RequestMapping(value = {"/app/login"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Response appLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam String username,
                             @RequestParam String password, @RequestParam(name = "remember", defaultValue = "false") boolean remember) {
        logger.info("用户登陆：{},remember:{}",username, remember);
        Response resp = new Response();
        Authentication token = new UsernamePasswordAuthenticationToken(username, password);

        authentication(token, resp);

        // 记住密码
        if (RetCode.SUCCESS.getCode().equals(resp.getHeader().getCode()) && remember){

            rememberMeServices.loginSuccess(request, response, token);

            String jwtToken = (String) resp.getBody();
            Map map = Maps.newHashMap();
            map.put("sessionId", jwtToken);
            map.put("rememberKey", request.getAttribute("rememberKey"));
            map.put("rememberMaxAge", request.getAttribute("rememberMaxAge"));
            resp.setBody(map);
        }

        logger.info("用户登陆完成，返回码：{}。返回消息；{}",
                resp.getHeader().getCode(),resp.getHeader().getMessage());

        return resp;
    }

    private void authentication(Authentication token, Response resp){

        try {

            token = authenticationManager.authenticate(token); // 登录认证
            SecurityContextHolder.getContext().setAuthentication(token);
            // 登陆成功
            resp.getHeader().setCode(RetCode.SUCCESS.getCode());
            resp.getHeader().setMessage(RetCode.SUCCESS.getMessage());

            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            Set<String> roleNames = Sets.newHashSet();

            userDetails.getAuthorities().forEach((authorities)-> {
                roleNames.add(authorities.getAuthority());
            }) ;

            // 返回jwt token
            UserSession session = new UserSession();
            session.setId(userDetails.getId());
            session.setRoleNames(roleNames);
            session.setMobile(userDetails.getMobile());
            session.setLoginName(userDetails.getUsername());
            session.setDisabled(!userDetails.isEnabled());
            resp.setBody(jwtTokenUtil.generate(session));
        } catch (UsernameNotFoundException ex) {
            resp.getHeader().setCode(RetCode.USERORPWDERR.getCode());
            resp.getHeader().setMessage(RetCode.USERORPWDERR.getMessage());
        } catch (LockedException e) {
            resp.getHeader().setCode(RetCode.USERLOCKED.getCode());
            resp.getHeader().setMessage(RetCode.USERLOCKED.getMessage());
        } catch (BadCredentialsException e) {
            resp.getHeader().setCode(RetCode.USERORPWDERR.getCode());
            resp.getHeader().setMessage(RetCode.USERORPWDERR.getMessage());
        } catch (AuthenticationException e) {
            resp.getHeader().setCode(RetCode.INTERNALEXCEP.getCode());
            resp.getHeader().setMessage(e.getMessage());
        }

    }
}
