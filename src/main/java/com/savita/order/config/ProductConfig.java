package com.savita.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "product.service")
public class ProductConfig {
	
	private String checkStockUrl;
	private String reduceStockUrl;
	
	public String getCheckStockUrl() {
		return checkStockUrl;
	}
	public void setCheckStockUrl(String checkStockUrl) {
		this.checkStockUrl = checkStockUrl;
	}
	
	public void setReduceStockUrl(String reduceStockUrl) {
		this.reduceStockUrl = reduceStockUrl;
	}
	public String getReduceStockUrl() {
		// TODO Auto-generated method stub
		return reduceStockUrl;
	}
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	

}
