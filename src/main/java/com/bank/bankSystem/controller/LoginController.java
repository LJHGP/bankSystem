package com.bank.bankSystem.controller;

import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.mapper.AccountMapper;
import com.bank.bankSystem.model.LoginModel;
import com.bank.bankSystem.model.Result;
import com.bank.bankSystem.model.Result.ReturnValue;
import com.bank.bankSystem.session.SessionStore;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * 登录模块
 *
 * @author admin
 * @create 2018-05-26 13:47
 **/
@RestController
@RequestMapping("/api/login")
@Api(description = "login")
public class LoginController {

    @Autowired
    private AccountMapper accountMapper;

    @RequestMapping("/login")
    @ResponseBody
    public Result login(HttpServletRequest request, LoginModel loginModel) {

        Account account = accountMapper.findByNumber(loginModel.getAccountNumber());
        if (account == null) {
            return new Result(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        if (!Objects.equals(account.getPin(), loginModel.getPin())) {
            return new Result(Result.ReturnValue.FAILURE, "your pin is wrong");
        }
        HttpSession session = request.getSession();
        session.setAttribute(Account.SESSION_ATTR, loginModel);
        SessionStore.getInstance().addUser(session.getId(), session);
        return new Result(ReturnValue.SUCCESS, "");
    }

    @PostMapping(value = "/logout")
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Account.SESSION_ATTR);
        SessionStore.getInstance().removeUser(request.getSession().getId());
        return new Result<>(Result.ReturnValue.SUCCESS);
    }
}
