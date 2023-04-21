package com.example.ode.model;

import lombok.Data;

/**
 * 和解密得到的用户详细信息各字段名一致的实体类
 */
@Data
public class WxUserInfo {

    private String openId;

    private String nickName;

    private String gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String unionId;
}