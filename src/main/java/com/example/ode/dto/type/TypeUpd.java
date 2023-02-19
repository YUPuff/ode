package com.example.ode.dto.type;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TypeUpd extends TypeIns{

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Integer id;
}
