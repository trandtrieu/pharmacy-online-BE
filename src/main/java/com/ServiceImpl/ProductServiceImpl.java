package com.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Product;
import com.repository.ProductRepository;

@Service
public class ProductServiceImpl {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> searchProduct(String keyword) {
		List<Product> list = productRepository.searchProduct(keyword);
		return list;
	}

	public List<Product> getListByRange(long id, int min, int max) {
		List<Product> list = productRepository.getListProductByPriceRange(id, min, max);
		return list;
	}

}
