package com.example.ode.vo;

import lombok.Data;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description:
 * @Date: 2023-03-17 20:43
 **/

@Data
public class RecommendVO {

    /**
     * 菜品id
     */
    Long dishId;

    /**
     * 菜品名
     */
    String dishName;

    /**
     * 菜品图片路径
     */
    String dishPic;

    /**
     * 总计点菜次数
     */
    Integer amount;
}
