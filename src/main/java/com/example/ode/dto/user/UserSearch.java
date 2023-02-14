package com.example.ode.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description: 模糊查询用户
 * @Date: 2023-02-08 17:40
 **/

@Data
public class UserSearch {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     * 当前页码，用于分页
     */
    private Integer pageNum;



}
