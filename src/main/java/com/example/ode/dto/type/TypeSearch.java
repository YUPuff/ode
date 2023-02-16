package com.example.ode.dto.type;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TypeSearch {

    /**
     * 分类代号
     */
    @ApiModelProperty(value = "分类代号")
    private Integer number;
    /**
     * 分类名
     */
    @ApiModelProperty(value = "分类名")
    private String name;

    /**
     * 当前页码，用于分页
     */
    private Integer pageNum;

    /**
     * 当前页大小
     */
    private Integer pageSize;
}
