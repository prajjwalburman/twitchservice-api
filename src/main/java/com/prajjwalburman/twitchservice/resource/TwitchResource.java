package com.prajjwalburman.twitchservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajjwalburman.twitchservice.service.TwitchService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/twitch/v1")
public class TwitchResource {
	
	@Autowired
	private TwitchService twitchService;

	@ApiOperation(value = "Fetch Game Info By Id or Name")
	@GetMapping(value = "/game/{idType}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "REQUEST_ID", value = "Unique ID of Request", required = true, dataType = "String", paramType = "header"),
		@ApiImplicitParam(name = "HTTP_AUTH_TOKEN", value = "Secure token to access the API", required = true, dataType = "String", paramType = "header")})
	public void getGameDetails(
			@ApiParam(name = "idType", value = "idType", required = true) @PathVariable("idType") String idType,
			@ApiParam(name = "id", value = "id", required = true) @PathVariable("id") String id) {
		
		twitchService.getGameDetails(idType, id);
	}

}
