package com.prajjwalburman.twitchservice.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.prajjwalburman.twitchservice.client.TwitchLegacyClient;
import com.prajjwalburman.twitchservice.vo.twitch.StreamSummaryResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwitchLegacyClientImpl implements TwitchLegacyClient{
	
	@Value("${service.twitch.api}")
	private String twitchApiUrl;
	
	@Value("${twitch.rest.kraken.streamsummary}")
	private String getStreamSummary;
	
	@Value("${twitch.clientid}")
	private String clientId;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public StreamSummaryResponse getStreamSumary(String gameName) {
		final String methodName = "getStreamSumary():";
		long start = System.currentTimeMillis();

		log.info("{} started", methodName);

		StreamSummaryResponse streamSummaryResponse = null;
		try {
			String twitchUrl = new StringBuilder().append(twitchApiUrl).append(getStreamSummary).toString();
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(twitchUrl).queryParam("game", gameName);

			HttpEntity<String> entity = new HttpEntity<>(getAPIHeaders());

			log.debug("{} About to invoke twitch to fetch stream summary for game: [{}]", methodName, gameName);

			ResponseEntity<StreamSummaryResponse> getStreamSumaryResponseEntity = restTemplate
					.exchange(builder.build().toString(), HttpMethod.GET, entity, StreamSummaryResponse.class);

			streamSummaryResponse = getStreamSumaryResponseEntity.getBody();
			log.debug("{} Response from Twitch - [{}]", methodName, streamSummaryResponse);
		} catch (Exception e) {
			log.error("{} Exception occured while fetching stream summary for game: [{}]", methodName, gameName, e);
		} finally {
			log.info("{} Response time: {}\n", methodName, System.currentTimeMillis() - start);
		}
		return streamSummaryResponse;
	}

	@Override
	public void getStreams(String gameName) {
		// TODO Auto-generated method stub
		
	}

	private HttpHeaders getAPIHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Host", "api.twitch.tv");
		httpHeaders.add("Client-Id", clientId);
		httpHeaders.add("Accept", "application/vnd.twitchtv.v5+json");
		return httpHeaders;
	}

}
