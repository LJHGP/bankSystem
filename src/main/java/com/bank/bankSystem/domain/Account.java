package com.bank.bankSystem.domain;


import java.math.BigDecimal;
import java.util.Date;

public class Account {

    public static final String SESSION_ATTR = "number";

    private String id;

    private String customerId;

    private String type;

    private String status;

    private String number;

    private String pin;

    private BigDecimal balance;

    private BigDecimal unClearedBalance;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPin() {
        return pin;
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

    public BigDecimal getUnClearedBalance() {
        return unClearedBalance;
    }

    public void setUnClearedBalance(BigDecimal unClearedBalance) {
        this.unClearedBalance = unClearedBalance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        if (customerId != null ? !customerId.equals(account.customerId) : account.customerId != null) return false;
        if (type != null ? !type.equals(account.type) : account.type != null) return false;
        if (status != null ? !status.equals(account.status) : account.status != null) return false;
        if (number != null ? !number.equals(account.number) : account.number != null) return false;
        if (pin != null ? !pin.equals(account.pin) : account.pin != null) return false;
        if (balance != null ? !balance.equals(account.balance) : account.balance != null) return false;
        if (unClearedBalance != null ? !unClearedBalance.equals(account.unClearedBalance) : account.unClearedBalance != null)
            return false;
        return createTime != null ? createTime.equals(account.createTime) : account.createTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (pin != null ? pin.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (unClearedBalance != null ? unClearedBalance.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", number='" + number + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                ", unClearedBalance=" + unClearedBalance +
                ", createTime=" + createTime +
                '}';
    }

    public enum Status {
        NORMAL, CLOSED, SUSPENDED;
    }


    public enum Type {
        SAVER, JUNIOR, CURRENT;

    }
}
