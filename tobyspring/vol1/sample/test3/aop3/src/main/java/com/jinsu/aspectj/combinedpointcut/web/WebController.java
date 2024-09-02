package com.jinsu.aspectj.combinedpointcut.web;

import org.springframework.stereotype.Controller;

@Controller
public class WebController {
	public void handleRequest() {
		System.out.println("WebController::handleRequest: Handling web request");
	}

}
