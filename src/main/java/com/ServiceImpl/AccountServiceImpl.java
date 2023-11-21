package com.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dto.AccountDTO;
import com.model.Account;
import com.repository.AccountRepository;
import com.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private final String FOLDER_PATH = "D:/Documents/OJT/mock-project/pharmacy-online-fe/public/assets/images";

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account with id: " + accountId + " not found!!"));
    }

    @Override
    public AccountDTO getAccountById(Long accountId) {
        Account account = getAccount(accountId);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setName(account.getName());
        accountDTO.setMail(account.getMail());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setDob(account.getDob());
//        accountDTO.setPassword(account.getPassword());
//        accountDTO.setAvatar(account.getAvatar());
        accountDTO.setPhone(account.getPhone());
        return accountDTO;
    }

    @Override
    public List<AccountDTO> getListAccount() {
        List<Account> accountList = accountRepository.findAll();
        List<AccountDTO> accountDTOList = accountList.stream().map(account -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setId(account.getId());
            accountDTO.setName(account.getName());
            accountDTO.setMail(account.getMail());
            accountDTO.setUsername(account.getUsername());
            accountDTO.setDob(account.getDob());
//            accountDTO.setPassword(account.getPassword());
//            accountDTO.setAvatar(account.getAvatar());
            accountDTO.setPhone(account.getPhone());
            return accountDTO;
        }).collect(Collectors.toList());
        return accountDTOList;
    }

    @Override
    public void addAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setName(accountDTO.getName());
        account.setMail(accountDTO.getMail());
        account.setUsername(accountDTO.getUsername());
        account.setPassword(encoder.encode(accountDTO.getPassword()));
        account.setRoles("USER");
//        account.setDob(accountDTO.getDob());
//        account.setAvatar(accountDTO.getAvatar());
        account.setPhone(accountDTO.getPhone());
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = getAccount(accountId);
        accountRepository.delete(account);
    }

    @Override
    public void updateAccount(Long accountId, AccountDTO accountDTO) {
        Account account = getAccount(accountId);
        account.setName(accountDTO.getName());
        account.setMail(accountDTO.getMail());
        account.setDob(accountDTO.getDob());
        account.setUsername(accountDTO.getUsername());
//        account.setAvatar(accountDTO.getAvatar());
        account.setPhone(accountDTO.getPhone());
        accountRepository.save(account);
    }

    @Override
    public AccountDTO getAccountByIdu(Long accountId) {
        Account account = getAccount(accountId);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setName(account.getName());
        accountDTO.setMail(account.getMail());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setDob(account.getDob());
//        accountDTO.setPassword(account.getPassword());
        accountDTO.setAvatar(account.getAvatar());
        accountDTO.setPhone(account.getPhone());
        return accountDTO;
    }

    @Override
    public void updateAccountu(Long accountId, AccountDTO accountDTO){
        Account account = getAccount(accountId);
        account.setName(accountDTO.getName());
        account.setMail(accountDTO.getMail());
        account.setDob(accountDTO.getDob());
        account.setPhone(accountDTO.getPhone());
        accountRepository.save(account);
    }

    @Override
    public void updateAccountImage(Long accountId, MultipartFile multipartFile) throws IOException{
        String filePath = FOLDER_PATH + multipartFile.getOriginalFilename();
        Account account = getAccount(accountId);
        account.setAvatar(multipartFile.getOriginalFilename());
        multipartFile.transferTo(new File(filePath));
        accountRepository.save(account);
    }

}
