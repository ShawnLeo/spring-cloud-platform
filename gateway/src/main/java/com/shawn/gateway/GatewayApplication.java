package com.shawn.gateway;

import com.shawn.gateway.filter.ParamSetFilter;
import com.shawn.gateway.filter.SendResponsePreFilter;
import org.springframework.boot.SpringApplication;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringCloudApplication
@EnableFeignClients
@EnableZuulProxy
@ComponentScan("com.shawn")
@EnableAuthorizationServer
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


	@Bean
	public ParamSetFilter paramSetFilter() {
		return new ParamSetFilter();
	}

	@Bean
	public SendResponsePreFilter sendResponsePreFilter(){
		return  new SendResponsePreFilter();
	}
}
