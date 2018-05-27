package com.bank.bankSystem.mapper;

import com.bank.bankSystem.domain.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RecordMapper {

    void insert(Record record);


    List<Record> findAll(@Param("number") String number);

}
