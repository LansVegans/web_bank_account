package com.business.convertation.entities;

import com.business.convertation.entities.view.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "user")
@DynamicInsert
public class User implements Serializable {

    // This is transactionID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({TransactionID.class, TransactionList.class})
    @Column(name = "id")
    private Long id;

    @JsonView({AccountID.class})
    @Column(name = "accountId")
    private Long accountId;

    @JsonView({AccountInfo.class})
    @Column(name = "name")
    private String name;

    @JsonView({AccountInfo.class})
    @Column(name = "balance")
    private double balance;

    @JsonView({TransactionInfo.class, TransactionList.class})
    @Column(name = "type")
    private String type;

    @JsonView({TransactionInfo.class, TransactionList.class})
    @Column(name = "currency")
    private String currency;

    @JsonView({TransactionInfo.class, TransactionList.class})
    @Column(name = "date")
    private Date date;

    @JsonView({TransactionInfo.class, TransactionList.class})
    @Column(name = "balanceBefore")
    private double balanceBefore;

    @JsonView({TransactionInfo.class, TransactionList.class})
    @Column(name = "balanceAfter")
    private double balanceAfter;

    @JsonView({TransactionInfo.class})
    @Column(name = "cbrfRate")
    private double cbrfRate;

    @JsonView({TransactionInfo.class, TransactionList.class})
    @Column(name = "amount")
    private double amount;




    public User() {
    }

    public User(Long accountId) {
        this.accountId = accountId;
    }

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public User(Long accountId, Long transactionId) {
        this.accountId = accountId;
        this.id = transactionId;
    }

    public User(Long accountId, String name, double balance) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
    }

    public User(Long accountId, String name, double balance, Date date) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
        this.date = date;
    }

    public User(Long accountId, String name, double balance, String type, String currency, Date date, double balanceBefore, double balanceAfter, double cbrfRate, Long transactionId) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
        this.date = date;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.cbrfRate = cbrfRate;
        this.id = transactionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public double getCbrfRate() {
        return cbrfRate;
    }

    public void setCbrfRate(double cbrfRate) {
        this.cbrfRate = cbrfRate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        return result;
    }

}
