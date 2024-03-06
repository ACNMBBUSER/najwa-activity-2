package com.account.account.controller;

import com.account.account.models.response.ResponseHandler;
import com.account.account.repository.entity.Account;
import com.account.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    ResponseHandler<Account> responseHandler = new ResponseHandler<>();

    @GetMapping("/v1/retrieve/accounts")
    public ResponseEntity<ResponseHandler> getAll(){
        return ResponseEntity.ok(responseHandler.generateSuccessResponse(accountService.getAllAccount()));
    }
}
