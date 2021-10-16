package com.prajjwalburman.twitchservice.client;

import com.prajjwalburman.twitchservice.vo.twitch.StreamSummaryResponse;

/**
 * @author prajjwal
 *
 */
public interface TwitchLegacyClient {
	
	/**
	 * Method to fetch streams by game name
	 * 
	 * @param gameName
	 */
	public void getStreams(String gameName);
	
	/**
	 * Method to fetch streams summary
	 * 
	 * @param gameName
	 * @return
	 */
	public StreamSummaryResponse getStreamSumary(String gameName);

}
