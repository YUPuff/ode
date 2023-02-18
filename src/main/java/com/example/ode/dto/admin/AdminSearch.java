package com.example.ode.dto.admin;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: lyl
 * @Description:
 * @Date: 2023-01-29 20:56
 **/

@Data
public class AdminSearch {


    /**
     * 用户名
     */
    private String name;


    /**
     * 角色（0：管理员，1：服务员，2：后厨）
     */
    private Integer role;

    /**
     * 是否锁定（0：否，1：是）
     */
    private Integer isLock;

    /**
     * 是否生效（0：否，1：是,2：已驳回）
     */
    private Integer isVal;


    /**
     * 当前页码，用于分页
     */
    @NotNull(message = "当前页码不能为空")
    private Integer pageNum;

    /**
     * 当前页大小
     */
    @NotNull(message = "当前页大小不能为空")
    private Integer pageSize;


}
