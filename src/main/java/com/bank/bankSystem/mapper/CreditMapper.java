package com.bank.bankSystem.mapper;

import com.bank.bankSystem.domain.Credit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface CreditMapper {


    Credit findByName(@Param("name") String name);
}
