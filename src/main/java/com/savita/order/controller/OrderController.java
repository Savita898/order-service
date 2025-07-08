package com.savita.order.controller;

//import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.data.web.PagedModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.savita.order.dao.OrderEntity;
import com.savita.order.dao.OrderRepository;
import com.savita.order.exception.OrderNotFoundException;
import com.savita.order.model.OrderRequest;
import com.savita.order.orderresource.OrderResource;
import com.savita.order.orderresource.OrderResourceAssembler;
import com.savita.order.service.OrderService;






@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
    private OrderResourceAssembler orderResourceAssembler;
	
	@PostMapping("/createOrder")
	public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest)
	{
		try
		{
		  String orderId = orderService.createOrder(orderRequest);
          return ResponseEntity.ok("Order created successfully with ID: " + orderId);
		}
		catch(RuntimeException e)
		{
			//for making change
		  return ResponseEntity.badRequest().body("Failed to create order: " + e.getMessage());
		}
	}
	
	@GetMapping("/{orderId}")
    public OrderResource getOrderById(@PathVariable Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return orderResourceAssembler.toModel(orderEntity);
    }
	
	@GetMapping("/user/{userId}")
	public PagedModel<OrderResource> getUserOrders(@PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		Page<OrderEntity> orders = orderRepository.findByUserId(userId, pageable);
		return orderResourceAssembler.toPagedModel(orders);		
	}
	
}
