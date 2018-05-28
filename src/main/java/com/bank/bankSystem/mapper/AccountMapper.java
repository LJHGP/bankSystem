package com.bank.bankSystem.mapper;

import com.bank.bankSystem.domain.Account;
import com.bank.bankSystem.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AccountMapper {


    void insert(Account account);


    Account findByNumber(@Param("number") String number);


    void update(Account account);

    List<UserInfo> findUserInfo(@Param("number") String number);
}
