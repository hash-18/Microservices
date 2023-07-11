package com.mac.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mac.microservices.currencyexchangeservice.beans.CurrencyExchange;
import com.mac.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	private Logger logger=LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	//Spring inhouse utility interface to extract port from the url
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retreiveExchangeValue(@PathVariable("from") String from,@PathVariable("to") String to)
	{
		
		logger.info("retreiveExchangeValue called with {} to {}",from,to);
		//2023-07-10T10:24:38.171+05:30 INFO [currency-exchange,457f935a21f90aa33ffbe6ce62dcd4ce,ff9b05cd360df8de] #SB3 23600 --- [nio-8001-exec-1] c.m.m.c.c.CurrencyExchangeController     : retreiveExchangeValue called with INR to USD
//These two ids are v important and important to trace the request across multiple microservices.
//These ids are provided by microservices.
		
		//Sending hard coded value
		//CurrencyExchange currencyExchange = new CurrencyExchange(1000L,from,to,BigDecimal.valueOf(50));
		
		//Extracting currency value from Repository
		
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		if(currencyExchange==null)
			throw new RuntimeException("Value not found for given currencies");
		//Extracting the port dynamically
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
		
	}
}
