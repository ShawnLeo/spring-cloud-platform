package com.shawn.gateway.security.web.authentication.rememberme;

import com.google.common.collect.Lists;
import com.shawn.common.RetCode;
import com.shawn.gateway.util.ResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MyRememberMeServices implements RememberMeServices {

	protected final Log logger = LogFactory.getLog(getClass());

	public static final String DEFAULT_PARAMETER = "remember";
	public static final int TWO_WEEKS_S = 1209600;

	private static final String DELIMITER = ":";

	private String parameter = DEFAULT_PARAMETER;
	private boolean alwaysRemember;
	private String key;

	private UserDetailsService userDetailsService;

	private int tokenValiditySeconds = TWO_WEEKS_S;

//	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
//	private GrantedAuthoritiesMapper roleAuthoritiesMapper = new RoleHierarchyAuthoritiesMapper();
	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

	public MyRememberMeServices(String key, UserDetailsService userDetailsService) {
		this.key = key;
		this.userDetailsService = userDetailsService;
	}

	public void setAlwaysRemember(boolean alwaysRemember) {
		this.alwaysRemember = alwaysRemember;
	}

	public void setParameter(String parameter) {
		Assert.hasText(parameter, "Parameter name cannot be empty or null");
		this.parameter = parameter;
	}

	public String getParameter() {
		return parameter;
	}

	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public String getKey() {
		return key;
	}

	public void setTokenValiditySeconds(int tokenValiditySeconds) {
		this.tokenValiditySeconds = tokenValiditySeconds;
	}

	protected int getTokenValiditySeconds() {
		return tokenValiditySeconds;
	}

	@Override
	public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
		String rememberKey = request.getParameter("rememberKey");
		if (StringUtils.isEmpty(rememberKey)) {
			return null;
		}
		logger.debug("发现rememberKey");

		try {
			// 解密 rememberKey
			String[] rememberTokens = decodeRememberKey(rememberKey);
			UserDetails user = processAutoLogin(rememberTokens, request, response);

			userDetailsChecker.check(user);

			logger.debug("记住密码登陆");

			return createSuccessfulAuthentication(request, user);
		} catch (CookieTheftException cte) {
			throw cte;
		} catch (UsernameNotFoundException noUser) {
			logger.debug("Remember-me login was valid but corresponding user not found.",
					noUser);
		} catch (InvalidCookieException invalidCookie) {
			logger.debug("Invalid remember-me cookie: " + invalidCookie.getMessage());
		} catch (AccountStatusException statusInvalid) {
			logger.debug("Invalid UserDetails: " + statusInvalid.getMessage());
		} catch (RememberMeAuthenticationException e) {
			logger.debug(e.getMessage());
		}
		return null;
	}
	protected Authentication createSuccessfulAuthentication(HttpServletRequest request,
                                                            UserDetails user) {

		List<GrantedAuthority> authorities = Lists.newArrayList();
		if (user.getAuthorities() != null) {
			user.getAuthorities().forEach((roleName) -> {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
			});
		}
		RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(key, user, authorities);
		auth.setDetails(authenticationDetailsSource.buildDetails(request));
		return auth;
	}

	private UserDetails processAutoLogin(String[] rememberTokens, HttpServletRequest request, HttpServletResponse response) {
		if (rememberTokens.length != 3) {
			throw new InvalidCookieException("rememberKey token 长度不为3 '" + Arrays.asList(rememberTokens) + "'");
		}

		long tokenExpiryTime;

		try {
			tokenExpiryTime = new Long(rememberTokens[1]).longValue();
		}
		catch (NumberFormatException nfe) {
			throw new InvalidCookieException(
					"过期时长类型无效");
		}

		if (isTokenExpired(tokenExpiryTime)) {
			throw new InvalidCookieException("Cookie token[1] has expired (expired on '"
					+ new Date(tokenExpiryTime) + "'; current time is '" + new Date()
					+ "')");
		}

		UserDetails userDetails = getUserDetailsService().loadUserByUsername(
				rememberTokens[0]);

		String expectedTokenSignature = makeTokenSignature(tokenExpiryTime,
				userDetails.getUsername(), userDetails.getPassword());

		if (!equals(expectedTokenSignature, rememberTokens[2])) {
			throw new InvalidCookieException("Cookie token[2] contained signature '"
					+ rememberTokens[2] + "' but expected '" + expectedTokenSignature + "'");
		}

		return userDetails;
	}

	protected boolean isTokenExpired(long tokenExpiryTime) {
		return tokenExpiryTime < System.currentTimeMillis();
	}

	protected String[] decodeRememberKey(String rememberKey) throws InvalidCookieException {
		for (int j = 0; j < rememberKey.length() % 4; j++) {
			rememberKey = rememberKey + "=";
		}

		if (!Base64.isBase64(rememberKey.getBytes())) {
			throw new InvalidCookieException(
					"rememberKey 不是 Base64 加密类型; rememberKey值为：'" + rememberKey + "'");
		}

		String rememberKeyAsPlainText = new String(Base64.decode(rememberKey.getBytes()));

		String[] tokens = StringUtils.delimitedListToStringArray(rememberKeyAsPlainText,
				DELIMITER);

		if ((tokens[0].equalsIgnoreCase("http") || tokens[0].equalsIgnoreCase("https"))
				&& tokens[1].startsWith("//")) {
			// Assume we've accidentally split a URL (OpenID identifier)
			String[] newTokens = new String[tokens.length - 1];
			newTokens[0] = tokens[0] + ":" + tokens[1];
			System.arraycopy(tokens, 2, newTokens, 1, newTokens.length - 1);
			tokens = newTokens;
		}

		return tokens;
	}

	@Override
	public void loginFail(HttpServletRequest request, HttpServletResponse response) {
		ResponseUtil.write(response, RetCode.REMEMBERMELOGIN);
	}

	@Override
	public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
		if (!rememberMeRequested(request, parameter)) {
			logger.debug("Remember-me login not requested.");
			return;
		}

		onLoginSuccess(request, response, successfulAuthentication);
	}

	private void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
		String username = retrieveUserName(successfulAuthentication);
		String password = getUserDetailsService().loadUserByUsername(username).getPassword();

		if (!StringUtils.hasLength(username)) {
			logger.debug("Unable to retrieve username");
			return;
		}

		int tokenLifetime = calculateLoginLifetime(request, successfulAuthentication);
		long expiryTime = System.currentTimeMillis();
		// SEC-949
		expiryTime += 1000L * (tokenLifetime < 0 ? TWO_WEEKS_S : tokenLifetime);

		String signatureValue = makeTokenSignature(expiryTime, username, password);

		String encodeRememberKey = encodeRememberKey(new String[] { username, Long.toString(expiryTime), signatureValue });

		request.setAttribute("rememberKey", encodeRememberKey);
		request.setAttribute("rememberMaxAge", tokenLifetime);

	}

	protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
		if (alwaysRemember) {
			return true;
		}

		String paramValue = request.getParameter(parameter);

		if (paramValue != null) {
			if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
					|| paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Did not send remember-me cookie (principal did not set parameter '"
					+ parameter + "')");
		}

		return false;
	}

	protected String retrieveUserName(Authentication authentication) {
		if (isInstanceOfUserDetails(authentication)) {
			return ((UserDetails) authentication.getPrincipal()).getUsername();
		}
		else {
			return authentication.getPrincipal().toString();
		}
	}

	protected String retrievePassword(Authentication authentication) {
		if (isInstanceOfUserDetails(authentication)) {
			return ((UserDetails) authentication.getPrincipal()).getPassword();
		}
		else {
			if (authentication.getCredentials() == null) {
				return null;
			}
			return authentication.getCredentials().toString();
		}
	}

	private boolean isInstanceOfUserDetails(Authentication authentication) {
		return authentication.getPrincipal() instanceof UserDetails;
	}

	protected int calculateLoginLifetime(HttpServletRequest request,
										 Authentication authentication) {
		return getTokenValiditySeconds();
	}

	protected String makeTokenSignature(long tokenExpiryTime, String username,
										String password) {
		String data = username + ":" + tokenExpiryTime + ":" + password + ":" + getKey();
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encode(digest.digest(data.getBytes())));
	}

	private static boolean equals(String expected, String actual) {
		byte[] expectedBytes = bytesUtf8(expected);
		byte[] actualBytes = bytesUtf8(actual);
		if (expectedBytes.length != actualBytes.length) {
			return false;
		}

		int result = 0;
		for (int i = 0; i < expectedBytes.length; i++) {
			result |= expectedBytes[i] ^ actualBytes[i];
		}
		return result == 0;
	}

	private static byte[] bytesUtf8(String s) {
		if (s == null) {
			return null;
		}
		return Utf8.encode(s);
	}


	protected String encodeRememberKey(String[] rememberTokens) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rememberTokens.length; i++) {
			sb.append(rememberTokens[i]);

			if (i < rememberTokens.length - 1) {
				sb.append(DELIMITER);
			}
		}

		String value = sb.toString();

		sb = new StringBuilder(new String(Base64.encode(value.getBytes())));

		while (sb.charAt(sb.length() - 1) == '=') {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}
}
