package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.DiscountCodeDTO;
import com.model.DiscountCode;
import com.repository.DiscountCodeRepository;

@Service
public class DiscountCodeService {

	@Autowired
	private DiscountCodeRepository discountCodeRepository;

	public DiscountCode createDiscountCode(DiscountCodeDTO discountCodeDTO) {
		DiscountCode discountCode = new DiscountCode();
		discountCode.setCode(discountCodeDTO.getCode());
		discountCode.setDiscountPercentage(discountCodeDTO.getDiscountPercentage());
		discountCode.setExpiryDate(discountCodeDTO.getExpiryDate());
		discountCode.setTimesUsable(discountCodeDTO.getTimesUsable());

		return discountCodeRepository.save(discountCode);
	}

	public DiscountCode getDiscountCodeById(Long id) {
		return discountCodeRepository.findById(id).orElse(null);
	}
	
	
	public DiscountCode getDiscountCodeByCode(String code) {
	    return discountCodeRepository.findByCode(code);
	}
	
	public void updateDiscountCode(DiscountCode discountCode) {
	    discountCodeRepository.save(discountCode);
	}


}
