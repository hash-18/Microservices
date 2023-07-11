package com.mac.microservices.limitsservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac.microservices.limitsservices.beans.Limits;
import com.mac.microservices.limitsservices.configuration.Configuration;

@RestController
public class LimitsServiceController {
	
	@Autowired
	private Configuration configuration;
	
	@RequestMapping("/limits")
	public Limits retreiveLimits() {
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
		
	}

}
