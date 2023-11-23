package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.DiscountCodeDTO;
import com.model.DiscountCode;
import com.repository.DiscountCodeRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
		discountCode.setStatus(discountCodeDTO.getStatus());
		return discountCodeRepository.save(discountCode);
	}

	public DiscountCode getDiscountCodeById(Long id) {
		return discountCodeRepository.findById(id).orElse(null);
	}

	public List<DiscountCodeDTO> getAllDiscountCode(){
		List<DiscountCode> discountCodeList = discountCodeRepository.findAll();
		List<DiscountCodeDTO> discountCodeDTOList = discountCodeList.stream().map(discountCode -> {
		DiscountCodeDTO discountCodeDTO = new DiscountCodeDTO();
		discountCodeDTO.setCode(discountCode.getCode());
		discountCodeDTO.setId(discountCode.getId());
		discountCodeDTO.setDiscountPercentage(discountCode.getDiscountPercentage());
		discountCodeDTO.setTimesUsable(discountCode.getTimesUsable());
		discountCodeDTO.setStatus(discountCode.getStatus());
		discountCodeDTO.setExpiryDate(discountCode.getExpiryDate());
		return discountCodeDTO;
		}).collect(Collectors.toList());
				return discountCodeDTOList;
	}

	public DiscountCodeDTO getDiscountCode(Long id){
		DiscountCode discountCode  = discountCodeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID not found!"));
		DiscountCodeDTO discountCodeDTO = new DiscountCodeDTO();
		discountCodeDTO.setId(discountCode.getId());
		discountCodeDTO.setDiscountPercentage(discountCode.getDiscountPercentage());
		discountCodeDTO.setExpiryDate(discountCode.getExpiryDate());
		discountCodeDTO.setTimesUsable(discountCode.getTimesUsable());
		discountCodeDTO.setCode(discountCode.getCode());
		discountCodeDTO.setStatus(discountCode.getStatus());

		return discountCodeDTO;
	}
	
	public DiscountCode getDiscountCodeByCode(String code) {
	    return discountCodeRepository.findByCode(code);
	}
	
	public void updateDiscountCode(DiscountCode discountCode) {
	    discountCodeRepository.save(discountCode);
	}

	public void updateDiscountCodeT(DiscountCodeDTO discountCodeDTO, Long id){
		DiscountCode discountCode = discountCodeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
		discountCode.setCode(discountCodeDTO.getCode());
		discountCode.setDiscountPercentage(discountCodeDTO.getDiscountPercentage());
		discountCode.setTimesUsable(discountCodeDTO.getTimesUsable());
		discountCode.setExpiryDate(discountCodeDTO.getExpiryDate());
		discountCode.setStatus(discountCodeDTO.getStatus());
		discountCodeRepository.save(discountCode);
	}

	public String deleteDiscountCode(Long id){
		discountCodeRepository.deleteById(id);
		return "Deleted";
	}

	public String generateDiscountCode(){
		int codeLength = 6;
		String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder voucherCode = new StringBuilder();

		Random random = new Random();
		for (int i = 0; i < codeLength; i++) {
			char randomChar = characters.charAt(random.nextInt(characters.length()));
			voucherCode.append(randomChar);
		}
		return voucherCode.toString();
	}


}
