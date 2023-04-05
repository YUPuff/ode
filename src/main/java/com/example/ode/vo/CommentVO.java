package com.example.ode.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lyl
 * @Date: 2023-04-05 20:19
 **/

@Data
public class CommentVO {
    /**
     * 评价Id
     */
    @ApiModelProperty(value = "评价Id")
    private Long id;


    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;
}
