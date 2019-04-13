package com.gr232.restaurantadmin.helpers;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public final class EncryptionHelper {
    private static final String ALGORITHM = "AES";

    private byte[] key;
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;

    private void initData(byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        cipher = Cipher.getInstance(ALGORITHM);
    }

    public EncryptionHelper(byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.key = key;
        initData(key);
    }

    public EncryptionHelper(String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.key = key.getBytes();
        initData(this.key);
    }

    public byte[] encrypt(byte[] plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(plainText);
    }

    public byte[] decrypt(byte[] cipherText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(cipherText);
    }
}
