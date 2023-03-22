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
    @NotBlank(message = "用户名不能为空")
    @Length(message = "用户名不能超过{max}个字符",max = 30)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    @Length(message = "密码不能超过{max}个字符",max = 30)
    private String password;






}
