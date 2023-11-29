package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.OrderInfo;

public interface OrderRepository  extends JpaRepository<OrderInfo, Long> {
    List<OrderInfo> findByAccountIdAndStatus(String accountId, String status);

}
