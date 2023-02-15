package com.example.ode.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.ode.entity.AdminEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ode.vo.AdminVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yilin
 * @date 2023-02-08 17:35:28
 */
@Mapper
public interface AdminDao extends BaseMapper<AdminEntity> {

    IPage<AdminVO> selectMyPage(IPage<AdminEntity> page, @Param(Constants.WRAPPER) Wrapper<AdminEntity> queryWrapper);
}
