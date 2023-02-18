package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.constant.ResultConstant;
import com.example.ode.dto.dish.DishIns;
import com.example.ode.dto.dish.DishSearch;
import com.example.ode.dto.dish.DishUpd;
import com.example.ode.entity.TypeEntity;
import com.example.ode.service.TypeService;
import com.example.ode.util.ObjectUtils;
import com.example.ode.vo.DishVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.DishDao;
import com.example.ode.entity.DishEntity;
import com.example.ode.service.DishService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("dishService")
public class DishServiceImpl extends ServiceImpl<DishDao, DishEntity> implements DishService {

    @Autowired
    private DishDao dishDao;

    @Autowired
    private TypeService typeService;

    /**
     * 增加单个菜品
     * @param ins
     */
    @Override
    public void add(DishIns ins) {
        DishEntity entity = new DishEntity();
        TypeEntity one = typeService.getOne(new LambdaQueryWrapper<TypeEntity>()
                .eq(TypeEntity::getNumber, ins.getType()));
        if (one == null)
            throw new BusinessException(ResultConstant.TYPE_NO_EXIST_EXCEPTION);
        BeanUtils.copyProperties(ins,entity);
        dishDao.insert(entity);
    }

    /**
     * 更新单个菜品信息
     * @param upd
     */
    @Override
    public void update(DishUpd upd) {
        DishEntity entity = dishDao.selectById(upd.getId());
        if (entity == null) throw new BusinessException(ResultConstant.DISH_NO_EXIST_EXCEPTION);
        BeanUtils.copyProperties(upd,entity);
        dishDao.updateById(entity);
    }


    /**
     * 批量删除菜品
     * @param ids
     */
    @Override
    @Transactional
    public void delete(List<Integer> ids) {
        if (dishDao.deleteBatchIds(ids)<=0) throw new BusinessException("删除不存在的菜品，操作失败！");
    }

    /**
     * 分页查询
     * @param search
     * @return
     */
    @Override
    public MyPage<DishVO> getDishes(DishSearch search) {
        IPage<DishEntity> page = new Page<>(search.getPageNum(),search.getPageSize());
        IPage<DishVO> dishPage = dishDao.selectMyPage(page,new LambdaQueryWrapper<DishEntity>()
                .like(StringUtils.isNotBlank(search.getName()),DishEntity::getName,search.getName())
                .ge(StringUtils.isNotBlank(ObjectUtils.toString(search.getMinPrice())),DishEntity::getPrice,search.getMinPrice())
                .le(StringUtils.isNotBlank(ObjectUtils.toString(search.getMaxPrice())),DishEntity::getPrice,search.getMaxPrice())
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getType())),DishEntity::getType,search.getType()));
        MyPage<DishVO> myPage = MyPage.createPage(dishPage);
        return myPage;
    }


    /**
     * 由于分类改变，需要批量修改分类代号
     * @param oldType
     * @param newType
     */
    @Override
    public void updateForTypeChange(Integer oldType, Integer newType) {
        DishEntity entity = new DishEntity();
        entity.setType(newType);
        dishDao.update(entity,new LambdaQueryWrapper<DishEntity>().eq(DishEntity::getType,oldType));
    }


}