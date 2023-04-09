package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.entity.DishEntity;
import com.example.ode.entity.RecommendEntity;
import com.example.ode.vo.DishVO;
import com.example.ode.vo.RecommendVO;

import java.util.List;
import java.util.Map;

/**
 * @author yilin
 * @date 2023-03-06 17:26:49
 */
public interface RecommendService extends IService<RecommendEntity> {


    List recommend(Long userId) throws Exception;

    List<RecommendVO> getRecordsForUser(Long userId);
}

