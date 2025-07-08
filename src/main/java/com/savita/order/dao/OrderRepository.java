package com.savita.order.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

	Page<OrderEntity> findByUserId(Long userId, Pageable pageable);

}
