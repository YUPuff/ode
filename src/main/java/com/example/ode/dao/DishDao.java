package com.example.ode.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.ode.entity.DishEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ode.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Mapper
public interface DishDao extends BaseMapper<DishEntity> {

    IPage<DishVO> selectMyPage(IPage<DishEntity> page, @Param(Constants.WRAPPER) Wrapper<DishEntity> queryWrapper);
}
