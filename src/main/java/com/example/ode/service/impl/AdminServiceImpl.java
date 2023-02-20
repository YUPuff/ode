package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.constant.ResultConstant;
import com.example.ode.dao.AdminDao;
import com.example.ode.dto.admin.AdminIns;
import com.example.ode.dto.admin.AdminSearch;
import com.example.ode.dto.admin.AdminUpd;
import com.example.ode.entity.AdminEntity;
import com.example.ode.enums.IsLock;
import com.example.ode.enums.IsVal;
import com.example.ode.service.UserService;
import com.example.ode.util.ObjectUtils;
import com.example.ode.vo.AdminVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.service.AdminService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminDao, AdminEntity> implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserService userService;

    /**
     * 员工注册
     * @param ins
     */
    @Override
    public void signup(AdminIns ins) {
        AdminEntity entity = adminDao.selectOne(new LambdaQueryWrapper<AdminEntity>().eq(AdminEntity::getName,ins.getName()));
        if (entity == null){
            entity = new AdminEntity();
            BeanUtils.copyProperties(ins,entity);
            adminDao.insert(entity);
            return;
        }
        verify(entity);
        throw new BusinessException("此用户名已存在，请勿重复注册！");
    }

    /**
     * 员工登录
     * 需要满足用户名存在，账户未锁定、已生效，密码正确
     * 登录成功之后生成token存在redis中并将包含token在内的用户信息返回给前端
     * @param ins
     * @return
     */
    @Override
    public AdminVO login(AdminIns ins) {
        AdminEntity entity = adminDao.selectOne(new LambdaQueryWrapper<AdminEntity>().eq(AdminEntity::getName,ins.getName()));
        verify(entity);
        if (!ins.getPassword().equals(entity.getPassword())){
            throw new BusinessException("密码错误，请重试！");
        }
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(entity,adminVO);
        userService.loginForToken(adminVO);
        return adminVO;
    }


    /**
     * 修改员工信息（编辑资料、审核注册、多次密码错误锁定）
     * @param upd
     */
    @Override
    public void update(AdminUpd upd) {
        AdminEntity entity = adminDao.selectById(upd.getId());
        if (upd.getIsLock() == IsLock.NOT_LOCK.getCode())
            entity.setIsLock(upd.getIsLock());
        if (upd.getIsVal() == IsVal.VAL.getCode())
            entity.setIsVal(upd.getIsVal());
        verify(entity);
        BeanUtils.copyProperties(upd,entity);
        adminDao.updateById(entity);
    }



    /**
     * 分页查询员工
     *
     * @param search
     * @return
     */
    @Override
    public MyPage<AdminVO> getAdmins(AdminSearch search) {
        IPage<AdminEntity> page = new Page<>(search.getPageNum(),search.getPageSize());
        IPage<AdminVO> adminPage = adminDao.selectMyPage(page,new LambdaQueryWrapper<AdminEntity>()
                .like(StringUtils.isNotBlank(search.getName()),AdminEntity::getName,search.getName())
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getRole())),AdminEntity::getRole,search.getRole())
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getIsLock())),AdminEntity::getIsLock,search.getIsLock())
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getIsVal())),AdminEntity::getIsVal,search.getIsVal()));
        MyPage<AdminVO> myPage = MyPage.createPage(adminPage);
        return myPage;
    }

    /**
     * 查看某一员工详细信息
     * @param id
     * @return
     */
    @Override
    public AdminVO getOneAdmin(Integer id) {
        AdminEntity entity = adminDao.selectById(id);
        if (entity == null)
            throw new BusinessException(ResultConstant.USER_NO_EXIST_EXCEPTION);
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(entity,adminVO);
        return adminVO;
    }


    /**
     * 验证账户是否合理
     * @param entity
     * @return
     */
    private void verify(AdminEntity entity){
        if (entity == null){
            throw new BusinessException(ResultConstant.USER_NO_EXIST_EXCEPTION);
        }
        // 账户被锁定
        else if (entity.getIsLock() == IsLock.LOCK.getCode())
            throw new BusinessException(ResultConstant.USER_IS_LOCK_EXCEPTION);
        // 账户未生效
        else if (entity.getIsVal() == IsVal.NOT_VAL.getCode())
            throw new BusinessException(ResultConstant.USER_IS_VAL_EXCEPTION);
        // 账户被驳回
        else if (entity.getIsVal() == IsVal.REJECT.getCode())
            throw new BusinessException(ResultConstant.USER_REJECT_EXCEPTION+entity.getExtra());
    }
}