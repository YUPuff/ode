package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.entity.RecommendEntity;
import com.example.ode.vo.DishVO;

import java.util.List;
import java.util.Map;

/**
 * @author yilin
 * @date 2023-03-06 17:26:49
 */
public interface RecommendService extends IService<RecommendEntity> {

    List<DishVO> recommend(Long userId);
}

