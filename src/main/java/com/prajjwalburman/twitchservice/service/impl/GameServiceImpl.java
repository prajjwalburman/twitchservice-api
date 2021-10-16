package com.prajjwalburman.twitchservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.prajjwalburman.twitchservice.client.TwitchClient;
import com.prajjwalburman.twitchservice.client.TwitchLegacyClient;
import com.prajjwalburman.twitchservice.service.GameService;
import com.prajjwalburman.twitchservice.util.GameStatUtil;
import com.prajjwalburman.twitchservice.util.TwitchUtil;
import com.prajjwalburman.twitchservice.vo.GameStatsResponseVO;
import com.prajjwalburman.twitchservice.vo.OtherChannelsGameStatsResponseVO;
import com.prajjwalburman.twitchservice.vo.StreamDetailsVO;
import com.prajjwalburman.twitchservice.vo.TopChannelsGameStatsResponseVO;
import com.prajjwalburman.twitchservice.vo.twitch.GameDetailsResponse;
import com.prajjwalburman.twitchservice.vo.twitch.StreamSummaryResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameServiceImpl implements GameService{
	
	@Autowired
	private TwitchClient twitchClient;
	
	@Autowired
	private TwitchLegacyClient twitchLegacyClient;

	@Override
	public GameStatsResponseVO getGameStats(String gameName, boolean isDetailedStatsRequired) {
		final String methodName = "getGameStats():";
		log.debug("{} started", methodName);
		
		GameDetailsResponse gameDetailsResponse = twitchClient.getGameDetails("name", gameName);
		String gameId = TwitchUtil.addGameFromGameDetailsResponse(gameDetailsResponse, true);
		
		StreamSummaryResponse streamSummaryResponse = twitchLegacyClient.getStreamSumary(gameName);
		int viewersToChannelRatio = GameStatUtil.getViewersToChannelRatio(streamSummaryResponse.getViewers(),
				streamSummaryResponse.getChannels());
		
		if (isDetailedStatsRequired) {
			List<StreamDetailsVO> streamDetailsVOList = twitchClient.getStreams(gameId);
			return buildDetailedGameStatsResponseVO(streamDetailsVOList, streamSummaryResponse, viewersToChannelRatio);
		}
		return new GameStatsResponseVO(streamSummaryResponse.getViewers(), streamSummaryResponse.getChannels(),
				viewersToChannelRatio, null, null);
	}

	
	private GameStatsResponseVO buildDetailedGameStatsResponseVO(List<StreamDetailsVO> streamDetailsVOList,
			StreamSummaryResponse streamSummaryResponse, int viewersToChannelRatio) {

		int channelCountToProcess = (streamSummaryResponse.getChannels() * 20) / 100;
		int topChannelCount = 0;
		int topViewersCount = 0;
		int otherChannelCount = 0;
		int otherViewersCount = 0;
		for (StreamDetailsVO streamDetailsVO : streamDetailsVOList) {
			if (topChannelCount < channelCountToProcess) {
				topViewersCount += streamDetailsVO.getViewer_count();
				topChannelCount++;
			} else {
				otherViewersCount += streamDetailsVO.getViewer_count();
				otherChannelCount++;
			}
		}
		return new GameStatsResponseVO(streamSummaryResponse.getViewers(), streamSummaryResponse.getChannels(),
				viewersToChannelRatio,
				new TopChannelsGameStatsResponseVO(topViewersCount, topChannelCount,
						GameStatUtil.getViewersToChannelRatio(topViewersCount, topChannelCount)),
				new OtherChannelsGameStatsResponseVO(otherViewersCount, otherChannelCount,
						GameStatUtil.getViewersToChannelRatio(otherViewersCount, otherChannelCount)));
	}

}
