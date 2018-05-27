package com.bank.bankSystem.service.impl;

import com.bank.bankSystem.mapper.TestMapper;
import com.bank.bankSystem.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author admin
 * @create 2018-05-27 10:23
 **/
public class TestServiceImpl implements TestService {

    @Autowired
    TestMapper testMapper;


    @Override
    public void getInfo() {
        testMapper.getInfo();
    }
}
