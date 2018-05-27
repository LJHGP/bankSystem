package com.bank.bankSystem.util;

import java.util.Random;

public class PinUtil {


    public static String createPinNumber() {

        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            Integer n = random.nextInt(10);
            accountNumber.append(n);
        }
        return accountNumber.toString();
    }
}
