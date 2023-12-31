package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.DeliveryAddressDTO;
import com.model.Account;
import com.model.DeliveryAddress;
import com.repository.AccountRepository;
import com.repository.DeliveryAddressRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class DeliveryAddressController {
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	DeliveryAddressRepository deliveryAddressRepository;

	// get delivery-address by user_id
	@GetMapping("delivery-address/{user_id}")
	public List<DeliveryAddressDTO> getDeliveryAddressesByUserId(@PathVariable long user_id) {
		List<DeliveryAddressDTO> deliveryAddressDTOs = new ArrayList<>();

		Account account = accountRepository.findById(user_id).orElse(null);

		if (account != null) {
			List<DeliveryAddress> deliveryAddresses = account.getAddress();
			for (DeliveryAddress deliveryAddress : deliveryAddresses) {
				DeliveryAddressDTO deliveryAddressDTO = new DeliveryAddressDTO();
				// Thiết lập các thông tin trong DeliveryAddressDTO dựa trên DeliveryAddress
				deliveryAddressDTO.setAddress_id(deliveryAddress.getAddress_id());
				deliveryAddressDTO.setRecipient_full_name(deliveryAddress.getRecipient_full_name());
				deliveryAddressDTO.setRecipient_phone_number(deliveryAddress.getRecipient_phone_number());
				deliveryAddressDTO.setSpecific_address(deliveryAddress.getSpecific_address());
				deliveryAddressDTO.setStatus_default(deliveryAddress.getStatus_default());
				deliveryAddressDTO.setUser_id(deliveryAddress.getAccount().getId());
				deliveryAddressDTOs.add(deliveryAddressDTO);
			}
		}

		return deliveryAddressDTOs;
	}

	// delete delivery_address
	@DeleteMapping("delivery-address/delete")
	public ResponseEntity<String> deleteDeliveryAddress(@RequestParam long user_id, @RequestParam int address_id) {
		Account account = accountRepository.findById(user_id).orElse(null);

		if (account == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}

		DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(address_id).orElse(null);

		if (deliveryAddress == null) {
			return new ResponseEntity<>("DeliveryAddress not found", HttpStatus.NOT_FOUND);
		}

		// Kiểm tra xem địa chỉ giao hàng thuộc về người dùng
		if (deliveryAddress.getAccount().getId() != user_id) {
			return new ResponseEntity<>("DeliveryAddress does not belong to the user", HttpStatus.BAD_REQUEST);
		}

		deliveryAddressRepository.delete(deliveryAddress);
		return new ResponseEntity<>("DeliveryAddress deleted successfully", HttpStatus.OK);
	}

	// add delivery_address
	@PostMapping("delivery-address/add/{user_id}")
	public ResponseEntity<?> addDeliveryAddress(@PathVariable long user_id,
			@RequestBody DeliveryAddressDTO deliveryAddressDTO) {
		Account account = accountRepository.findById(user_id).orElse(null);
		if (account == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		} else {
			DeliveryAddress deliveryAddress = new DeliveryAddress();
			deliveryAddress.setAccount(account);
			deliveryAddress.setRecipient_full_name(deliveryAddressDTO.getRecipient_full_name());
			deliveryAddress.setRecipient_phone_number(deliveryAddressDTO.getRecipient_phone_number());
			deliveryAddress.setSpecific_address(deliveryAddressDTO.getSpecific_address());
			deliveryAddressRepository.save(deliveryAddress);
		}
		return new ResponseEntity<>(deliveryAddressDTO, HttpStatus.OK);

	}

	// set default delivery_address
	@PutMapping("delivery-address/setDefault")
	public ResponseEntity<?> setDefaultDeliveryAddress(@RequestParam long user_id,
			@RequestParam int deliveryAddressID) {

		Optional<Account> optionalAccount = accountRepository.findById(user_id);
		if (!optionalAccount.isPresent()) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}

		Optional<DeliveryAddress> optionalDeliveryAddress = deliveryAddressRepository.findById(deliveryAddressID);
		if (!optionalDeliveryAddress.isPresent()) {
			return new ResponseEntity<>("Delivery address not found", HttpStatus.NOT_FOUND);
		}

		DeliveryAddress deliveryAddress = optionalDeliveryAddress.get();
		if (deliveryAddress.getAccount().getId() != user_id) {
			return new ResponseEntity<>("Delivery address does not belong to the user", HttpStatus.BAD_REQUEST);
		}

		deliveryAddress.setStatus_default(1);

		List<DeliveryAddress> userAddresses = deliveryAddressRepository.findByAccountId(user_id);
		for (DeliveryAddress address : userAddresses) {
			if (address.getAddress_id() != deliveryAddressID) {
				address.setStatus_default(0);
				deliveryAddressRepository.save(address);
			}
		}

		deliveryAddressRepository.save(deliveryAddress);

		return new ResponseEntity<>("Default delivery address set successfully.", HttpStatus.OK);
	}

	// get delivery-address by status default = 1
	@GetMapping("delivery-address/status-default/{user_id}")
	public ResponseEntity<DeliveryAddressDTO> getDefaultDeliveryAddressByUserId(@PathVariable long user_id) {
		Account account = accountRepository.findById(user_id).orElse(null);

		if (account != null) {
			List<DeliveryAddress> deliveryAddresses = account.getAddress();

			for (DeliveryAddress deliveryAddress : deliveryAddresses) {
				if (deliveryAddress.getStatus_default() == 1) {
					DeliveryAddressDTO deliveryAddressDTO = new DeliveryAddressDTO();
					// Thiết lập các thông tin trong DeliveryAddressDTO dựa trên DeliveryAddress
					deliveryAddressDTO.setAddress_id(deliveryAddress.getAddress_id());
					deliveryAddressDTO.setRecipient_full_name(deliveryAddress.getRecipient_full_name());
					deliveryAddressDTO.setRecipient_phone_number(deliveryAddress.getRecipient_phone_number());
					deliveryAddressDTO.setSpecific_address(deliveryAddress.getSpecific_address());
					deliveryAddressDTO.setStatus_default(deliveryAddress.getStatus_default());
					deliveryAddressDTO.setUser_id(deliveryAddress.getAccount().getId());

					return new ResponseEntity<>(deliveryAddressDTO, HttpStatus.OK);
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}