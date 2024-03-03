package org.example.laba.security;

import lombok.RequiredArgsConstructor;
import org.example.laba.service.EncodingKeyService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class MD2PasswordEncoder implements PasswordEncoder {
    private final EncodingKeyService encodingKeyService;
    @Override
    public String encode(CharSequence rawPassword) {
        try {
            String key = encodingKeyService.getKey();
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] digest = md.digest((rawPassword.toString() + key).getBytes());
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to encode password", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
