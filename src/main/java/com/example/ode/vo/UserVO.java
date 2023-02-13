package com.example.ode.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description:
 * @Date: 2023-02-08 17:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    @ApiModelProperty(value = "用户Id")
    private Integer id;

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
     * 头像路径
     */
    @ApiModelProperty(value = "头像路径")
    private String pic;

}
