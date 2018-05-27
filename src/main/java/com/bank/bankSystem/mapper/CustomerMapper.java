package com.bank.bankSystem.mapper;

import com.bank.bankSystem.domain.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface CustomerMapper {


    Customer findByName(@Param("name") String name);


    void insert(Customer customer);
}
