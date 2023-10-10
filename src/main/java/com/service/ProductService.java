package com.service;

import java.util.List;

import com.model.Product;

public interface ProductService {
	List<Product> searchProduct(String keyword);
	
	List<Product> getListByRange(long id, int min, int max);
}
