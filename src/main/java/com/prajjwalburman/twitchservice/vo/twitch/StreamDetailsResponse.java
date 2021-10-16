package com.prajjwalburman.twitchservice.vo.twitch;

import java.util.List;

import com.prajjwalburman.twitchservice.vo.PaginationVO;
import com.prajjwalburman.twitchservice.vo.StreamDetailsVO;

import lombok.Data;

@Data
public class StreamDetailsResponse {
	
	List<StreamDetailsVO> data;
	
	PaginationVO pagination;

}
