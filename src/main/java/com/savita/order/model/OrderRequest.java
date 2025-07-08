package com.savita.order.model;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequest {
	private Long userId;
	 private List<OrderItem> items; 
    private BigDecimal totalAmount;
    private String status;
    
    public OrderRequest(Long userId, List<OrderItem> items, BigDecimal totalAmount, String status) {
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    
    public static class OrderItem {
        private Long productId;  // ID of the product
        private Integer quantity;  // Quantity of the product in the order

        // Getters and Setters
        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
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
