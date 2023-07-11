package com.mac.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mac.microservices.currencyconversionservice.entity.CurrencyConversion;
import com.mac.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;

import jakarta.websocket.server.PathParam;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	//Modified - To integrate RestTemplate with micrometer to  trace in ZIpkin
	//we create RestTemplate object with Builder
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		HashMap<String, String> uriVariables=new HashMap<>();
		uriVariables.put("from",from);
		uriVariables.put("to",to);
		
		//To make CurrencyExchange called via rest template traceable in Zipkin, instead of
				//directly creating an object, we create object via Spring configuration class and inject 
				// the object using autowiring
		
		//ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8001/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
		ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8001/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);

		CurrencyConversion currencyConversion=responseEntity.getBody();
		
		
		return new CurrencyConversion(currencyConversion.getId(),from, to, quantity, currencyConversion.getConversionMultiple(),quantity.multiply(currencyConversion.getConversionMultiple()),currencyConversion.getEnvironment());
}
	
	//Calling currencyExchange via Feign CLient
	
	@GetMapping("/feign/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		CurrencyConversion currencyConversion= currencyExchangeProxy.retreiveExchangeValue(from, to);
		return new CurrencyConversion(
				currencyConversion.getId(),
				from, to, quantity, 
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()),
				currencyConversion.getEnvironment()+"via Feign");
}

}
