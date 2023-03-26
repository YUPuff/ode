package com.example.ode.dto.type;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TypeIns {

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    @Length(message = "分类名不能超过{max}个字符",max = 10)
    private String name;
}
