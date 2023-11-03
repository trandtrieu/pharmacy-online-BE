package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AccountDTO;
import com.model.Account;
import com.repository.AccountRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class AccountController {
	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/accounts")
	public List<AccountDTO> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		List<AccountDTO> accountDTOs = accounts.stream().map(account -> {
			return new AccountDTO(account.getId(), account.getName(), account.getMail(), account.getPassword(),
					account.getAddress(), account.getDob(), account.getAvatar(), account.getPhone(), account.getRoles(),
					account.getContents());

		}).collect(Collectors.toList());
		return accountDTOs;
	}
}
