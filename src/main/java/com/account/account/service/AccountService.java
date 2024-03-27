package com.account.account.service;

import com.account.account.models.request.AccountRequest;
import com.account.account.models.response.AccountDTO;
import com.account.account.models.response.ResponseHandler;
import com.account.account.repository.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    List<AccountDTO> getAllAccount();


    Account getProfileById(long id);

    ResponseEntity<ResponseHandler> createAccount(AccountRequest account);

    ResponseEntity<ResponseHandler> updateAccount(long accId, AccountRequest account);

    ResponseEntity<ResponseHandler> updateMultipleAccounts(List<AccountRequest> accountList);

    ResponseEntity<?> deleteAccount(long accId);
}
