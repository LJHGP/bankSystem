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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/customer")
@Api(description = "customer")
public class CustomerController {


    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private AccountService accountService;


    @RequestMapping("/signIn")
    @ResponseBody
    public Result<Account> signIn(SignInModel signInModel) {
        Customer customer = customerMapper.findByName(signInModel.getName());
        if(customer == null){
            Credit credit = creditMapper.findByName(signInModel.getName());
            if (credit == null) {
                return new Result<>(Result.ReturnValue.FAILURE, "your credit score is not exist");
            }
            if (credit.getScore() >= 80) {
                String accountNumber = accountService.createAccount(signInModel);
                if (accountNumber == null) {
                    return new Result<>(Result.ReturnValue.FAILURE, "system error");
                }
                return new Result(Result.ReturnValue.SUCCESS, "success! account:" + signInModel.getName() + ",password:" + accountNumber,"");
            } else {
                return new Result<>(Result.ReturnValue.FAILURE, "your credit score must be greater than 80");
            }
        }else{
            return new Result<>(Result.ReturnValue.FAILURE, "your account exist");
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
