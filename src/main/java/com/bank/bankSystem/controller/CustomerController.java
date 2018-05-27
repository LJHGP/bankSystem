package com.bank.bankSystem.controller;

import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.domain.Credit;
import com.bank.bankSystem.domain.Customer;
import com.bank.bankSystem.mapper.CreditMapper;
import com.bank.bankSystem.mapper.CustomerMapper;
import com.bank.bankSystem.model.Result;
import com.bank.bankSystem.model.SignInModel;
import com.bank.bankSystem.service.AccountService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customer")
@Api(description = "customer")
public class CustomerController {


    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private AccountService accountService;


    @PostMapping("/signIn")
    public Result<Account> signIn(@RequestBody SignInModel signInModel) {
        Credit credit = creditMapper.findByName(signInModel.getName());
        if (credit.getScore() >= 80) {
            Account account = accountService.createAccount(signInModel);
            return new Result<>(Result.ReturnValue.SUCCESS, "", account);
        } else {
            return new Result<>(Result.ReturnValue.FAILURE, "your credit score is not enough");
        }
    }


    @PostMapping("/check")
    public Result check(@RequestParam String name) {
        Customer customer = customerMapper.findByName(name);
        if (customer != null) {
            return new Result(Result.ReturnValue.FAILURE, "this name is exist");
        }
        return new Result(Result.ReturnValue.SUCCESS);
    }


}
