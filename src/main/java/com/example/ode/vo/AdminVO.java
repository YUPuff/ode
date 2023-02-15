package com.example.ode.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description: 用户视图类
 * @Date: 2023-01-29 20:57
 **/

public class AdminVO extends UserVO{

    /**
     * 角色（0：管理员，1：服务员，2：后厨）
     */
    private Integer role;

    /**
     * 是否生效（0：否，1：是，2：已驳回）
     */
    private Integer isVal;

    /**
     * 是否锁定（0：否，1：是）
     */
    private Integer isLock;
}
