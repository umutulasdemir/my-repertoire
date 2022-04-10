package com.example.sahneovevi.Utility;

import java.util.Random;

public class RandomString {
    public static String getSaltString(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUWYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random random = new Random();
        while(salt.length()<18){
            int index = (int) (random.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
