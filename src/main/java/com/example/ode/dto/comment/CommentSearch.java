package com.example.ode.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private Integer pageNum;

    /**
     * 当前页大小
     */
    private Integer pageSize;

}
