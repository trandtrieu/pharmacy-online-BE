package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Product;
import com.repository.ProductRepository;
import com.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> searchProduct(String keyword) {
		List<Product> list = productRepository.searchProduct(keyword);
		return list;
	}

	@Override
	public List<Product> getListByRange(long id, int min, int max) {
		List<Product> list = productRepository.getListProductByPriceRange(id, min, max);
		return list;
	}
	
	

}
