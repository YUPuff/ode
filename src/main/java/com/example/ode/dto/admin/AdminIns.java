package com.example.ode.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description: 用户新增类
 * @Date: 2023-01-29 20:53
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminIns {

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
     * 头像路径
     */
    @ApiModelProperty(value = "头像路径")
    private String pic;




}
