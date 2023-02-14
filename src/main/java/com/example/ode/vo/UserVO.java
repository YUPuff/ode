package com.example.ode.vo;

import com.example.ode.common.WxUserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description: 用于前端渲染类
 * @Date: 2023-02-08 17:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    @ApiModelProperty(value = "自动生成的id")
    private Long id;

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


    // 扩展属性
    /**
     * 后端生成的token令牌，用于判断当前用户登录状态
     */
    private String token;


}
