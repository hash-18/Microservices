package com.mac.microservices.springcloudconfigserver.configuration;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

	@Configuration
	public class CustomContainer {

	    @Bean
	    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
	        return container -> container.setPort(8888);
	    }
	}