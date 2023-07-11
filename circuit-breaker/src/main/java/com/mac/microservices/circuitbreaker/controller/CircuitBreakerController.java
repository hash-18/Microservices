package com.mac.microservices.circuitbreaker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	
	private Logger logger=LoggerFactory.getLogger(CircuitBreakerController.class);
	@GetMapping("/sample-api")
	//@Retry(name = "default")  //Using deafault re-tries 3 times upon failure
	@Retry(name="sampleApiRetryCount", fallbackMethod = "hardCodedResponse") // retries as specified in application.properties
	//@CircuitBreaker(name="sampleApiRetryCount", fallbackMethod = "hardCodedResponse") // retries as specified in application.properties
	//@Ratelimiter(name="default")
	//Bulkhead(name="sample-api")
	public String sampleApi()
	{	
		logger.info("Hitting /sample-api");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
		return forEntity.getBody();
	}
	
	//This method will be executed as a fallback if all the retries fail
	//Must accept exception ex as a parameter
	public String hardCodedResponse(Exception ex)
	{
		return "executed as a fallback as all the retries failed";
	}

}
