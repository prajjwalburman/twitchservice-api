package com.prajjwalburman.twitchservice.util;

public class GameStatUtil {
	
	private GameStatUtil() {
		super();
	}

	public static int getViewersToChannelRatio(int viewers, int channels) {
		return viewers/channels;
	}

}
