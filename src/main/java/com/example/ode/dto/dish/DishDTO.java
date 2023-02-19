package com.example.ode.dto.dish;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DishDTO {

    @NotNull(message = "菜品id不能为空")
    private Integer id;

    @NotNull(message = "菜品数量不能为空")
    private Integer amount;
}
