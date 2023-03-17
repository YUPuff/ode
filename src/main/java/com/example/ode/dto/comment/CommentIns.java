package com.example.ode.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CommentIns {

    /**
     * 对不同菜品以及环境、服务评价的集合
     * 单个评价组成：类型-（目标对象id）-等级
     */
    @NotEmpty(message = "未进行任何评价")
    private List<String> items;

    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 内容
     */
    private String content;



}
