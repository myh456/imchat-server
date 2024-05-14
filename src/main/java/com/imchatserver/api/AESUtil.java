package com.imchatserver.api;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;

public class AESUtil {

    private static final String[] keyrings = {
            "7a4d3e5f6b8c9d1a2e0f3b4a5c6d7e8f",
            "1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e",
            "9f8e7d6c5b4a3e2f1d0c9b8a7e6f5d4c",
            "6d5c4b3a2e1f0d9c8b7a6e5f4d3c2b1a",
            "3a4b5c6d7e8f9a0b1c2d3e4f5a6b7c8d",
            "0f1e2d3c4b5a6e7d8c9b0a1f2e3d4c5b",
            "c6d7e8f9a0b1c2d3e4f5a6b7c8d9e0f1",
            "4a5b6c7d8e9f0a1b2c3d4e5f6a7b8c9d",
            "2e3f4d5c6b7a8e9f0d1c2b3a4e5f6d7c",
            "8b9a0e1f2d3c4b5a6e7f8d9c0b1a2e3f",
            "5d6e7f8g9a0b1c2d3e4f5a6b7c8d9e0f",
            "b1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e6"
    };

    public static String encrypt(String word, Timestamp timestamp) throws Exception {
        return encrypt(word, keyrings[(int) (timestamp.getTime() / (3L * 30 * 24 * 60 * 60 * 1000) % 12)]);
    }
    // 加密
    public static String encrypt(String word, String keyStr) throws Exception {
        byte[] key = keyStr.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encrypted = cipher.doFinal(word.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedWord, Timestamp timestamp) throws Exception {
        return decrypt(encryptedWord, keyrings[(int) (timestamp.getTime() / (3L * 30 * 24 * 60 * 60 * 1000) % 12)]);
    }

    // 解密
    public static String decrypt(String encryptedWord, String keyStr) throws Exception {
        byte[] key = keyStr.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedWord));
        return new String(original, StandardCharsets.UTF_8);
    }
}
