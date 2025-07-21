package com.saucedemo.Test.utils;
import java.util.Random;

public class RandomEmailPass {

    public static String generateRandomEmail() {
        String emailDomain = "@gmail.com";
        String randomString = generateRandomString(4);
        return capitalizeFirstLetter(randomString) + emailDomain;
    }

    public static String generateRandomPassword() {
        return capitalizeFirstLetter(generateRandomString(4)) + "@123"; // Ensures first letter is capital
    }

    private static String generateRandomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return sb.toString();
    }

    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
