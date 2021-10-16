package com.prajjwalburman.twitchservice.vo;

import java.util.List;

import lombok.Data;

@Data
public class StreamDetailsVO {
	
	String id;
	
	String user_id;
	
	String user_name;
	
	String game_id;
	
	String game_name;
	
	String type;
	
	String title;
	
	int viewer_count;
	
	String started_at;
	
	String language;
	
	String thumbnail_url;
	
	List<String> tag_ids;
}