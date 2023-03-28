package com.example.ode.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.ode.entity.AdminEntity;
import com.example.ode.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ode.vo.AdminVO;
import com.example.ode.vo.DishVO;
import com.example.ode.vo.OrderDishVO;
import com.example.ode.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    List<OrderDishVO> selectDishForOrder(@Param("orderId")Long orderId,
                                         @Param("pageNum")Integer pageNum,
                                         @Param("pageSize")Integer pageSize);

//    IPage<OrderVO> selectMyPage(IPage<OrderEntity> page, @Param(Constants.WRAPPER) Wrapper<OrderVO> queryWrapper);
}
