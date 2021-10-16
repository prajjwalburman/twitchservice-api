package com.prajjwalburman.twitchservice.client.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.prajjwalburman.twitchservice.client.TwitchClient;
import com.prajjwalburman.twitchservice.util.TwitchUtil;
import com.prajjwalburman.twitchservice.vo.StreamDetailsVO;
import com.prajjwalburman.twitchservice.vo.twitch.AppAccessTokenResponse;
import com.prajjwalburman.twitchservice.vo.twitch.GameDetailsResponse;
import com.prajjwalburman.twitchservice.vo.twitch.StreamDetailsResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwitchClientImpl implements TwitchClient{
	
	@Value("${service.twitch.id}")
	private String twitchIdUrl;
	
	@Value("${service.twitch.api}")
	private String twitchApiUrl;
	
	@Value("${twitch.rest.outh.appaccesstoken}")
	private String appAccessToken;
	
	@Value("${twitch.rest.helix.games}")
	private String getGames;
	
	@Value("${twitch.rest.helix.streams}")
	private String getStreams;
	
	@Value("${twitch.clientid}")
	private String clientId;
	
	@Value("${twitch.clientsecret}")
	private String clientSecret;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TwitchUtil twitchUtil;
	
	@Override
	public AppAccessTokenResponse fetchAppAccessToken() {
		final String methodName = "fetchAppAccessToken():";
		long start = System.currentTimeMillis();
		
		log.info("{} started", methodName);
		AppAccessTokenResponse appAccessTokenResponse = null;
		try {
			String twitchUrl = new StringBuilder().append(twitchIdUrl).append(appAccessToken).toString();
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(twitchUrl)
		            .queryParam("client_id", clientId)
		            .queryParam("client_secret", clientSecret)
		            .queryParam("grant_type", "client_credentials");
			
			HttpEntity<Map<String, String>> entity = new HttpEntity<>(geOuthtHeaders());
			
			log.debug("{} About to invoke twitch to fetch application access token", methodName);
			
			ResponseEntity<AppAccessTokenResponse> appAccessTokenResponseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, AppAccessTokenResponse.class);
			appAccessTokenResponse = appAccessTokenResponseEntity.getBody();
			
			log.debug("{} Response from Twitch - [{}]", methodName, appAccessTokenResponseEntity.getBody());
		} catch (Exception e) {
			log.error("{} Exception occured while fetching application access token", methodName, e);
		} finally {
			log.info("{} Response time: {}\n", methodName, System.currentTimeMillis() - start);
		}
		
		return appAccessTokenResponse;
	}

	@Override
	public GameDetailsResponse getGameDetails(String idType, String id) {
		final String methodName = "getGameDetails():";
		long start = System.currentTimeMillis();
		log.info("{} started", methodName);
		GameDetailsResponse gameDetailsResponse = null;
		try {
			String twitchUrl = new StringBuilder().append(twitchApiUrl).append(getGames).toString();
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(twitchUrl)
		            .queryParam(idType, id);
			
			HttpEntity<String> entity = new HttpEntity<>(getAPIHeaders());
			
			log.debug("{} About to invoke twitch to fetch game details for idType: [{}] and id: [{}]", methodName, idType, id);
			
			ResponseEntity<GameDetailsResponse> gameDetailsResponseEntity = restTemplate.exchange(builder.build().toString(), HttpMethod.GET, entity, GameDetailsResponse.class);
			gameDetailsResponse = gameDetailsResponseEntity.getBody();
			
			log.debug("{} Response from Twitch - [{}]", methodName, gameDetailsResponse);
		} catch (Exception e) {
			log.error("{} Exception occured while fetching game details for idType: [{}] and id: [{}]", methodName, idType, id, e);
		} finally {
			log.info("{} Response time: {}\n", methodName, System.currentTimeMillis() - start);
		}
		return gameDetailsResponse;
	}
	
	@Override
	public List<StreamDetailsVO> getStreams(String gameId) {
		final String methodName = "getStreams():";
		long start = System.currentTimeMillis();
		log.info("{} started", methodName);
		ResponseEntity<StreamDetailsResponse> streamDetailsResponseEntity = null;
		
		List<StreamDetailsVO> streamDetailsVOList = new ArrayList<>();
		
		try {
			String twitchUrl = new StringBuilder().append(twitchApiUrl).append(getStreams).toString();
			HttpEntity<String> entity = new HttpEntity<>(getAPIHeaders());

			log.debug("{} About to invoke twitch to fetch streams for game id: [{}]", methodName, gameId);
			do {
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(twitchUrl).queryParam("game_id", gameId)
						.queryParam("after", getPagination(streamDetailsResponseEntity))
						.queryParam("first", 100);
				streamDetailsResponseEntity = restTemplate.exchange(builder.build().toString(), HttpMethod.GET, entity,
						StreamDetailsResponse.class);
				
				streamDetailsVOList.addAll(streamDetailsResponseEntity.getBody().getData());

			} while (streamDetailsResponseEntity.getBody().getData().size() > 95);

			log.debug("{} Number of streams returned from Twitch - [{}]", methodName,
					streamDetailsResponseEntity.getBody().getData().size());
		} catch (Exception e) {
			log.error("{} Exception occured while fetching streams for game id: [{}]", methodName, gameId, e);
		} finally {
			log.info("{} Response time: {}\n", methodName, System.currentTimeMillis() - start);
		}

		return streamDetailsVOList;
	}
	
	private String getPagination(ResponseEntity<StreamDetailsResponse> streamDetailsResponseEntity) {
		return (null == streamDetailsResponseEntity || null == streamDetailsResponseEntity.getBody()) ? null
				: streamDetailsResponseEntity.getBody().getPagination().getCursor();
	}

	private HttpHeaders geOuthtHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Host", "id.twitch.tv");
		return httpHeaders;
	}

	private HttpHeaders getAPIHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Host", "api.twitch.tv");
		httpHeaders.add("Client-Id", clientId);
		httpHeaders.add("Authorization", new StringBuilder().append("Bearer ").append(twitchUtil.getAppAccessToken()).toString());
		return httpHeaders;
	}
}
