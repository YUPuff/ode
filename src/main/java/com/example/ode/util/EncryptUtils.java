package com.example.ode.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: lyl
 * @Date: 2023-02-20 20:20
 **/

public class EncryptUtils {
    public static String MD5EncryptMethod(String str){
        return DigestUtils.md5Hex(str);
    }

    public static String base64EncryptMethod(byte[] binaryData){
        return Base64.encodeBase64String(binaryData);
    }
}
