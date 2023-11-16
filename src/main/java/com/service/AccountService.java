package com.service;

import com.dto.AccountDTO;
import com.dto.FeedbackDTO;
import com.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {

    public Account getAccount(Long accountId);

    public AccountDTO getAccountById(Long accountId);

    public List<AccountDTO> getListAccount();

    public void addAccount(AccountDTO accountDTO);

    public void deleteAccount(Long accountId);

    public void updateAccount(Long accountId, AccountDTO accountDTO);

//    public List<FeedbackDTO> getFeedBackList(Long accountId);

}
