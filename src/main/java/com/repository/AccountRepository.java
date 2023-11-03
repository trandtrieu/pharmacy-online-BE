package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dto.SignUpDTO;
import com.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByMail(String mail);
    Optional<Account> findByUsernameOrMail(String username, String mail);
    Optional<Account> findByUsername(String username);
    Optional<Account> findById(Long id);
    Boolean existsByUsername(String username);
    Boolean existsByMail(String mail);
	
}
