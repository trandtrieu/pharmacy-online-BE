package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Account;
import com.repository.AccountRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class AccountController {
	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/accounts/list")
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}
}
