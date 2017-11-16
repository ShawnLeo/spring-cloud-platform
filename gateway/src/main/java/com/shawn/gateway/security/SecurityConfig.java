package com.shawn.gateway.security;

import com.shawn.gateway.security.crypto.MD5PasswordEncoder;
import com.shawn.gateway.security.web.UnAuthenticationEntryPoint;
import com.shawn.gateway.security.web.access.AccessAuthenticationEntryPoint;
import com.google.common.collect.Lists;
import com.shawn.gateway.security.web.filter.AuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.List;

/**
 * @author Shawn
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UnAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private AccessAuthenticationEntryPoint accessAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                // 认证拦截，response返回
                .and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                // 权限拦截，response返回
                .and().exceptionHandling().accessDeniedHandler(accessAuthenticationEntryPoint)
                // 不需要创建session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 跨域拦截
        // custom-filter顺序详见：
        //  https://docs.spring.io/spring-security/site/docs/4.2.4.BUILD-SNAPSHOT/reference/htmlsingle/#ns-custom-filters
        http.addFilterBefore(corsFilter(), ChannelProcessingFilter.class);

        // 自定义JWT认证拦截器
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // 自定义资源角色拦截
        http.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);

        // disable page caching
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(this.userService); // 数据库验证用户
        auth.authenticationProvider(authenticationProvider()); // MD5加密
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(new MD5PasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor() {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);

        List<AccessDecisionVoter<? extends Object>> decisionVoters = Lists.newArrayList();
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(new RoleVoter());
        filterSecurityInterceptor.setAccessDecisionManager(new AffirmativeBased(decisionVoters));

        return filterSecurityInterceptor;
    }



    @Bean
    public Filter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthenticationTokenFilter();
    }

}
