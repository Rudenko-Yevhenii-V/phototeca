package com.task.phototeca.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;

@Configuration
public class CoinConfig {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
