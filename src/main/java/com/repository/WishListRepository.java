package com.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Account;
import com.model.Product;
import com.model.WishList;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    WishList findByAccountAndProducts(Account account, Product product);
    WishList findByAccount_Id(Long accountId);
    Set<WishList> findByProducts(Product product);
}



