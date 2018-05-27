package com.bank.bankSystem.interceptor;


import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.mapper.AccountMapper;
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
        String token = request.getHeader("token");
        if (token == null || token.trim().isEmpty()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        HttpSession session = SessionStore.getInstance().getSession(token);
        if (Objects.isNull(session)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        String number = (String) session.getAttribute(Account.SESSION_ATTR);
        if (number == null || number.trim().isEmpty()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        Account account = accountMapper.findByNumber(number);
        if (Objects.isNull(account)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        return true;
    }
}
