package com.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.AccountDTO;
import com.service.AccountService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDTO> getAcountByIda(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountByIdu(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateAcountU(@PathVariable Long id, @RequestBody AccountDTO accountDTO ){
        accountService.updateAccountu(id, accountDTO);
        return ResponseEntity.ok("Account updated");
    }

    @PutMapping(path = "/img/{id}")
    public ResponseEntity<String> updateAccountImage(@PathVariable Long id, @RequestParam("image") MultipartFile multipartFile) throws IOException{
        accountService.updateAccountImage(id, multipartFile);
        return ResponseEntity.ok("Image Updated");
    }



}
