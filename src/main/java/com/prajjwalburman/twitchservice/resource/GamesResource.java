package com.prajjwalburman.twitchservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prajjwalburman.twitchservice.service.GameService;
import com.prajjwalburman.twitchservice.vo.GameStatsResponseVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/games/v1")
public class GamesResource {
	
	@Autowired
	private GameService gameService;

	@ApiOperation(value = "Fetch Game Stat By Name")
	@GetMapping(value = "/stat/{gameName}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "REQUEST_ID", value = "Unique ID of Request", required = true, dataType = "String", paramType = "header"),
		@ApiImplicitParam(name = "HTTP_AUTH_TOKEN", value = "Secure token to access the API", required = true, dataType = "String", paramType = "header")})
	public GameStatsResponseVO getGameDetails(
			@ApiParam(name = "gameName", value = "gameName", required = true) @PathVariable("gameName") String gameName,
			@ApiParam(name = "isDetailedStatsRequired", value = "isDetailedStatsRequired", required = true) @RequestParam String isDetailedStatsRequired) {
		
		return gameService.getGameStats(gameName.toLowerCase(), Boolean.valueOf(isDetailedStatsRequired));
	}

}
