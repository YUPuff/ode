package com.example.ode.dto.dish;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class DishDTO {

    @NotNull(message = "菜品id不能为空")
    private Long id;

    @NotNull(message = "菜品数量不能为空")
    @Min(value = 1,message = "菜品数量必须是正数")
    private Integer amount;
}
