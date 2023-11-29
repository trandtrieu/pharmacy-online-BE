package com.ServiceImpl;

import com.dto.AccountDTO;
import com.model.Account;
import com.repository.AccountRepository;
import com.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private final String FOLDER_PATH = "C:\\Users\\quoct\\Desktop\\Pharmacy\\pharmacy-online-FE-new\\public\\assets\\img\\avatar\\";
    private final String FOLDER_PATH2 = "C:\\Users\\quoct\\Desktop\\Pharmacy\\pharmacy-online-fe-admin\\public\\assets\\images\\avatar\\";
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
        accountDTO.setStatus(account.getStatus());
//        accountDTO.setPassword(account.getPassword());
//        accountDTO.setAvatar(account.getAvatar());
        accountDTO.setPhone(account.getPhone());
        return accountDTO;
    }

    @Override
    public List<AccountDTO> getListAccount() {
        List<Account> accountList = accountRepository.findAll();
        List<AccountDTO> accountDTOList = accountList.stream()
                .filter(account -> account.getStatus() != 0)
                .map(account -> {
                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setId(account.getId());
                    accountDTO.setName(account.getName());
                    accountDTO.setMail(account.getMail());
                    accountDTO.setUsername(account.getUsername());
                    accountDTO.setDob(account.getDob());
                    accountDTO.setStatus(account.getStatus());
//          accountDTO.setPassword(account.getPassword());
                    accountDTO.setAvatar(account.getAvatar());
                    accountDTO.setPhone(account.getPhone());
                    return accountDTO;
                })
                .collect(Collectors.toList());
        return accountDTOList;
    }



    @Override
    public void addAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setName(accountDTO.getName());
        account.setMail(accountDTO.getMail());
        account.setUsername(accountDTO.getUsername());
        account.setPassword(encoder.encode(accountDTO.getPassword()));
        account.setRoles(accountDTO.getRoles());
        account.setStatus(1);
        account.setDob(accountDTO.getDob());
//        account.setAvatar(accountDTO.getAvatar());
        account.setPhone(accountDTO.getPhone());
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = getAccount(accountId);
        account.setStatus(0);
        account.setPassword(generateRandomPassword(10));
        accountRepository.save(account);
    }

    public String generateRandomPassword(int length) {
        // Define the characters to be used in the random string
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Create a Random object
        Random random = new Random();

        // Use StringBuilder to efficiently build the random string
        StringBuilder stringBuilder = new StringBuilder();

        // Generate the random string of the specified length
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
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
    public void updateAccountImage(Long accountId, MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String filePath = FOLDER_PATH + originalFilename;
        String filePath2 = FOLDER_PATH2 + originalFilename;

        // Create a copy of the input stream to avoid overwriting for the first location
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }

        // Create a copy for the second location
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, Paths.get(filePath2), StandardCopyOption.REPLACE_EXISTING);
        }

        Account account = getAccount(accountId);
        account.setAvatar(originalFilename);
        accountRepository.save(account);
    }


}
