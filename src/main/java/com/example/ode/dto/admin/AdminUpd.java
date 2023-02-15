package com.example.ode.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: lyl
 * @Description:
 * @Date: 2023-01-29 20:53
 **/

@Data
public class AdminUpd extends AdminIns{

    /**
     * id
     */
    @ApiModelProperty(value = "用户Id")
    @NotBlank(message = "id不能为空")
    private Long id;



    /**
     * 角色（0：管理员，1：服务员，2：后厨）
     */
    @ApiModelProperty(value = "角色（0：管理员，1：服务员，2：后厨）")
    @NotBlank(message = "请选择角色")
    private Integer role;

    /**
     * 是否锁定（0：否，1：是）
     */
    @ApiModelProperty(value = "是否锁定（0：否，1：是）")
    private Integer isLock;

    /**
     * 是否生效（0：否，1：是）
     */
    @ApiModelProperty(value = "是否生效（0：否，1：是）")
    private Integer isVal;


    /**
     * 头像路径
     */
    @ApiModelProperty(value = "头像路径")
    private String pic;

}
