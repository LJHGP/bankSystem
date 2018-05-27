package com.bank.bankSystem.controller;


import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.domain.Record;
import com.bank.bankSystem.mapper.AccountMapper;
import com.bank.bankSystem.mapper.RecordMapper;
import com.bank.bankSystem.model.FundsModel;
import com.bank.bankSystem.model.Result;
import com.bank.bankSystem.session.SessionStore;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping("/api/account")
@Api(description = "account")
public class AccountController {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RecordMapper recordMapper;


    @PostMapping("/withdraw")
    public Result<Account> withdraw(@RequestBody FundsModel drawFunds, @RequestHeader(value = "token") String token) {
        HttpSession session = SessionStore.getInstance().getSession(token);
        String number = (String) session.getAttribute(Account.SESSION_ATTR);
        Account account = accountMapper.findByNumber(number);
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        if (Objects.equals(account.getType(), Account.Type.JUNIOR.name()) && drawFunds.getAmount().doubleValue() > 100) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account can not negative balance");

        }
        if (Objects.equals(account.getType(), Account.Type.SAVER.name()) && drawFunds.getAmount().doubleValue() > 200) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account can not negative balance");
        }
        if (Objects.equals(account.getType(), Account.Type.CURRENT.name()) && drawFunds.getAmount().doubleValue() > 300) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account can not negative balance");
        }
        if (Objects.equals(account.getType(), Account.Type.JUNIOR.name()) || Objects.equals(account.getType(), Account.Type.SAVER.name())) {
            if (drawFunds.getAmount().doubleValue() > account.getBalance().doubleValue()) {
                return new Result<>(Result.ReturnValue.FAILURE, "your account can not negative balance");
            }
        }
        if (Objects.equals(account.getType(), Account.Type.CURRENT.name())) {
            if ((account.getBalance().divide(drawFunds.getAmount())).doubleValue() < -100) {
                return new Result<>(Result.ReturnValue.FAILURE, "your account can not exceeding overdraft limit");
            }
        }
        account.setBalance(account.getBalance().divide(drawFunds.getAmount()));
        accountMapper.update(account);
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setAmount(drawFunds.getAmount());
        record.setNumber(drawFunds.getAccountNumber());
        record.setType(Record.Type.withdraw.name());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account);
    }


    @PostMapping("/deposited")
    public Result<Account> deposited(@RequestBody FundsModel drawFunds, @RequestHeader(value = "token") String token) {
        HttpSession session = SessionStore.getInstance().getSession(token);
        String number = (String) session.getAttribute(Account.SESSION_ATTR);
        Account account = accountMapper.findByNumber(number);
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        account.setBalance(account.getBalance().add(drawFunds.getAmount()));
        accountMapper.update(account);
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setAmount(drawFunds.getAmount());
        record.setNumber(drawFunds.getAccountNumber());
        record.setType(Record.Type.deposited.name());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account);
    }

    @PostMapping("/cheque")
    public Result<Account> cheque(@RequestBody FundsModel drawFunds, @RequestHeader(value = "token") String token) {
        HttpSession session = SessionStore.getInstance().getSession(token);
        String number = (String) session.getAttribute(Account.SESSION_ATTR);
        Account account = accountMapper.findByNumber(number);
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        account.setUnClearedBalance(account.getBalance().add(drawFunds.getAmount()));
        accountMapper.update(account);
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setAmount(drawFunds.getAmount());
        record.setNumber(drawFunds.getAccountNumber());
        record.setType(Record.Type.cheque.name());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account);
    }


    @PostMapping("/clearFounds")
    public Result<Account> clearFounds(@RequestParam FundsModel drawFunds, @RequestHeader(value = "token") String token) {
        HttpSession session = SessionStore.getInstance().getSession(token);
        String number = (String) session.getAttribute(Account.SESSION_ATTR);
        Account account = accountMapper.findByNumber(number);
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        account.setBalance(account.getBalance().add(account.getUnClearedBalance()));
        account.setUnClearedBalance(BigDecimal.ZERO);
        accountMapper.update(account);
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setAmount(drawFunds.getAmount());
        record.setNumber(drawFunds.getAccountNumber());
        record.setType(Record.Type.clear_Founds.name());
        record.setCreateTime(new Date());
        recordMapper.insert(record);
        return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account);
    }

    @PostMapping("/close")
    public Result<Account> close(@RequestBody FundsModel drawFunds, @RequestHeader(value = "token") String token) {
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
    public Result<Account> unSuspended(@RequestBody FundsModel drawFunds) {
        Account account = accountMapper.findByNumber(drawFunds.getAccountNumber());
        if (account == null) {
            return new Result<>(Result.ReturnValue.FAILURE, "your account is not exist");
        }
        if (!Objects.equals(account.getPin(), drawFunds.getPin())) {
            return new Result<>(Result.ReturnValue.FAILURE, "your pin is wrong");
        }
        if (account.getBalance().doubleValue() != 0) {
            return new Result<>(Result.ReturnValue.FAILURE, "your balance is not cleared");
        }
        account.setStatus(Account.Status.NORMAL.name());
        accountMapper.update(account);
        return new Result<>(Result.ReturnValue.SUCCESS, "your pin is wrong", account);
    }
}
