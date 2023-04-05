package com.example.ode.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.ode.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ode.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
//    IPage<CommentVO> selectMyPage(@Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize);
}
