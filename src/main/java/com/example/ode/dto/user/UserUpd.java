package com.example.ode.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: lyl
 * @Date: 2023-05-10 19:03
 **/

@Data
public class UserUpd {

    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户头像
     */
    private String pic;
}
