package com.example.ode.dao;

import com.example.ode.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
	
}
