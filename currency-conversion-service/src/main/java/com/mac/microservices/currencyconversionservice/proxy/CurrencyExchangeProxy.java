package com.mac.microservices.currencyconversionservice.proxy;

import org.hibernate.annotations.Proxy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mac.microservices.currencyconversionservice.entity.CurrencyConversion;

//name(as set in app.properties of that servuce of the service that is being proxied along with it's domain url
@FeignClient(name="currency-exchange", url="localhost:8001")
//@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retreiveExchangeValue(
			@PathVariable("from") String from,
			@PathVariable("to") String to);


}
