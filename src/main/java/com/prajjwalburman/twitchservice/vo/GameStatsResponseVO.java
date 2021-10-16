package com.prajjwalburman.twitchservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class GameStatsResponseVO {
	
	private int viewers;
	
	private int channels;
	
	private int viewersToChannelRatio;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private TopChannelsGameStatsResponseVO topChannelsGameStats;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private OtherChannelsGameStatsResponseVO otherChannelsGameStats;
	
	public GameStatsResponseVO() {
		super();
	}

	public GameStatsResponseVO(int viewers, int channels, int viewersToChannelRatio,
			TopChannelsGameStatsResponseVO topChannelsGameStats,
			OtherChannelsGameStatsResponseVO otherChannelsGameStats) {
		super();
		this.viewers = viewers;
		this.channels = channels;
		this.viewersToChannelRatio = viewersToChannelRatio;
		this.topChannelsGameStats = topChannelsGameStats;
		this.otherChannelsGameStats = otherChannelsGameStats;
	}

}
