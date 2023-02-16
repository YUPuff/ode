package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.constant.ResultConstant;
import com.example.ode.dto.type.TypeIns;
import com.example.ode.dto.type.TypeSearch;
import com.example.ode.dto.type.TypeUpd;
import com.example.ode.entity.DishEntity;
import com.example.ode.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.TypeDao;
import com.example.ode.entity.TypeEntity;
import com.example.ode.service.TypeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeDao, TypeEntity> implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private DishService dishService;


    /**
     * 添加新分类，需保证分类代号/分类名均唯一
     * @param ins
     */
    @Override
    public void add(TypeIns ins) {
        verifyBoth(ins);
        TypeEntity entity = new TypeEntity();
        BeanUtils.copyProperties(ins,entity);
        typeDao.insert(entity);
    }

    /**
     * 修改分类，分类需存在
     * 修改分类代号，需保证唯一性，且原代号下没有菜品
     * 修改分类名，保证唯一性
     * @param upd
     */
    @Override
    public void update(TypeUpd upd) {
        // 排除原分类不存在
        TypeEntity oldEntity = typeDao.selectById(upd.getId());
        if (oldEntity == null) throw new BusinessException(ResultConstant.TYPE_NO_EXIST_EXCEPTION);
        // 分情况处理
        if(upd.getNumber().equals(oldEntity.getNumber()) && upd.getName().equals(oldEntity.getName())){
            // 未进行任何修改
            return;
        }else if (upd.getNumber().equals(oldEntity.getNumber())){
            // 只修改分类名，只需要验证新分类名具有唯一性
            TypeEntity entity = typeDao.selectOne(new LambdaQueryWrapper<TypeEntity>().eq(TypeEntity::getName,upd.getName()));
            if (entity != null) throw new BusinessException(ResultConstant.TYPE_EXIST_EXCEPTION);

        }else if (upd.getName().equals(oldEntity.getName())){
            // 只修改分类代号，需要验证新分类代号具有唯一性，同时将菜品表中的分类代号修改为新代号
            TypeEntity entity = typeDao.selectOne(new LambdaQueryWrapper<TypeEntity>().eq(TypeEntity::getNumber,upd.getNumber()));
            if (entity != null) throw new BusinessException(ResultConstant.TYPE_EXIST_EXCEPTION);
            dishService.updateForTypeChange(oldEntity.getNumber(),upd.getNumber());
        }else{
            // 同时修改两者，都需要验证唯一性，同时将菜品表中的分类代号修改为新代号
            verifyBoth(upd);
            dishService.updateForTypeChange(oldEntity.getNumber(),upd.getNumber());
        }
        // 最后修改type表中的内容
        BeanUtils.copyProperties(upd,oldEntity);
        typeDao.updateById(oldEntity);
    }

    /**
     * 删除分类，分类需存在
     * 只有当分类下没有菜品时才能成功删除分类
     */
    @Override
    public void delete(Integer id) {
        // 排除原分类不存在
        TypeEntity entity = typeDao.selectById(id);
        if (entity == null) throw new BusinessException(ResultConstant.TYPE_NO_EXIST_EXCEPTION);
        // 验证该分类下没有菜品
        DishEntity one = dishService.getOne(new LambdaQueryWrapper<DishEntity>().eq(DishEntity::getType, entity.getNumber()));
        if (one != null) throw new BusinessException(ResultConstant.TYPE_HAS_EXCEPTION);
        typeDao.deleteById(id);
    }

    /**
     * 查看某一分类详细信息
     * @param id
     * @return
     */
    @Override
    public TypeEntity getOneType(Integer id) {
        return typeDao.selectById(id);
    }

    /**
     * 分页查询分类
     * @param search
     * @return
     */
    @Override
    public MyPage<TypeEntity> getTypes(TypeSearch search) {
        IPage<TypeEntity> page = new Page<>(search.getPageNum(),search.getPageSize());
        IPage<TypeEntity> typePage = typeDao.selectPage(page,new LambdaQueryWrapper<TypeEntity>()
                .like(TypeEntity::getName,search.getName())
                .like(TypeEntity::getNumber,search.getNumber()));
        MyPage<TypeEntity> myPage = MyPage.createPage(typePage);
        return myPage;
    }

    /**
     * 验证分类代号和分类名是否同时具有唯一性
     * @param ins
     */
    private void verifyBoth(TypeIns ins){
        TypeEntity typeEntity = typeDao.selectOne(new LambdaQueryWrapper<TypeEntity>()
                                                        .eq(TypeEntity::getName, ins.getName())
                                                        .or()
                                                        .eq(TypeEntity::getNumber, ins.getNumber()));
        if (typeEntity != null) throw new BusinessException(ResultConstant.TYPE_EXIST_EXCEPTION);
    }
}