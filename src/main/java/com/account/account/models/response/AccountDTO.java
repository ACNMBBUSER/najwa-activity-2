package com.account.account.models.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDTO {
    private Long accountId;
    private Long cid;
    private String name;
    private int CASAAccount;
    private String accountType;
    private String status;
    private String transStatus;
    private float transLimit;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
}
