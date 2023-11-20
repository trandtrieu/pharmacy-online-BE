package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.OrderInfo;

public interface OrderRepository  extends JpaRepository<OrderInfo, Long> {

}
