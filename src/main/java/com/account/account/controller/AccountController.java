package com.account.account.controller;

import com.account.account.models.request.AccountRequest;
import com.account.account.models.response.ResponseHandler;
import com.account.account.repository.entity.Account;
import com.account.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    ResponseHandler<Account> responseHandler = new ResponseHandler<>();

    @GetMapping("/v1/retrieve/accounts")
    public ResponseEntity<ResponseHandler> getAll(){
        return ResponseEntity.ok(responseHandler.generateSuccessResponse(accountService.getAllAccount()));
    }

    @GetMapping("v1/retrieve/account/{id}")
    public Account getById(@PathVariable long id){
        return accountService.getProfileById(id);
    }

    @PostMapping("/v1/create/account")
    public ResponseEntity<ResponseHandler> createAccount(@RequestBody AccountRequest account){
        return accountService.createAccount(account);
    }

    @PutMapping("/v1/update/account/{accId}")
    public ResponseEntity<ResponseHandler> updateProfile(@PathVariable long accId, @RequestBody AccountRequest account){
        return accountService.updateAccount(accId,account);
    }

    @PutMapping("v1/update/multiple-accounts")
    public ResponseEntity<ResponseHandler> updateMultipleAccounts(@RequestBody List<AccountRequest> accountList) {
        return accountService.updateMultipleAccounts(accountList);
    }
    @DeleteMapping("/v1/delete/account/{accId}")
    public ResponseEntity<?> deleteProfile(@PathVariable long accId){
        return accountService.deleteAccount(accId);
    }

}
