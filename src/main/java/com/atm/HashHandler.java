package com.atm;

import java.security.*;
import java.util.Arrays;
import java.nio.charset.*;

public class HashHandler {
    public static byte[] hash(String PIN) {
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
        return hashedBytes;
    }

    public static boolean compare(String givenPIN, byte[] expectedHash){
        return Arrays.equals(hash(givenPIN), expectedHash);
    }
}
