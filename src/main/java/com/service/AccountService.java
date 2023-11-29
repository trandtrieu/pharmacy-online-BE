package com.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dto.AccountDTO;
import com.model.Account;

public interface AccountService {

    public Account getAccount(Long accountId);

    public AccountDTO getAccountById(Long accountId);

    public List<AccountDTO> getListAccount();

    public void addAccount(AccountDTO accountDTO);

    public void deleteAccount(Long accountId);

    public void updateAccount(Long accountId, AccountDTO accountDTO);

    public AccountDTO getAccountByIdu(Long accountId);
    public void updateAccountu(Long accountId, AccountDTO accountDTO);

    public void updateAccountImage(Long accountId, MultipartFile multipartFile) throws IOException;


//    public List<FeedbackDTO> getFeedBackList(Long accountId);

}