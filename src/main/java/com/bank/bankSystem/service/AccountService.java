package com.bank.bankSystem.service;

import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.model.SignInModel;
import org.apache.ibatis.annotations.Param;

public interface AccountService {

    String createAccount(SignInModel signInModel);

    Account findByNumber(@Param("number") String number);
}
