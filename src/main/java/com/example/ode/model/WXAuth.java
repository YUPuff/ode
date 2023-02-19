package com.example.ode.model;

import lombok.Data;

@Data
public class WXAuth {

    /**
     * 微信小程序平台自动生成的字符串
     */
    private String encryptedData;
    private String iv;

    /**
     * 第一次登录返回的会话id
     */
    private String key;
}
