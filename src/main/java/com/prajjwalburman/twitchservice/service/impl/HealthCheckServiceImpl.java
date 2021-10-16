package com.prajjwalburman.twitchservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.prajjwalburman.twitchservice.service.HealthCheckService;
import com.prajjwalburman.twitchservice.util.TwitchUtil;
import com.prajjwalburman.twitchservice.vo.HealthCheckOutputVO;

public class HealthCheckServiceImpl implements HealthCheckService {

	@Autowired
	TwitchUtil twitchUtil;
	@Override
	public HealthCheckOutputVO healthcheck() {
		HealthCheckOutputVO healthCheckOutputVO = new HealthCheckOutputVO();
		
		healthCheckOutputVO.setReturnCode("0");
		healthCheckOutputVO.setReturnMessage("UP");
		return healthCheckOutputVO;
	}

}
