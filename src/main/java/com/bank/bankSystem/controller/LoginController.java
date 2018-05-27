package com.bank.bankSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录模块
 * @author admin
 * @create 2018-05-26 13:47
 **/
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        //TODO
        return "/login";
    }

    @RequestMapping("/index")
    public String index(){
        //TODO
        return "/index";
    }
}
