package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.common.MyPage;
import com.example.ode.dto.dish.DishIns;
import com.example.ode.dto.dish.DishSearch;
import com.example.ode.dto.dish.DishUpd;
import com.example.ode.entity.DishEntity;
import com.example.ode.vo.DishVO;

import java.util.List;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
public interface DishService extends IService<DishEntity> {

    void add(DishIns ins);

    void update(DishUpd upd);

    MyPage<DishVO> getDishes(DishSearch search);

    DishVO getOneDish(Long dishId);

    void delete(List<Long> ids);
}

