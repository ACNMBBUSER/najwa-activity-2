package com.account.account.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountRelation implements Serializable {

    private long id;
    private long accountId;
    private long CASAAccount;

}
