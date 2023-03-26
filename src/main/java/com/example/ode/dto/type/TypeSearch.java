package com.example.ode.dto.type;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TypeSearch {


    /**
     * 分类名
     */
    private String name;

    /**
     * 当前页码，用于分页
     */
    @NotNull(message = "当前页码不能为空")
    @Min(value = 1,message = "当前页码必须是正数")
    private Integer pageNum;

    /**
     * 当前页大小
     */
    @NotNull(message = "当前页大小不能为空")
    @Min(value = 1,message = "当前页大小必须是正数")
    private Integer pageSize;
}
