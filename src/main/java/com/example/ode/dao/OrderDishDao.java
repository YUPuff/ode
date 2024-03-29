package com.example.ode.dao;

import com.example.ode.entity.OrderDishEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ode.vo.DishVO;
import com.example.ode.vo.OrderDishVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Mapper
public interface OrderDishDao extends BaseMapper<OrderDishEntity> {

    List<DishVO> getTop5Dishes(String start,String end);

    List<OrderDishVO> getToDo(Integer status);
}
