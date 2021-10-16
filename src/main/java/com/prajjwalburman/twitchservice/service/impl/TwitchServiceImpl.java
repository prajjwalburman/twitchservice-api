package com.prajjwalburman.twitchservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.prajjwalburman.twitchservice.client.TwitchClient;
import com.prajjwalburman.twitchservice.client.TwitchLegacyClient;
import com.prajjwalburman.twitchservice.service.TwitchService;

public class TwitchServiceImpl implements TwitchService{
	
	@Autowired
	private TwitchClient twitchClient;
	
	@Autowired
	private TwitchLegacyClient twitchLegacyClient;
	
	@Override
	public void getGameDetails(String idType, String id) {

		twitchClient.getGameDetails(idType, id);
		
		twitchLegacyClient.getStreamSumary("world of warships".toLowerCase());
	}

}
