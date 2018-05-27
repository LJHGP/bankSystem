package com.bank.bankSystem.service;

import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.model.SignInModel;

public interface AccountService {

    Account createAccount(SignInModel signInModel);
}
