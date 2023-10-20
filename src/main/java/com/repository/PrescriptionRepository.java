package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

	
	@Query("SELECT p FROM Prescription p WHERE p.account.id = :account_id")
    List<Prescription> findPresciprionByAccountId(@Param("account_id") Long account_id);	
}
