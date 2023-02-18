package com.example.ode.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CommentSearch {

    /**
     * 评价类型（1：菜品评价，2：服务评价，3：环境评价）
     */
    private Integer type;

    /**
     * 目标id（如果此条评价是菜品评价的话，此列值不为空）
     */
    private Integer target;


    /**
     * 用户id
     */
    private Long userId;


    /**
     * 等级（0：差，1：中，2：好）
     */
    private Integer level;

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
