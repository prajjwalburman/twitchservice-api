package com.prajjwalburman.twitchservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prajjwalburman.twitchservice.service.HealthCheckService;
import com.prajjwalburman.twitchservice.vo.HealthCheckOutputVO;

@RestController
@RequestMapping(value = "/twitch/v1")
public class HealthCheckResource {
	
	@Autowired
	private HealthCheckService healthCheckService;

	@RequestMapping(value = "/healthcheck", method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public HealthCheckOutputVO healthCheck() {
		return healthCheckService.healthcheck();
	}
}
