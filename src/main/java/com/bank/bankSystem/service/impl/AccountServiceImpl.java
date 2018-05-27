package com.bank.bankSystem.service.impl;

import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.domain.Customer;
import com.bank.bankSystem.mapper.AccountMapper;
import com.bank.bankSystem.mapper.CustomerMapper;
import com.bank.bankSystem.mapper.SequenceMapper;
import com.bank.bankSystem.model.SignInModel;
import com.bank.bankSystem.service.AccountService;
import com.bank.bankSystem.util.PinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Account createAccount(SignInModel signInModel) {
        Customer customer = customerMapper.findByName(signInModel.getName());
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUID.randomUUID().toString());
            customer.setName(signInModel.getName());
            customer.setAddress(signInModel.getAddress());
            customer.setBirth(signInModel.getBirth());
            customer.setCreateTime(new Date());
            customerMapper.insert(customer);
        }
        Account account = new Account();
        String accountNumber = sequenceMapper.getNextAccountNumber();
        String pinNumber = PinUtil.createPinNumber();
        account.setCustomerId(customer.getId());
        account.setId(UUID.randomUUID().toString());
        account.setType(signInModel.getType());
        account.setStatus(Account.Status.NORMAL.name());
        account.setPin(pinNumber);
        account.setNumber(accountNumber);
        account.setUnClearedBalance(BigDecimal.ZERO);
        account.setBalance(BigDecimal.ZERO);
        account.setCreateTime(new Date());
        accountMapper.insert(account);
        return account;
    }
}
