package com.example.ode.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.ode.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ode.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    IPage<UserVO> selectMyPage(IPage<UserEntity> page, @Param(Constants.WRAPPER) Wrapper<UserEntity> queryWrapper);
}
