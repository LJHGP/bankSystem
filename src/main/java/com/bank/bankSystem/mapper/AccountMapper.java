package com.bank.bankSystem.mapper;

import com.bank.bankSystem.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountMapper {


    void insert(Account account);


    Account findByNumber(@Param("number") String number);

    void update(Account account);
}
