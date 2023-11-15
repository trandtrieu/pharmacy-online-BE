package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.DeliveryAddress;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer>{
	 List<DeliveryAddress> findAllByAccount_Id(Long userId);
	 List<DeliveryAddress> findByAccountId(Long userId);
}
