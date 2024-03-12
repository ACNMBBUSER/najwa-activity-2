package com.account.account.service;

import com.account.account.models.response.ResponseHandler;
import com.account.account.repository.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    List<Account> getAllAccount();


    ResponseEntity<ResponseHandler> createAccount(Account account);

    ResponseEntity<ResponseHandler> updateAccount(int id, Account account);

    ResponseEntity<ResponseHandler> updateMultipleAccounts(List<Account> accountList);

    ResponseEntity<?> deleteAccount(int id);
}
