package com.example.ode.dto.type;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TypeIns {

    /**
     * 分类代号
     */
    @ApiModelProperty(value = "分类代号")
    @NotBlank(message = "分类代号不能为空")
    private Integer number;
    /**
     * 分类名
     */
    @ApiModelProperty(value = "分类名")
    @NotBlank(message = "分类名不能为空")
    private String name;
}
