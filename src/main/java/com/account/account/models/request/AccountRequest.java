package com.account.account.models.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountRequest {

    private long accountId;
    private long cid;
    private long CASAAccount;
    private String name;
    private String accountType;
    private String status;
    private String transStatus;
    private LocalDate createdDate;
    private BigDecimal transLimit;
}
