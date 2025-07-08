package com.savita.order.orderresource;
import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;


public class OrderResource extends RepresentationModel<OrderResource> {
	
	private Long orderId;
    private Long userId;
    private String items;
    private BigDecimal totalAmount;
    private String status;
    
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}  
	
}
