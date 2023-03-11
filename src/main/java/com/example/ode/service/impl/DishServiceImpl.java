package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.constant.ResultConstants;
import com.example.ode.dto.dish.DishIns;
import com.example.ode.dto.dish.DishSearch;
import com.example.ode.dto.dish.DishUpd;
import com.example.ode.entity.RecommendEntity;
import com.example.ode.entity.TypeEntity;
import com.example.ode.service.RecommendService;
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

    @Autowired
    private RecommendService recommendService;

    /**
     * 增加单个菜品
     * @param ins
     */
    @Override
    public void add(DishIns ins) {
        // 验证分类是否存在
        verifyTypeExist(ins.getType());
        DishEntity entity = new DishEntity();
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
        if (entity == null)
            throw new BusinessException(ResultConstants.DISH_NO_EXIST_EXCEPTION);
        verifyTypeExist(upd.getType());
        BeanUtils.copyProperties(upd,entity);
        dishDao.updateById(entity);
    }


    /**
     * 分页查询菜品
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

    @Override
    public DishVO getOneDish(Long dishId) {
        return null;
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            // 判断菜品id是否存在
            DishEntity dishEntity = dishDao.selectById(id);
            if (dishEntity == null)
                throw new BusinessException(ResultConstants.DISH_NO_EXIST_EXCEPTION);
            // 删除推荐表中菜品相关所有记录
            recommendService.remove(new LambdaQueryWrapper<RecommendEntity>().eq(RecommendEntity::getDishId,id));
            // 删除菜品表中记录
            dishDao.deleteById(id);
        }
    }

    /**
     * 验证分类是否存在
     * @param typeId
     */
    private void verifyTypeExist(Integer typeId){
        TypeEntity one = typeService.getOne(new LambdaQueryWrapper<TypeEntity>()
                .eq(TypeEntity::getNumber, typeId));
        if (one == null)
            throw new BusinessException(ResultConstants.TYPE_NO_EXIST_EXCEPTION);
    }

}