package com.bank.bankSystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SequenceMapper {

    String getNextAccountNumber();
}
