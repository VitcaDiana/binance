package com.example.binance.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExternalApiConfig {
    @Bean
    //construieste un obict de tip rest template builder cu anumite configurari de baza
    //pentru ca noi sa facem call spre api extern
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
    //un obiect care il construieac din libraria de jakson atunci cand
    //atributele din json bat cu atributele de care am eu nevoie
    //temeperatureFeelsLike, temperature, humidity, data
}