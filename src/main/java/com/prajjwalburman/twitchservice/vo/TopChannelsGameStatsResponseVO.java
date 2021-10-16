package com.prajjwalburman.twitchservice.vo;

import lombok.Data;

@Data
public class TopChannelsGameStatsResponseVO {
	
	private int viewers;
	
	private int channels;
	
	private int viewersToChannelRatio;
	
	public TopChannelsGameStatsResponseVO() {
		super();
	}
	
	public TopChannelsGameStatsResponseVO(int viewers, int channels, int viewersToChannelRatio) {
		super();
		this.viewers = viewers;
		this.channels = channels;
		this.viewersToChannelRatio = viewersToChannelRatio;
	}
}
