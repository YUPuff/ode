package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.common.MyPage;
import com.example.ode.dto.type.TypeIns;
import com.example.ode.dto.type.TypeSearch;
import com.example.ode.dto.type.TypeUpd;
import com.example.ode.entity.TypeEntity;

import java.util.List;


/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
public interface TypeService extends IService<TypeEntity> {

    void add(TypeIns ins);

    void update(TypeUpd upd);

    void delete(Integer id);

    TypeEntity getOneType(Integer id);

    MyPage<TypeEntity> getTypes(TypeSearch search);

}

