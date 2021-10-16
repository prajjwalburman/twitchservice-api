package com.prajjwalburman.twitchservice.service;

import com.prajjwalburman.twitchservice.vo.GameStatsResponseVO;

/**
 * @author prajjwal
 *
 */
public interface GameService {
	
	/**
	 * Method to fetch game streaming stats
	 * 
	 * @param gameName
	 */
	public GameStatsResponseVO getGameStats(String gameName, boolean isDetailedStatsRequired);

}
