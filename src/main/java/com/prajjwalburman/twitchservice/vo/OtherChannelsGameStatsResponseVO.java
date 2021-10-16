package com.prajjwalburman.twitchservice.vo;

import lombok.Data;

@Data
public class OtherChannelsGameStatsResponseVO {
	
	private int viewers;
	
	private int channels;
	
	private int viewersToChannelRatio;
	
	public OtherChannelsGameStatsResponseVO() {
		super();
	}
	
	public OtherChannelsGameStatsResponseVO(int viewers, int channels, int viewersToChannelRatio) {
		super();
		this.viewers = viewers;
		this.channels = channels;
		this.viewersToChannelRatio = viewersToChannelRatio;
	}

}
