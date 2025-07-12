package com.yuvraj.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvraj.main.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findbyUserId(Long userId);

}
