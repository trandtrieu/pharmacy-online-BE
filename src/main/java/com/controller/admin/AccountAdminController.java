package com.controller.admin;

import com.dto.AccountDTO;
import com.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3006")
@RestController
    @RequestMapping("/pharmacy-online/admin/account")
public class AccountAdminController {
    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/")
    public ResponseEntity<List<AccountDTO>> getListAccount() {
        return ResponseEntity.ok(accountService.getListAccount());
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> addAccount(@RequestBody AccountDTO accountDTO) {
        accountService.addAccount(accountDTO);
        return ResponseEntity.ok("Data saved");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted");
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateAcount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        accountService.updateAccount(id, accountDTO);
        return ResponseEntity.ok("Account updated");
    }

}