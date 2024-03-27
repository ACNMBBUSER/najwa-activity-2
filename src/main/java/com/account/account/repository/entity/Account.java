package com.account.account.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Account", schema = "account_db")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(AccountRelation.class)
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_seq")
    @SequenceGenerator(name="id_seq", sequenceName="id_seq", initialValue = 4, allocationSize=1)
    @Column(name = "Id")
    private long id;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "acc_id_generator")
    @TableGenerator(
            name = "acc_id_generator",
            table = "acc_id_table",
            initialValue = 327670000,
            allocationSize = 1)
    @Column(name = "AccountId")
    private long accountId;

    @Column(name = "cid")
    private Long cid;

    @Column(name = "Name")
    private String name;


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "casa_acc_generator")
    @TableGenerator(
            name = "casa_acc_generator",
            table = "casa_acc_table",
            initialValue = 987650000,
            allocationSize = 1)
    @Column(name = "CASAAccount")
    private long CASAAccount;

    @Column(name = "AccountType")
    private String accountType;

    @Column(name = "Status")
    private String status;

    @Column(name = "TransStatus")
    private String transStatus;

    @JsonSerialize(using = ToStringSerializer.class)
    @DecimalMin(value = "0.00", inclusive = false)
    @DecimalMax(value = "999999999999.99", inclusive = false)
    @Column(name = "TransLimit")
    private BigDecimal transLimit;

    @Column(name = "CreatedDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @Column(name = "ModifiedDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

}
