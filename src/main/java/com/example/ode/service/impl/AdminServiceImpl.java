package com.example.ode.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.constant.RedisConstants;
import com.example.ode.constant.ResultConstants;
import com.example.ode.dao.AdminDao;
import com.example.ode.dto.admin.AdminIns;
import com.example.ode.dto.admin.AdminSearch;
import com.example.ode.dto.admin.AdminUpd;
import com.example.ode.entity.AdminEntity;
import com.example.ode.entity.OrderEntity;
import com.example.ode.enums.IsLock;
import com.example.ode.enums.IsVal;
import com.example.ode.enums.OrderStatus;
import com.example.ode.enums.Role;
import com.example.ode.service.OrderService;
import com.example.ode.service.UserService;
import com.example.ode.util.EncryptUtils;
import com.example.ode.util.LocalDateTimeUtils;
import com.example.ode.util.ObjectUtils;
import com.example.ode.vo.AdminVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.service.AdminService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.shiro.SecurityUtils.getSubject;


@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminDao, AdminEntity> implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 员工注册
     * @param ins
     */
    @Override
    public void signup(AdminIns ins) {
        AdminEntity entity = adminDao.selectOne(new LambdaQueryWrapper<AdminEntity>().eq(AdminEntity::getName,ins.getUsername()));
        if (entity == null){
            entity = new AdminEntity();
            entity.setName(ins.getUsername());
            entity.setPassword(EncryptUtils.MD5EncryptMethod(ins.getPassword()));
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
        AdminEntity entity = adminDao.selectOne(new LambdaQueryWrapper<AdminEntity>().eq(AdminEntity::getName,ins.getUsername()));
        verify(entity);
        if (!ins.getPassword().equals(entity.getPassword())){
            throw new BusinessException("密码错误，请重试！");
        }
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(entity,adminVO);
        userService.loginForToken(adminVO);
        return adminVO;
    }

    @Override
    public void logout(String token) {
        getSubject().logout();
        redisTemplate.delete(RedisConstants.TOKEN+token);
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
        // 确保新用户名唯一
        if (!entity.getName().equals(upd.getName())){
            AdminEntity newEntity = adminDao.selectOne(new LambdaQueryWrapper<AdminEntity>()
                    .eq(AdminEntity::getName,upd.getName()));
            if(newEntity != null)
                throw new BusinessException(ResultConstants.USER_EXIST_EXCEPTION);
        }
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
    public AdminVO getOneAdmin(Long id) {
        AdminEntity entity = adminDao.selectById(id);
        if (entity == null)
            throw new BusinessException(ResultConstants.USER_NO_EXIST_EXCEPTION);
        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(entity,adminVO);
        return adminVO;
    }

    /**
     * 通过token获取已登录用户信息
     * @param token
     * @return
     */
    @Override
    public AdminVO getAdminByToken(String token) {
//        return getOneAdmin(2L);
        String json = redisTemplate.opsForValue().get(RedisConstants.TOKEN+token);
        return JSON.parseObject(json,AdminVO.class);
    }

    /**
     * 获取员工数、上周和本周经营数据
     * @return
     */
    @Override
    public Map<String, Object> getStatistics() {
        Long admin = adminDao.selectCount(new LambdaQueryWrapper<AdminEntity>().eq(AdminEntity::getIsVal,IsVal.VAL.getCode()));
        // 本周开始时间
        Date weekStart = LocalDateTimeUtils.convertToDate(LocalDateTimeUtils.weekStartTime());
        // 本周结束时间
        Date weekEnd = LocalDateTimeUtils.convertToDate(LocalDateTimeUtils.weekEndTime());
        // 上周开始时间
        Date lastWeekStart = LocalDateTimeUtils.convertToDate(LocalDateTimeUtils.lastWeekStartTime());
        // 上周结束时间
        Date lastWeekEnd = LocalDateTimeUtils.convertToDate(LocalDateTimeUtils.lastWeekEndTime());
        List<OrderEntity> weekList = orderService.list(new LambdaQueryWrapper<OrderEntity>()
                .ge(OrderEntity::getAddTime, weekStart)
                .le(OrderEntity::getAddTime, weekEnd)
                .eq(OrderEntity::getStatus, OrderStatus.FINISHED.getCode()));
        List<OrderEntity> lastWeekList = orderService.list(new LambdaQueryWrapper<OrderEntity>()
                .ge(OrderEntity::getAddTime, lastWeekStart)
                .le(OrderEntity::getAddTime, lastWeekEnd)
                .eq(OrderEntity::getStatus, OrderStatus.FINISHED.getCode()));
        // 本周订单数
        Integer weekOrder =  weekList.size();
        // 上周订单数
        Integer lastWeekOrder = lastWeekList.size();
        // 计算本周营业额
        BigDecimal weekTotal = countTotal(weekList);
        // 计算上周营业额
        BigDecimal lastWeekTotal = countTotal(lastWeekList);
        Map<String,Object> map = new HashMap<>();
        map.put("admin",admin);
        map.put("order",weekOrder);
        map.put("lastOrder",lastWeekOrder);
        map.put("total",weekTotal.doubleValue());
        map.put("lastTotal",lastWeekTotal.doubleValue());
        return map;
    }

    @Override
    public Map<String,List> get12Data() {
        Map<String,List> map = new HashMap<>();
        List<Integer> order = new ArrayList<>();
        List<Double> total = new ArrayList<>();
        LocalDateTime yearStart = LocalDateTimeUtils.yearStartTime();
        for(long i=0;i<12;i++){
            Date start = LocalDateTimeUtils.convertToDate(LocalDateTimeUtils.nMonthStartTime(yearStart, i));
            Date end = LocalDateTimeUtils.convertToDate(LocalDateTimeUtils.nMonthEndTime(yearStart, i));
            List<OrderEntity> orders = orderService.list(new LambdaQueryWrapper<OrderEntity>()
                    .ge(OrderEntity::getAddTime, start)
                    .le(OrderEntity::getAddTime, end)
                    .eq(OrderEntity::getStatus, OrderStatus.FINISHED.getCode()));
            order.add(orders.size());
            total.add(countTotal(orders).doubleValue());
        }
        map.put("order",order);
        map.put("total",total);
        return map;
    }

    @Override
    public Map<String, Object> index(Long id) {
        AdminEntity entity = adminDao.selectById(id);
        if (entity == null)
            throw new BusinessException(ResultConstants.USER_NO_EXIST_EXCEPTION);
        Long day = LocalDateTimeUtils.getBetweenDay(entity.getAddTime());
        Long order = orderService.count(new LambdaQueryWrapper<OrderEntity>()
                .eq(OrderEntity::getStatus, OrderStatus.NOT_START.getCode())
                .or()
                .eq(OrderEntity::getStatus, OrderStatus.ING.getCode()));
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        map.put("order",order);
        if(entity.getRole() == Role.ADMIN.getCode()){
            Long people = adminDao.selectCount(new LambdaQueryWrapper<AdminEntity>().eq(AdminEntity::getIsVal, IsVal.NOT_VAL));
            map.put("people",people);
        }
        return map;
    }


    /**
     * 统计列表中订单总金额之和
     * @param list
     * @return
     */
    public BigDecimal countTotal(List<OrderEntity> list){
        BigDecimal total = new BigDecimal(0);
        for(OrderEntity item:list){
            total = total.add(item.getTotal());
        }
        return total;
    }
    /**
     * 验证账户是否合理
     * @param entity
     * @return
     */
    @Override
    public void verify(AdminEntity entity){
        if (entity == null){
            throw new BusinessException(ResultConstants.USER_NO_EXIST_EXCEPTION);
        }
        // 账户被锁定
        else if (entity.getIsLock() == IsLock.LOCK.getCode())
            throw new BusinessException(ResultConstants.USER_IS_LOCK_EXCEPTION);
        // 账户未生效
        else if (entity.getIsVal() == IsVal.NOT_VAL.getCode())
            throw new BusinessException(ResultConstants.USER_IS_VAL_EXCEPTION);
        // 账户被驳回
        else if (entity.getIsVal() == IsVal.REJECT.getCode())
            throw new BusinessException(ResultConstants.USER_REJECT_EXCEPTION+entity.getExtra());
    }
}