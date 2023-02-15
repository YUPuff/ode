package com.example.ode.dto.dish;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description:
 * @Date: 2023-01-29 20:53
 **/

@Data
public class DishUpd extends DishIns{

    @NotBlank(message = "id不能为空")
    private Integer id;
}
