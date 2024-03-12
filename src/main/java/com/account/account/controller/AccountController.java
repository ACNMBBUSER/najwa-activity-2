package com.account.account.controller;

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

    @PostMapping("/v1/create/account")
    public ResponseEntity<ResponseHandler> createAccount(@RequestBody Account account){
        return accountService.createAccount(account);
    }

    @PutMapping("/v1/update/account/{id}")
    public ResponseEntity<ResponseHandler> updateProfile(@PathVariable int id, @RequestBody Account account){
        return accountService.updateAccount(id,account);
    }

    @PutMapping("v1/update/multiple-accounts")
    public ResponseEntity<ResponseHandler> updateMultipleAccounts(@RequestBody List<Account> accountList) {
        return accountService.updateMultipleAccounts(accountList);
    }
    @DeleteMapping("/v1/delete/account/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable int id){
        return accountService.deleteAccount(id);
    }

}
