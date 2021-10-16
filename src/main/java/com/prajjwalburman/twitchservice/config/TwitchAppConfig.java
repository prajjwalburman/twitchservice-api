package com.prajjwalburman.twitchservice.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.prajjwalburman.twitchservice.client.TwitchClient;
import com.prajjwalburman.twitchservice.client.TwitchLegacyClient;
import com.prajjwalburman.twitchservice.client.impl.TwitchClientImpl;
import com.prajjwalburman.twitchservice.client.impl.TwitchLegacyClientImpl;
import com.prajjwalburman.twitchservice.interceptor.TwitchHttpRequestInterceptor;
import com.prajjwalburman.twitchservice.service.GameService;
import com.prajjwalburman.twitchservice.service.HealthCheckService;
import com.prajjwalburman.twitchservice.service.TwitchService;
import com.prajjwalburman.twitchservice.service.impl.GameServiceImpl;
import com.prajjwalburman.twitchservice.service.impl.HealthCheckServiceImpl;
import com.prajjwalburman.twitchservice.service.impl.TwitchServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ComponentScan(basePackages = {"com.prajjwalburman.twitchservice"})
@Slf4j
public class TwitchAppConfig {

	@Bean
	public HealthCheckService healthCheckService() {
		return new HealthCheckServiceImpl();
	}
	
	@Bean
	public TwitchService twitchService() {
		return new TwitchServiceImpl();
	}
	
	@Bean
	public GameService GameService() {
		return new GameServiceImpl();
	}
	
	@Bean
	public TwitchClient twitchClient() {
		return new TwitchClientImpl();
	}
	
	@Bean
	public TwitchLegacyClient twitchLegacyClient() {
		return new TwitchLegacyClientImpl();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return getRestTemplate();
	}
	
	private RestTemplate getRestTemplate() {
		String methodName = "getRestTemplate():";
		log.debug("{} Injecting Rest Teamplate", methodName);
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(clientHttpRequestFactory()));
		
		//setting intercepter
		restTemplate.setInterceptors(Collections.singletonList(restHttpIterceptor()));
		
		List<HttpMessageConverter<?>> messageConvertors = new ArrayList<>();
		messageConvertors.add(new StringHttpMessageConverter());
		messageConvertors.add(new FormHttpMessageConverter());
		messageConvertors.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(messageConvertors);
		return restTemplate;
	}

	protected ClientHttpRequestInterceptor restHttpIterceptor() {
		return new TwitchHttpRequestInterceptor();
	}

	protected ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(1000);
		factory.setConnectTimeout(1000);
		return factory;
	}
}
