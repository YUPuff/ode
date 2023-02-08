package com.example.ode.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description:
 * @Date: 2023-01-29 20:53
 **/

public class AdminUpd {

    /**
     * id
     */
    @ApiModelProperty(value = "用户Id")
    @NotBlank(message = "请输入id")
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "请输入用户名")
    @Length(message = "用户名不能超过{max}个字符",max = 30)
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "请输入密码")
    @Length(message = "密码不能超过{max}个字符",max = 30)
    private String password;

    /**
     * 角色代号（0：普通用户，1：管理员）
     */
    @ApiModelProperty(value = "角色代号（0：普通用户，1：管理员）")
    private Integer role;

    /**
     * 是否锁定（0：否，1：是）
     */
    @ApiModelProperty(value = "是否锁定（0：否，1：是）")
    private Integer isLock;


    /**
     * 头像路径
     */
    @ApiModelProperty(value = "头像路径")
    private String pic;

}
