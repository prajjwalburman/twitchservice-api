package com.prajjwalburman.twitchservice.vo.twitch;

import lombok.Data;

@Data
public class AppAccessTokenResponse {
	
	String access_token;
	int expires_in;
	String token_type;
	

}
