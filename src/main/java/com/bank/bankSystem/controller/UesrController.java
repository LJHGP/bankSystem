package com.bank.bankSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author admin
 * @create 2018-05-28 6:43
 **/
@Controller
@RequestMapping("/")
public class UesrController {

    @RequestMapping("/main")
    public String index(){
        return "/main";
    }
}
