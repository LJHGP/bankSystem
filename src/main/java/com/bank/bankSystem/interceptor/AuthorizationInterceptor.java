package com.bank.bankSystem.interceptor;


import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.mapper.AccountMapper;
import com.bank.bankSystem.model.LoginModel;
import com.bank.bankSystem.session.SessionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;


@Component("authorizationInterceptor")
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AccountMapper accountMapper;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getSession().getId();
        HttpSession session = SessionStore.getInstance().getSession(token);
        if (Objects.isNull(session)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        LoginModel loginModel = (LoginModel) session.getAttribute(Account.SESSION_ATTR);
        LoginModel requestLoginModel = (LoginModel) request.getSession().getAttribute(Account.SESSION_ATTR);
        if (loginModel == null || loginModel.getAccountNumber() == null || requestLoginModel == null ||
                loginModel.getAccountNumber().trim().isEmpty() || !Objects.equals(loginModel.getAccountNumber(), requestLoginModel.getAccountNumber())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        Account account = accountMapper.findByNumber(loginModel.getAccountNumber());
        if (Objects.isNull(account)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        return true;
    }
}
