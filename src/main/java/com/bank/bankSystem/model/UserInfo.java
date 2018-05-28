package com.bank.bankSystem.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author admin
 * @create 2018-05-28 12:27
 **/
public class UserInfo {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPin() {
        return pin;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getUn_cleared_balance() {
        return un_cleared_balance;
    }

    public void setUn_cleared_balance(BigDecimal un_cleared_balance) {
        this.un_cleared_balance = un_cleared_balance;
    }

    private String name;

    private String address;

    private String number;

    private Date birth;

    private String type;

    private String status;

    private String pin;

    private BigDecimal balance;

    private BigDecimal un_cleared_balance;



}
