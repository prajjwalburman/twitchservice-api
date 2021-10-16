package com.prajjwalburman.twitchservice.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TwitchHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		
		//HttpHeaders headers = request.getHeaders();
		long startTime = System.currentTimeMillis();
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(request, response);
		log.info("Response time - {}ms", System.currentTimeMillis() - startTime);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) {
		
		log.info("Incoming rquest: {} {}", request.getMethod(), request.getURI());
		String requestBody = new String(body, StandardCharsets.UTF_8);
		log.debug("========================================= Request Begin =========================================");
		log.debug("Headers:		{}", request.getHeaders());
		log.debug("Request Body:		{}", requestBody);
		log.debug("========================================== Request End ==========================================");
		
	}

	private void traceResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
		
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
		log.info("Response received: {}", response.getStatusCode());
		try {
			int line = bufferedReader.read();
			while(line != -1) {
				stringBuilder.append((char) line);
				line = bufferedReader.read();
			}
		} finally {
			bufferedReader.close();
		}
		
		log.debug("========================================= Response Begin =========================================");
		log.debug("Headers:		{}", request.getHeaders());
		log.debug("URI:		{}", request.getURI().getHost()); 
		log.debug("Response Body:	{}", stringBuilder);
		log.debug("========================================== Response End ==========================================");
		
	}

}
