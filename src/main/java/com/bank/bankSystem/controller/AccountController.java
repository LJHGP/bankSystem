package com.bank.bankSystem.controller;


import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.domain.Record;
import com.bank.bankSystem.mapper.AccountMapper;
import com.bank.bankSystem.mapper.RecordMapper;
import com.bank.bankSystem.model.FundsModel;
import com.bank.bankSystem.model.LoginModel;
import com.bank.bankSystem.model.Result;
import com.bank.bankSystem.model.UserInfo;
import com.bank.bankSystem.service.AccountService;
import com.bank.bankSystem.session.SessionStore;
import com.bank.bankSystem.util.PinUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Controller
@RequestMapping("/api/account")
@Api(description = "account")
public class AccountController {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RecordMapper recordMapper;


    @GetMapping("/query")
    public Result<Account> query(@RequestHeader(value = "token") String token) {
        HttpSession session = SessionStore.getInstance().getSession(token);
        String number = (String) session.getAttribute(Account.SESSION_ATTR);
        Account account = accountMapper.findByNumber(number);
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        return new Result<>(Result.ReturnValue.FAILURE, "", account);
    }


    @RequestMapping("/withdraw")
    @ResponseBody
    public Result<Account> withdraw(HttpServletRequest request,String pid, String amount) {
        //String number = (String) session.getAttribute(Account.SESSION_ATTR);
        LoginModel loginObject = PinUtil.getLoginObject(request);
        String number = loginObject.getAccountNumber();
        String pidFromSession = loginObject.getPin();
        Account account = accountMapper.findByNumber(number);
        BigDecimal amountFormat = new BigDecimal(amount);
        if(pid != null && !pid.equals(pidFromSession)){
            return new Result<>(Result.ReturnValue.FAILURE, "your password is wrong");
        }
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        if (Objects.equals(account.getType(), Account.Type.JUNIOR.name()) && amountFormat.doubleValue() > 100) {
            return new Result<>(Result.ReturnValue.FAILURE, Account.Type.JUNIOR.name() + " account Withdrawal does not exceed 100 Euro");

        }
        if (Objects.equals(account.getType(), Account.Type.SAVER.name()) && amountFormat.doubleValue() > 200) {
            return new Result<>(Result.ReturnValue.FAILURE, Account.Type.SAVER.name() + " account Withdrawal does not exceed 200 Euro");
        }
        if (Objects.equals(account.getType(), Account.Type.CURRENT.name()) && amountFormat.doubleValue() > 300) {
            return new Result<>(Result.ReturnValue.FAILURE, Account.Type.CURRENT.name() + " account Withdrawal does not exceed 300 Euro");
        }
        if (Objects.equals(account.getType(), Account.Type.JUNIOR.name()) || Objects.equals(account.getType(), Account.Type.SAVER.name())) {
            if (amountFormat.doubleValue() > account.getBalance().doubleValue()) {
                return new Result<>(Result.ReturnValue.FAILURE, "your account can not negative balance");
            }
        }
        if (Objects.equals(account.getType(), Account.Type.CURRENT.name())) {
            if ((account.getBalance().subtract(amountFormat)).doubleValue() < -100) {
                return new Result<>(Result.ReturnValue.FAILURE, "your account can not exceeding overdraft limit 100 Euro");
            }
        }
        account.setBalance(account.getBalance().subtract(amountFormat));
        accountMapper.update(account);
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setAmount(amountFormat);
        record.setNumber(number);
        record.setType(Record.Type.withdraw.name());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        return new Result<>(Result.ReturnValue.SUCCESS, "", account);
    }


    @RequestMapping("/deposited")
    @ResponseBody
    public Result<Account> deposited(HttpServletRequest request,String pid, String amount,String depositType) {
        HttpSession session = request.getSession();
        //HttpSession session = SessionStore.getInstance().getSession(token);
        FundsModel drawFunds = new FundsModel();
        LoginModel loginModel = (LoginModel) session.getAttribute(Account.SESSION_ATTR);
        String pidFromSession = loginModel.getPin();
        String number = loginModel.getAccountNumber();
        Account account = accountMapper.findByNumber(number);
        if (number == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        if(pid != null && !pid.equals(pidFromSession)){
            return new Result<>(Result.ReturnValue.FAILURE, "your password is wrong");
        }
        if("cash".equals(depositType)){//现金
            account.setBalance(account.getBalance().add(new BigDecimal(amount)));
        }else if("check".equals(depositType)){
            account.setUnClearedBalance(account.getUnClearedBalance().add(new BigDecimal(amount)));
        }
        account.setNumber(number);
        account.setPin(pid);
        account.setBalance(account.getBalance().add(new BigDecimal(amount)));
        accountMapper.update(account);
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setAmount(drawFunds.getAmount());
        record.setAmount(new BigDecimal(amount));
        record.setNumber(number);
        record.setType(Record.Type.deposited.name());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        return new Result<>(Result.ReturnValue.SUCCESS, "", account);
    }

    @PostMapping("/cheque")
    public Result<Account> cheque(@RequestBody FundsModel drawFunds, @RequestHeader(value = "token") String token) {
        HttpSession session = SessionStore.getInstance().getSession(token);
        String number = (String) session.getAttribute(Account.SESSION_ATTR);
        Account account = accountMapper.findByNumber(number);
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        account.setUnClearedBalance(account.getUnClearedBalance().add(drawFunds.getAmount()));
        accountMapper.update(account);
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setAmount(drawFunds.getAmount());
        record.setNumber(number);
        record.setType(Record.Type.cheque.name());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account);
    }


    @RequestMapping("/clearFounds")
    @ResponseBody
    public Result<Account> clearFounds(HttpServletRequest request,String pid) {
        //HttpSession session = SessionStore.getInstance().getSession(token);
        LoginModel loginObject = PinUtil.getLoginObject(request);
        String number = loginObject.getAccountNumber();
        String pidFromSession = loginObject.getPin();
        if(pid != null && !pid.equals(pidFromSession)){
            return new Result<>(Result.ReturnValue.FAILURE, "your password is wrong");
        }
        Account account = accountMapper.findByNumber(number);
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        BigDecimal unClearedBalance = account.getUnClearedBalance();
        account.setBalance(account.getBalance().add(account.getUnClearedBalance()));
        account.setUnClearedBalance(BigDecimal.ZERO);
        accountMapper.update(account);
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setAmount(unClearedBalance);
        record.setNumber(number);
        record.setType(Record.Type.clear_Founds.name());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account);
    }

    @PostMapping("/close")
    public Result<Account> close(@RequestHeader(value = "token") String token) {
        HttpSession session = SessionStore.getInstance().getSession(token);
        String number = (String) session.getAttribute(Account.SESSION_ATTR);
        Account account = accountMapper.findByNumber(number);
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        if (account.getBalance().doubleValue() != 0) {
            return new Result<>(Result.ReturnValue.FAILURE, "your balance is not cleared");
        }
        account.setStatus(Account.Status.CLOSED.name());
        accountMapper.update(account);
        return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account);
    }

    @PostMapping("/unSuspended")
    public Result<Account> unSuspended(@RequestHeader(value = "token") String token) {
        HttpSession session = SessionStore.getInstance().getSession(token);
        String number = (String) session.getAttribute(Account.SESSION_ATTR);
        Account account = accountMapper.findByNumber(number);
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        account.setStatus(Account.Status.NORMAL.name());
        accountMapper.update(account);
        return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account);
    }

    @RequestMapping("/recordList")
    @ResponseBody
    public List recordList(HttpServletRequest request){
        LoginModel loginObject = PinUtil.getLoginObject(request);
        List<Record> all = recordMapper.findAll(loginObject.getAccountNumber());
        return all;
    }

    @RequestMapping("/clearFoundsAmount")
    @ResponseBody
    public Result clearFoundsAmount(HttpServletRequest request){
        LoginModel loginObject = PinUtil.getLoginObject(request);
        String number = loginObject.getAccountNumber();
        if(number != null){
            Account account = accountMapper.findByNumber(number);
            return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account.getUnClearedBalance());
        }else{
            return new Result<>(Result.ReturnValue.SUCCESS, "account is null,please login against");
        }

    }

    @RequestMapping("/accountList")
    @ResponseBody
    public List accountList(HttpServletRequest request){
        LoginModel loginObject = PinUtil.getLoginObject(request);
        List<UserInfo> userInfo = accountMapper.findUserInfo(loginObject.getAccountNumber());
        return userInfo;
    }
}
