package com.example.pokersc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {
//    public static String sha256(String text) {
//        MessageDigest digest = null;
//        try {
//            digest = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        assert digest != null;
//        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
//        return Base64.getEncoder().encodeToString(hash);
//    }

    public static String sha256(String input) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");
            byte[] digest = md5.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static ResponseEntity<String> headerWrapper(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<String>(body, headers, HttpStatus.CREATED);
    }
}
