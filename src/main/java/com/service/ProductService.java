package com.service;

import java.math.BigDecimal;
import java.util.List;

import com.model.Product;

public interface ProductService {
	List<Product> searchProduct(String keyword);
	
	List<Product> getListByRange(long id, int min, int max);

//	Long countByRange(BigDecimal minPrice, BigDecimal maxPrice);
//	
//	Long countProductsByKeywordAndPriceRange(String keyword, BigDecimal minPrice, BigDecimal maxPrice);
	       
	
}
