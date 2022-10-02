package com.bsf.banktransactionapi.model;

import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ACCOUNTS")
@Data
public class Account {

    @Id
    @Column(name = "ACCOUNTID")
    private Long accountId;

    @NotNull
    @Column(name = "ACCOUNTNAME")
    private String name;

    @NotNull
    @Column(name="ACCOUNTTYPE")
    private String accountType;

    @NotNull
    @Column(name = "BRANCH")
    private String branch;

    @NotNull
    @Column(name = "BALANCE")
    @Min(value = 0, message = "account balance must be positive")
    private BigDecimal balance;

    public Account() {
        super();
    }

    public Account(Long accountId, @NotNull String name, @NotNull String accountType, @NotNull String branch,
                   @NotNull @Min(value = 0, message = "account balance must be positive") BigDecimal balance) {
        super();
        this.accountId = accountId;
        this.name = name;
        this.accountType = accountType;
        this.branch = branch;
        this.balance = balance;
    }


}
