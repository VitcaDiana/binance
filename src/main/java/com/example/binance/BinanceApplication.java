package com.example.binance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//Please create a SpringBoot application which will make two API calls to Binance and will store the results in two database tables:
//
//GET URL's:
//https://testnet.binancefuture.com/fapi/v1/ticker/bookTicker
//https://api.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1m&limit=1
//DB tables:
//Symbol - save the result from the first GET
//Klines - save the result from the second GET and save the id of the Symbol
@SpringBootApplication
public class BinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinanceApplication.class, args);
	}

}
