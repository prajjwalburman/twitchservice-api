package com.prajjwalburman.twitchservice.client;

import java.util.List;

import com.prajjwalburman.twitchservice.vo.StreamDetailsVO;
import com.prajjwalburman.twitchservice.vo.twitch.AppAccessTokenResponse;
import com.prajjwalburman.twitchservice.vo.twitch.GameDetailsResponse;

/**
 * @author prajjwal
 *
 */
public interface TwitchClient {
	
	/**
	 * Method to fetch Application Access Token
	 * 
	 * @return
	 */
	public AppAccessTokenResponse fetchAppAccessToken();
	
	
	/**
	 * Method to fetch game details
	 * 
	 * @param idType
	 * @param id
	 * @return
	 */
	public GameDetailsResponse getGameDetails(String idType, String id);
	
	/**
	 * Method to fetch streams by game id
	 * 
	 * @param gameId
	 * @return 
	 */
	public List<StreamDetailsVO> getStreams(String gameId);

}
