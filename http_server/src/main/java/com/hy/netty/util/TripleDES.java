/*
 * Decompiled with CFR 0_134.
 */
package com.hy.netty.util;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;

public class TripleDES {
    private static final String token = "c88a843557fd9ee80cfb986be487adce";

    public static byte[] encrypt(String message) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digestOfPassword = md.digest(token.getBytes());
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        int j = 0;
        int k = 16;
        while (j < 8) {
            keyBytes[k++] = keyBytes[j++];
        }
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
        String s1 = "12345678";
        byte[] bytes = s1.getBytes();
        IvParameterSpec iv = new IvParameterSpec(bytes);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(1, (Key)key, iv);
        byte[] plainTextBytes = message.getBytes("utf-8");
        byte[] cipherText = cipher.doFinal(plainTextBytes);
        return Base64A.encode(cipherText, 0);
    }

    public static String decrypt(byte[] message) throws Exception {
        byte[] values = Base64A.decode(message, 0);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digestOfPassword = md.digest(token.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        int j = 0;
        int k = 16;
        while (j < 8) {
            keyBytes[k++] = keyBytes[j++];
        }
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
        String s1 = "12345678";
        byte[] bytes = s1.getBytes();
        IvParameterSpec iv = new IvParameterSpec(bytes);
        Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decipher.init(2, (Key)key, iv);
        byte[] plainText = decipher.doFinal(values);
        return new String(plainText, "UTF-8");
    }

    public static String decrypts(byte[] message) throws Exception {
        byte[] values = Base64A.decode(message, 0);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digestOfPassword = md.digest(token.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        int j = 0;
        int k = 16;
        while (j < 8) {
            keyBytes[k++] = keyBytes[j++];
        }
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
        String s1 = "12345678";
        byte[] bytes = s1.getBytes();
        IvParameterSpec iv = new IvParameterSpec(bytes);
        Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decipher.init(2, (Key)key, iv);
        byte[] plainText = decipher.doFinal(values);
        return new String(plainText, "UTF-8");
    }

    public static String replaceNewLine(String strText) {
        String strResult = "";
        int intStart = 0;
        int intLoc = strText.indexOf("\n", intStart);
        while (intLoc != -1) {
            strResult = strResult + strText.substring(intStart, intLoc - 1);
            intStart = intLoc + 1;
            intLoc = strText.indexOf("\n", intStart);
        }
        strResult = strResult + strText.substring(intStart, strText.length());
        return strResult;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        for (byte aB : b) {
            String stmp = Integer.toHexString(aB & 255);
            hs = stmp.length() == 1 ? hs + "0" + stmp : hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if (b.length % 2 != 0) {
            throw new IllegalArgumentException("Length is not even");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte)Integer.parseInt(item, 16);
        }
        return b2;
    }
}

