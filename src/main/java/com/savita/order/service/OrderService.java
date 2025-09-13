package com.savita.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.savita.order.config.ProductConfig;
import com.savita.order.dao.OrderEntity;
import com.savita.order.dao.OrderRepository;
import com.savita.order.model.OrderRequest;
import com.savita.order.model.OrderRequest.OrderItem;



@Service
public class OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductConfig productConfig;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${user.service.url}")
	private String userServiceUrl;
	
	//private static final String USER_SERVICE_URL = "http://localhost:8088/users/{id}"; 
	//private static final String PRODUCT_SERVICE_URL ="http://localhost:8090/product/{id}/check-stock?requiredQty={requiredQty}";
	//private static final String PRODUCT_REDUCE_SERVICE_URL ="http://localhost:8090/product/{id}/reduce-stock?requiredQty={requiredQty}";
	
	public String createOrder(OrderRequest orderRequest)
	{
		logger.trace("Input Request came "+orderRequest);//internal dev level logs if every step we want to log
		boolean isUserValid = validateUser(orderRequest.getUserId());
		System.out.println("change");
		if (!isUserValid) {
			logger.error("Invalid user found "+orderRequest.getUserId());//something broke.
            throw new RuntimeException("User not valid!");
        }
		
		logger.debug("User validation done");//internal dev logs only
		
		for (OrderItem item : orderRequest.getItems()) {
            boolean isStockAvailable = checkStockAvailability(item.getProductId(), item.getQuantity());
            if (!isStockAvailable) {
                throw new RuntimeException("Insufficient stock for product ID: " + item.getProductId());
            }
        }
		
		
		String productIds = orderRequest.getItems().stream()
                .map(item -> String.valueOf(item.getProductId()))  // Convert productId to string
                .collect(Collectors.joining(","));
		System.out.print("Value captured are "+productIds);
		logger.info("Creating order for userId: {} with products: {}", orderRequest.getUserId(), productIds);//Normal status update
		
		OrderEntity order = new OrderEntity();		
		order.setUserId(orderRequest.getUserId());
		order.setItems(productIds);
		order.setStatus(orderRequest.getStatus());
		order.setTotalAmount(orderRequest.getTotalAmount());
		
		OrderEntity savedOrder = orderRepository.save(order);
		
		for (OrderItem item : orderRequest.getItems()) {
	        boolean stockReduced = reduceStock(item.getProductId(), item.getQuantity());
	        if (!stockReduced) {
	        	logger.warn("Insufficient stock for product ID: {}", item.getProductId());
	            orderRepository.deleteById(savedOrder.getOrderId());
	            throw new RuntimeException("Stock changed, please retry. Order rollback done.");
	        }
	    }
		
		return String.valueOf(savedOrder.getOrderId());
	}
	
	private boolean validateUser(long userId)
	{
		try
		{
			ResponseEntity<String> resp = restTemplate.getForEntity(userServiceUrl, String.class, userId);
			System.out.print(resp);
			return resp.getStatusCode().is2xxSuccessful();
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	private boolean checkStockAvailability(long productId, int requiredQty) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(productConfig.getCheckStockUrl(), String.class, productId, requiredQty);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
	
	private boolean reduceStock(long productId, int requiredQty) {
        try {
            restTemplate.put(productConfig.getReduceStockUrl(), String.class, productId, requiredQty);
            return true;
        } catch (Exception e) {
        	System.out.println("Exception is "+e);
            return false;
        }
    }
}
