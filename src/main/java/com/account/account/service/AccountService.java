package com.account.account.service;

import com.account.account.repository.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    List<Account> getAllAccount();

}
