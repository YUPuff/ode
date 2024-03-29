package com.example.ode.dao;

import com.example.ode.entity.RecommendEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ode.vo.RecommendVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yilin
 * @date 2023-03-06 17:26:49
 */
@Mapper
public interface RecommendDao extends BaseMapper<RecommendEntity> {

    List<RecommendVO> getRecordsForUser(Long userId);
	
}
