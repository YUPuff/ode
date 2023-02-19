package com.example.ode.dao;

import com.example.ode.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ode.vo.DishVO;
import com.example.ode.vo.OrderDishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    List<OrderDishVO> selectDishForOrder(@Param("orderId")Long orderId, @Param("pageNum")Integer pageNum);
}
