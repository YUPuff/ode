package com.example.ode.dto.admin;

import lombok.Data;

import javax.validation.constraints.*;

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
    @NotNull(message = "id不能为空")
    private Long id;



    /**
     * 角色（0：管理员，1：服务员，2：后厨）
     */
    @NotNull(message = "角色类型不能为空")
    @Max(value = 2,message = "角色类型只能是0、1、2")
    @Min(value = 0,message = "角色类型只能是0、1、2")
    private Integer role;

    /**
     * 是否锁定（0：否，1：是）
     */
    @Max(value = 1,message = "是否锁定只能是0或1")
    @Min(value = 0,message = "是否锁定只能是0或1")
    private Integer isLock;

    /**
     * 是否生效（0：否，1：是,2：已驳回）
     */
    @Max(value = 1,message = "是否生效只能是0~2")
    @Min(value = 0,message = "是否生效只能是0~2")
    private Integer isVal;


    /**
     * 头像路径
     */
    private String pic;

}
