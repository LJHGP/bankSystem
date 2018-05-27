package com.bank.bankSystem.model;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private String returnValue;
    private String reason;
    private T data;

    public Result() {

    }

    public Result(ReturnValue returnValue) {
        this.returnValue = returnValue.name();
    }

    public Result(ReturnValue returnValue, String reason) {
        this.returnValue = returnValue.name();
        this.reason = reason;
    }

    public Result(ReturnValue returnValue, String reason, T data) {
        this.returnValue = returnValue.name();
        this.reason = reason;
        this.data = data;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "returnValue='" + returnValue + '\'' +
                ", reason='" + reason + '\'' +
                ", data=" + data +
                '}';
    }

    public enum ReturnValue {
        SUCCESS, FAILURE
    }
}
