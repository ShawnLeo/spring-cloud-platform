package com.shawn.gateway.security.web.authentication.logout;

import com.shawn.common.RetCode;
import com.shawn.gateway.util.Constants;
import com.shawn.gateway.util.ResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 自定义退出成功事件
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

	private final Log logger = LogFactory.getLog(this.getClass());


	@Value("${jwt.header:xToken}")
	private String tokenName;

	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String xToken = request.getParameter(this.tokenName);
		redisTemplate.opsForHash().delete(Constants.KEY_PORTAL_TOKEN, xToken);
		response.setStatus(HttpStatus.OK.value());
		ResponseUtil.write(response, RetCode.SUCCESS);
	}
}
