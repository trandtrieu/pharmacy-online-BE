package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(value= "Select p from Product p where p.p_name like %:keyword% order by id desc")
    List<Product> searchProduct(String keyword);

	

    @Query(value = "Select * from Product where product_id = :id and p_price between :min and :max",nativeQuery = true)
    List<Product> getListProductByPriceRange(long id,int min,int max);

}
