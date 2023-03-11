package com.example.ode.service;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ode.constant.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

@Component
public class WxService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 对前端发送的数据先进行解密，得到用户详细信息
     * @param encryptedData
     * @param iv
     * @param key
     * @return
     * @throws Exception
     */
    public String wxDecrypt(String encryptedData,String iv,String s) throws Exception {
        // 将生成的jsonStr转换成对象
        JSONObject object = JSON.parseObject(s);
        String sessionKey = (String) object.get("session_key");
        byte[] encryptedData_b = Base64.decode(encryptedData);
        byte[] iv_b = Base64.decode(iv);
        byte[] sessionKey_b = Base64.decode(sessionKey);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv_b);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(sessionKey_b,"AES");
        cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
        return new String(cipher.doFinal(encryptedData_b),"UTF-8");
    }
}
