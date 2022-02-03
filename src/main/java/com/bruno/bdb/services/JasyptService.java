package com.bruno.bdb.services;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JasyptService {

    @Value("${jasypt.encryptor.password}")
    private String jasyptKey;

    private AES256TextEncryptor encryptor() {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword(jasyptKey);
        return encryptor;
    }

    public String encrypt(String data) {
        return encryptor().encrypt(data);
    }

    public String decrypt(String data) {
        return encryptor().decrypt(data);
    }


}
