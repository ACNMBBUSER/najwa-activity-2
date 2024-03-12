package com.account.account.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Account", schema = "account_db")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "AccountId")
    private long accountId;

    @Column(name = "cid")
    private Long cid;

    @Column(name = "Name")
    private String name;

    @Column(name = "CASAAccount")
    private int CASAAccount;

    @Column(name = "AccountType")
    private String accountType;

    @Column(name = "Status")
    private String status;

    @Column(name = "TransStatus")
    private String transStatus;

    @Column(name = "TransLimit")
    private float transLimit;

    @Column(name = "CreatedDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @Column(name = "ModifiedDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

}
