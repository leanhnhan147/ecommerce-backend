package com.ecommerce.backend.service;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class OTPService {

    private final SecureRandom secureRandom;
    private List<Integer> numberRand = new ArrayList<>();

    public OTPService() throws NoSuchAlgorithmException {
        secureRandom = SecureRandom.getInstance("SHA1PRNG");
        for(int i = 0; i < 10;i++){
            numberRand.add(i);
        }
    }
    public synchronized String generate(int maxLength) {
        final StringBuilder otp = new StringBuilder(maxLength);
        for (int i = 0; i < maxLength; i++) {
            otp.append(secureRandom.nextInt(9));
        }
        return otp.toString();
    }
}
