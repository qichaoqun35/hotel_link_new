package com.example.qichaoqun.amerilink.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Encryption {
	
	public static String generateSignature(String method, String reqUrl, String date, String secret) {
        StringBuilder sign = new StringBuilder();
        sign.append(method);
        sign.append(" ");
        sign.append(reqUrl);
        sign.append("\n");
        sign.append(date);
        byte[] sha1 = hmac_sha1(sign.toString(), secret);
        String signature;
        try {
            signature = new String(Base64.encodeBase64(sha1), "UTF-8");
            return signature;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private static byte[] hmac_sha1(String value, String key) {
        try {
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            return mac.doFinal(value.getBytes());
        } catch (Exception e) {
            return null;
        }
    }
}
