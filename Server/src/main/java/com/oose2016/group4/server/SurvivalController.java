package com.oose2016.group4.server;

import static spark.Spark.get;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Setup the web server and handle all requests and responses.
 */
public class SurvivalController {
	private static final String API_CONTEXT = "/v1";

	private SurvivalService survivalService;

	private final Logger logger = LoggerFactory.getLogger(SurvivalController.class);

	public SurvivalController(SurvivalService survivalService) {
		this.survivalService = survivalService;
		setupEndpoints();
	}

	/**
	 * Setup handlers for all request paths.
	 */
	private void setupEndpoints() {
		/**
		 * Retrieve linkIDs to Avoid
		 */
		get(API_CONTEXT + "/avoidLinkIds", "application/json", (request, response) -> {
			try {
				double fromLat = Double.parseDouble(request.queryParams("fromLat"));
				double fromLng = Double.parseDouble(request.queryParams("fromLng"));
				double toLat = Double.parseDouble(request.queryParams("toLat"));
				double toLng = Double.parseDouble(request.queryParams("toLng"));
				Coordinate from = new Coordinate(fromLat, fromLng);
				Coordinate to = new Coordinate(toLat, toLng);
				Coordinate.sortAndExpand(from, to);
				response.status(200);
				return survivalService.getAvoidLinkIds(from, to);
			} catch (Exception e) {
				logger.info("Invalid request", e);
				response.status(400);
				return Collections.EMPTY_MAP;
			}
		}, new JsonTransformer());
		
		/**
		 * Display Heat Map.
		 */
		get(API_CONTEXT + "/heatmap", "application/json", (request, response) -> {
			try {
				double lat = Double.parseDouble(request.queryParams("lat"));
				double lng = Double.parseDouble(request.queryParams("lng"));
				double zoom = Double.parseDouble(request.queryParams("zoom"));
				//generate PNG overlay
				return Collections.EMPTY_MAP;
			} catch (Exception e) {
				logger.info("Invalid request", e);
				response.status(400);
				return Collections.EMPTY_MAP;
			}
		}, new JsonTransformer());
		
	}
}
