package com.bank.bankSystem.controller;

import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.mapper.AccountMapper;
import com.bank.bankSystem.model.Result;
import com.bank.bankSystem.session.SessionStore;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/login")
    public Map login() {
        Map map = new HashMap();
        // TODO Auto-generated method stub
        String s="b";


        map.put("success",s);

        /*Account account = accountMapper.findByNumber(loginModel.getAccountNumber());
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        if (!Objects.equals(account.getPin(), loginModel.getPin())) {
            return new Result<>(Result.ReturnValue.FAILURE, "your pin is wrong");
        }
        HttpSession session = request.getSession();
        session.setAttribute(Account.SESSION_ATTR, loginModel.getAccountNumber());
        SessionStore.getInstance().addUser(session.getId(), session);*/
        return map;
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request, @RequestParam String token) {
        request.getSession().removeAttribute(Account.SESSION_ATTR);
        SessionStore.getInstance().removeUser(token);
        return new Result<>(Result.ReturnValue.SUCCESS);
    }
}
