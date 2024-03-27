package com.account.account.service.impl;

import com.account.account.exception.AccountNotFoundException;
import com.account.account.models.request.AccountRequest;
import com.account.account.models.response.AccountDTO;
import com.account.account.models.response.RandomBooleanResponse;
import com.account.account.models.response.ResponseHandler;
import com.account.account.repository.AccountRepository;
import com.account.account.repository.entity.Account;
import com.account.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    ResponseHandler<Account> responseHandler = new ResponseHandler<>();

    public RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper; // ObjectMapper for JSON parsing

    @Autowired
    private ModelMapper modelMapper;

    private final Environment environment;

    public AccountServiceImpl(Environment environment, RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.environment = environment;
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public List<AccountDTO> getAllAccount(){

        List<AccountDTO> mapper = accountRepository.findAll()
                .stream()
                .map(value -> modelMapper.map(value, AccountDTO.class)).toList();
        return mapper;
    }

    @Override
    public Account getProfileById(long accId){
        Account account = accountRepository.findByAccountId(accId);

        if (account == null) {
            throw new AccountNotFoundException("Account with ID " + accId + " not found");
        }

        return account;
    }

    @Override
    public ResponseEntity<ResponseHandler> createAccount(AccountRequest accountRequest) {
        String randomBooleanUrl = environment.getProperty("app.randomBooleanUrl");

        ResponseEntity<RandomBooleanResponse> response = restTemplate.exchange(randomBooleanUrl,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<RandomBooleanResponse>() {
                });

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                // Parse the JSON string into a JSON object
                String answer = response.getBody().getAnswer();
                log.info(answer);

                //Eligibility checking
                if (answer != null) {
                    if (answer.equals("yes"))
                    {
                          if (accountRequest.getCid() == 0) {
                            return ResponseEntity.ok(responseHandler.generateFailResponse("CID must be filled"));
                        } else if (accountRequest.getName() == null) {
                            return ResponseEntity.ok(responseHandler.generateFailResponse("Name must be filled"));
                        }
                          else if (accountRequest.getAccountType() == null) {
                            return ResponseEntity.ok(responseHandler.
                                    generateFailResponse("Account Type must be filled"));
                        }
                        else {
                            Account account = modelMapper.map(accountRequest, Account.class);
                            LocalDate currentDate = LocalDate.now();
                            account.setCreatedDate(currentDate);
                            account.setModifiedDate(currentDate);
                            accountRepository.save(account);
                            return ResponseEntity.ok(responseHandler.
                                    generateSuccessResponse("Account Creation Success"));
                        }
                    } else if (answer.equals("no")) {
                        return ResponseEntity.ok(responseHandler.generateFailResponse("Account creation failed",
                                "You are not eligible to create account"));
                    }
                } return ResponseEntity.ok(responseHandler.generateFailResponse("Failed to do eligibility checking"));


            } catch (Exception e) {
                e.printStackTrace(); // Handle parsing exception
                return ResponseEntity.ok(responseHandler.generateFailResponse("Failed to parse eligibility checking answer"));
            }
        } else {
            // Handle error response
            return ResponseEntity.ok(responseHandler.generateFailResponse("Failed to fetch eligibility checking"));
        }
    }

    @Override
    public ResponseEntity<ResponseHandler> updateAccount(long accId, AccountRequest accountRequest) {

        Account account = modelMapper.map(accountRequest, Account.class);
        Account optionalAccount = accountRepository.findByAccountId(accId);

        if (optionalAccount != null) {
            //Account updateAccount = optionalAccount.get();

            if(account.getAccountId() != 0) {
                optionalAccount.setAccountId(account.getAccountId());
            } if (account.getCid() != null) {
                optionalAccount.setCid(account.getCid());
            } if (account.getName() != null) {
                optionalAccount.setName(account.getName());
            } if (account.getCASAAccount() != 0) {
                optionalAccount.setCASAAccount(account.getCASAAccount());
            } if (account.getAccountType() != null) {
                optionalAccount.setAccountType(account.getAccountType());
            } if (account.getStatus() != null) {
                optionalAccount.setStatus(account.getStatus());
            } if (account.getTransStatus() != null) {
                optionalAccount.setTransStatus(account.getTransStatus());
            } if (account.getTransLimit() != null) {
                optionalAccount.setTransLimit(account.getTransLimit());
            } if (account.getCreatedDate() != null) {
                optionalAccount.setCreatedDate(account.getCreatedDate());
            }

            LocalDate currentDate = LocalDate.now();
            optionalAccount.setModifiedDate(currentDate);
            accountRepository.save(optionalAccount);
            return ResponseEntity.ok(responseHandler.generateSuccessResponse(optionalAccount));

        } else {
            return ResponseEntity.ok(responseHandler.generateFailResponse("Account not found with id: " + accId));
        }
    }

    @Override
    public ResponseEntity<ResponseHandler> updateMultipleAccounts(List<AccountRequest> accountList) {

        List<Account> updatedAccounts = new ArrayList<>();
        for (AccountRequest accountRequest : accountList) {

            Long accountId = accountRequest.getAccountId();

           // Account account = modelMapper.map(accountRequest, Account.class);
            Account optionalAccount = accountRepository.findByAccountId(accountId);
            if (optionalAccount != null) {
                //Account updateAccount = optionalAccount.get();
//
//                if(account.getAccountId() != 0) {
//                    optionalAccount.setAccountId(account.getAccountId());
//                }
                if (accountRequest.getCid() != 0) {
                    optionalAccount.setCid(accountRequest.getCid());
                }
                if (accountRequest.getName() != null) {
                    optionalAccount.setName(accountRequest.getName());
                }
                if (accountRequest.getAccountType() != null) {
                    optionalAccount.setAccountType(accountRequest.getAccountType());
                }
                if (accountRequest.getStatus() != null) {
                    optionalAccount.setStatus(accountRequest.getStatus());
                }
                if (accountRequest.getTransStatus() != null) {
                    optionalAccount.setTransStatus(accountRequest.getTransStatus());
                }
                if (accountRequest.getTransLimit() != null) {
                    optionalAccount.setTransLimit(accountRequest.getTransLimit());
                }
                if (accountRequest.getCreatedDate() != null) {
                    optionalAccount.setCreatedDate(accountRequest.getCreatedDate());
                }

                LocalDate currentDate = LocalDate.now();
                optionalAccount.setModifiedDate(currentDate);


                accountRepository.save(optionalAccount);
                updatedAccounts.add(optionalAccount);
            }
        }
        if (!updatedAccounts.isEmpty()) {
            return ResponseEntity.ok(responseHandler.generateSuccessResponse(updatedAccounts));
        } else {
            return ResponseEntity.ok(responseHandler.generateFailResponse("No accounts updated"));
        }
    }

    @Override
    public ResponseEntity<?> deleteAccount(long accId){

        Account account = accountRepository.findByAccountId(accId);
        if (account != null) {
            accountRepository.deleteAccount(accId);
            return ResponseEntity.ok(responseHandler.generateSuccessResponse("Account deletion with ID: " +
                    accId + " successfully deleted."));
        } else {
            return ResponseEntity.ok(responseHandler.generateFailResponse("Account not found with ID: " + accId));
        }
    }

}
