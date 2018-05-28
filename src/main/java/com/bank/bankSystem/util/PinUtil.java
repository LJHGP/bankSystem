package com.bank.bankSystem.util;

import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.model.LoginModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

public class PinUtil {


    public static String createPinNumber() {

        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            Integer n = random.nextInt(10);
            accountNumber.append(n);
        }
        return accountNumber.toString();
    }

    public static LoginModel getLoginObject(HttpServletRequest request){
        HttpSession session = request.getSession();
        LoginModel loginModel = (LoginModel) session.getAttribute(Account.SESSION_ATTR);
        return loginModel;
    }
}
