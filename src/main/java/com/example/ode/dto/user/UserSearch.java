package com.example.ode.dto.user;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * @Author: lyl
 * @Description: 模糊查询用户
 * @Date: 2023-02-08 17:40
 **/

@Data
public class UserSearch {

    /**
     * 用户名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 当前页码，用于分页
     */
    @NotNull(message = "当前页码不能为空")
    @Min(value = 1,message = "当前页码必须是正数")
    private Integer pageNum;


    /**
     * 当前页大小
     */
    @NotNull(message = "当前页大小不能为空")
    @Min(value = 1,message = "当前页大小必须是正数")
    private Integer pageSize;

}
