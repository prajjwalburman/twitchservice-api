package com.prajjwalburman.twitchservice.vo.twitch;

import java.util.List;

import com.prajjwalburman.twitchservice.vo.GameDetailsVO;

import lombok.Data;

@Data
public class GameDetailsResponse {
	
	private List<GameDetailsVO> data;

}
