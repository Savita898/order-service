package com.savita.order.orderresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import com.savita.order.controller.OrderController;
import com.savita.order.dao.OrderEntity;

@Component
public class OrderResourceAssembler implements RepresentationModelAssembler<OrderEntity, OrderResource> {
	
	@Autowired
    private PagedResourcesAssembler<OrderEntity> pagedResourcesAssembler;
	
	   @Override
	    public OrderResource toModel(OrderEntity entity) {
        OrderResource orderResource = new OrderResource();
        orderResource.setOrderId(entity.getOrderId());
        orderResource.setUserId(entity.getUserId());
        orderResource.setItems(entity.getItems());
        orderResource.setTotalAmount(entity.getTotalAmount());
        orderResource.setStatus(entity.getStatus());

        // Self link
        orderResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(entity.getOrderId())).withSelfRel());

        return orderResource;
       }

		public PagedModel<OrderResource> toPagedModel(Page<OrderEntity> orders) {
			
			PagedModel<OrderResource> pagedModel = pagedResourcesAssembler.toModel(orders, this);
			
			int currentPage = orders.getNumber();
	        int totalPages = orders.getTotalPages();
	        int pageSize = orders.getSize();
	        Long userId = orders.getContent().isEmpty() ? null : orders.getContent().get(0).getUserId();
	        
	        //auto getting by springboot
	        /*if (currentPage > 0) {
	            pagedModel.add(linkTo(methodOn(OrderController.class)
	                    .getUserOrders(userId, currentPage - 1, pageSize))
	                    .withRel("previous"));
	        }

	        if (currentPage < totalPages - 1) {
	            pagedModel.add(linkTo(methodOn(OrderController.class)
	                    .getUserOrders(userId, currentPage + 1, pageSize))
	                    .withRel("next"));
	        }*/
			
			return pagedModel;			
		}		    
	  
}
