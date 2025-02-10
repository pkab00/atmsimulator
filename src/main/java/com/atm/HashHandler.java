package com.atm;

import java.security.*;
import java.nio.charset.*;

public class HashHandler {
    public static String hash(String PIN) {
        String algorithm = "SHA-256";
        Charset charset = StandardCharsets.UTF_8;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hashedBytes = digest.digest(PIN.getBytes(charset));
        String hashedString = new StringBuilder(new String(hashedBytes, charset)).reverse().toString();
        return hashedString;
    }

    public static boolean compare(String givenPIN, String expectedHash){
        return hash(givenPIN).equals(expectedHash);
    }
}
