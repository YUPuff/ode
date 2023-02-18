package com.example.ode.dto.type;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TypeIns {

    /**
     * 分类代号
     */
    @NotNull(message = "分类代号不能为空")
    private Integer number;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    private String name;
}
