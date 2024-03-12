package com.account.account.service.impl;

import com.account.account.models.response.RandomBooleanResponse;
import com.account.account.models.response.ResponseHandler;
import com.account.account.repository.AccountRepository;
import com.account.account.repository.entity.Account;
import com.account.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    ResponseHandler<Account> responseHandler = new ResponseHandler<>();

    public RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper; // ObjectMapper for JSON parsing

    public AccountServiceImpl(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }

    @Override
    public ResponseEntity<ResponseHandler> createAccount(Account account) {
        String randomBooleanUrl = "https://yesno.wtf/api";

        ResponseEntity<String> response = restTemplate.exchange(randomBooleanUrl,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                });

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                // Parse the JSON string into a JSON object
                String responseBody = response.getBody();
                RandomBooleanResponse randomBooleanResponse = objectMapper.readValue(responseBody, RandomBooleanResponse.class);
                String answer = randomBooleanResponse.getAnswer();
                log.info(answer);

                //Eligibility checking
                if (answer != null) {
                    if (answer.equals("yes"))
                    {
                          if (account.getCid() == null) {
                            return ResponseEntity.ok(responseHandler.generateFailResponse("CID must be filled"));
                        } else if (account.getName() == null) {
                            return ResponseEntity.ok(responseHandler.generateFailResponse("Name must be filled"));
                        }
                          else if (account.getAccountType() == null) {
                            return ResponseEntity.ok(responseHandler.generateFailResponse("Account Type must be filled"));
                        }
                        else {
                            LocalDate currentDate = LocalDate.now();
                            account.setCreatedDate(currentDate);
                            account.setModifiedDate(currentDate);
                            accountRepository.save(account);
                            return ResponseEntity.ok(responseHandler.generateSuccessResponse("Account Creation Success"));
                        }
                    } else if (answer.equals("no")) {
                        return ResponseEntity.ok(responseHandler.generateFailResponse("Account creation failed", "You are not eligible to create account"));
                        //return ResponseEntity.ok(responseHandler.generateSuccessResponse("You are not eligible to create account"));
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
    public ResponseEntity<ResponseHandler> updateAccount(int id, Account account) {

        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isPresent()) {
            Account updateAccount = optionalAccount.get();

            if(account.getAccountId() != 0) {
                updateAccount.setAccountId(account.getAccountId());
            } if (account.getCid() != null) {
                updateAccount.setCid(account.getCid());
            } if (account.getName() != null) {
                updateAccount.setName(account.getName());
            } if (account.getCASAAccount() != 0) {
                updateAccount.setCASAAccount(account.getCASAAccount());
            } if (account.getAccountType() != null) {
                updateAccount.setAccountType(account.getAccountType());
            } if (account.getStatus() != null) {
                updateAccount.setStatus(account.getStatus());
            } if (account.getTransStatus() != null) {
                updateAccount.setTransStatus(account.getTransStatus());
            } if (account.getTransLimit() != 0) {
                updateAccount.setTransLimit(account.getTransLimit());
            } if (account.getCreatedDate() != null) {
                updateAccount.setCreatedDate(account.getCreatedDate());
            }

            LocalDate currentDate = LocalDate.now();
            updateAccount.setModifiedDate(currentDate);
            accountRepository.save(updateAccount);
            return ResponseEntity.ok(responseHandler.generateSuccessResponse(updateAccount));

        } else {
            return ResponseEntity.ok(responseHandler.generateFailResponse("Account not found with id: " + id));
        }
    }

    @Override
    public ResponseEntity<ResponseHandler> updateMultipleAccounts(List<Account> accountList) {

        List<Account> updatedAccounts = new ArrayList<>();
        for (Account account : accountList) {
            Optional<Account> optionalAccount = accountRepository.findById(account.getId());
            if (optionalAccount.isPresent()) {
                Account updateAccount = optionalAccount.get();

                if(account.getAccountId() != 0) {
                    updateAccount.setAccountId(account.getAccountId());
                }
                if (account.getCid() != null) {
                    updateAccount.setCid(account.getCid());
                }
                if (account.getName() != null) {
                    updateAccount.setName(account.getName());
                }
                if (account.getCASAAccount() != 0) {
                    updateAccount.setCASAAccount(account.getCASAAccount());
                }
                if (account.getAccountType() != null) {
                    updateAccount.setAccountType(account.getAccountType());
                }
                if (account.getStatus() != null) {
                    updateAccount.setStatus(account.getStatus());
                }
                if (account.getTransStatus() != null) {
                    updateAccount.setTransStatus(account.getTransStatus());
                }
                if (account.getTransLimit() != 0) {
                    updateAccount.setTransLimit(account.getTransLimit());
                }
                if (account.getCreatedDate() != null) {
                    updateAccount.setCreatedDate(account.getCreatedDate());
                }

                LocalDate currentDate = LocalDate.now();
                updateAccount.setModifiedDate(currentDate);
                updatedAccounts.add(accountRepository.save(updateAccount));
            }
        }
        if (!updatedAccounts.isEmpty()) {
            return ResponseEntity.ok(responseHandler.generateSuccessResponse(updatedAccounts));
        } else {
            return ResponseEntity.ok(responseHandler.generateFailResponse("No accounts updated"));
        }
    }

    @Override
    public ResponseEntity<?> deleteAccount(int id){

        Optional<Account> optionalProfile = accountRepository.findById(id);
        if (optionalProfile.isPresent()) {
            accountRepository.deleteById(id);
            return ResponseEntity.ok(responseHandler.generateSuccessResponse("Account deletion with ID: " + id + " successfully deleted."));
        } else {
            return ResponseEntity.ok(responseHandler.generateFailResponse("Account not found with ID: " + id));
        }
    }

}
