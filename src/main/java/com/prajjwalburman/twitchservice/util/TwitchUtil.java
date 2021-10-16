package com.prajjwalburman.twitchservice.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prajjwalburman.twitchservice.client.TwitchClient;
import com.prajjwalburman.twitchservice.vo.AppAccessTokenVO;
import com.prajjwalburman.twitchservice.vo.GameDetailsVO;
import com.prajjwalburman.twitchservice.vo.twitch.AppAccessTokenResponse;
import com.prajjwalburman.twitchservice.vo.twitch.GameDetailsResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TwitchUtil {
	
	@Autowired
	private TwitchClient twitchClient;
	
	private static AppAccessTokenVO appAccessTokenVO;
	
	private static Map<String, String> gamesMap;
	
	@PostConstruct
	private void populateApplicationAccessToken() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		AppAccessTokenResponse appAccessTokenResponse = twitchClient.fetchAppAccessToken();
		
		if(StringUtils.isEmpty(appAccessTokenResponse.getAccess_token())) {
			return;
		}
		appAccessTokenVO = new AppAccessTokenVO();
		appAccessTokenVO.setLastUpdatedDate(dateFormat.format(date));
		appAccessTokenVO.setAppAccessToken(appAccessTokenResponse.getAccess_token());
		appAccessTokenVO.setTimeTillExpiry(appAccessTokenResponse.getExpires_in());
	}
	
	@PostConstruct
	private static void populateGamesMap() {
		gamesMap = new HashMap<>();
	}
	
	public static Map<String, String> getGamesMap() {
		return gamesMap;
	}
	
	public static String addGameFromGameDetailsResponse(GameDetailsResponse gameDetailsResponse, boolean isGameIdRequired) {
		String gameId = null;
		for (GameDetailsVO gameDetailsVO : gameDetailsResponse.getData()) {
			if (StringUtils.isBlank(gamesMap.get(gameDetailsVO.getId()))) {
				gamesMap.put(gameDetailsVO.getId(), gameDetailsVO.getName());
			}
			gameId = (gameDetailsResponse.getData().size() == 1) && isGameIdRequired ? gameDetailsVO.getId() : null;
		}
		
		return gameId;
	}
	
	public String getAppAccessToken() {
		if(StringUtils.isEmpty(appAccessTokenVO.getAppAccessToken()) || isAppAccessTokenRefreshRequired()) {
			populateApplicationAccessToken();
		}
		
		return appAccessTokenVO.getAppAccessToken();
	}

	private boolean isAppAccessTokenRefreshRequired() {
		final String methodName = "isAppAccessTokenRefreshRequired():";
		Date date = new Date();
		try {
			Date lastUpdated = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(appAccessTokenVO.getLastUpdatedDate());
			Date exiryDate = DateUtils.addSeconds(lastUpdated, (appAccessTokenVO.getTimeTillExpiry()-259200));
			
			log.debug("{} Application access token was last update on - [{}], current date is - [{}],  and token will expire on - [{}]",
					methodName, lastUpdated.toString(), date.toString(), exiryDate.toString());

			if (exiryDate.before(date)) {
				return true;
			}
		} catch (ParseException e) {
			return true;
		}
		return false;
	}

}
