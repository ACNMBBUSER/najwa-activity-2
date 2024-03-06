package com.account.account.service.impl;

import com.account.account.repository.AccountRepository;
import com.account.account.repository.entity.Account;
import com.account.account.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }
}
