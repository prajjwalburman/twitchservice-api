package com.prajjwalburman.twitchservice.vo;

import lombok.Data;

@Data
public class AppAccessTokenVO {
	
	String appAccessToken;
	
	String lastUpdatedDate;
	
	int timeTillExpiry;

}
