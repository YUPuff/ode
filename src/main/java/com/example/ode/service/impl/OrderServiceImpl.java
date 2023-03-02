package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.constant.ResultConstant;
import com.example.ode.dto.dish.DishDTO;
import com.example.ode.dto.order.OrderIns;
import com.example.ode.dto.order.OrderSearch;
import com.example.ode.entity.DishEntity;
import com.example.ode.entity.OrderDishEntity;
import com.example.ode.entity.UserEntity;
import com.example.ode.enums.DishStatus;
import com.example.ode.enums.OrderStatus;
import com.example.ode.service.DishService;
import com.example.ode.service.OrderDishService;
import com.example.ode.service.UserService;
import com.example.ode.util.ObjectUtils;
import com.example.ode.vo.OrderDishVO;
import com.example.ode.vo.OrderVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.OrderDao;
import com.example.ode.entity.OrderEntity;
import com.example.ode.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderDishService orderDishService;

    @Autowired
    private UserService userService;

    /**
     * 添加新订单
     * @param ins
     */
    @Override
    @Transactional
    public void add(OrderIns ins) {
        // 验证用户是否存在
        UserEntity user = userService.getById(ins.getUserId());
        if (user == null)
            throw new BusinessException(ResultConstant.USER_NO_EXIST_EXCEPTION);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTableId(ins.getTableId());
        orderEntity.setUserId(ins.getUserId());
        orderEntity.setRemark(ins.getRemark());
        // 先插入订单表以获取生成的订单id
        orderDao.insert(orderEntity);
        Long orderId = orderEntity.getId();
        // 遍历菜品集合
        List<DishDTO> dishes = ins.getDishes();
//        BigDecimal total = new BigDecimal(0);
        for(DishDTO dish:dishes){
            // 验证菜品是否存在
            DishEntity entity = dishService.getById(dish.getId());
            if (entity == null)
                throw new BusinessException(ResultConstant.DISH_NO_EXIST_EXCEPTION);
            // 将订单菜品关系插入对应表中
            OrderDishEntity orderDishEntity = new OrderDishEntity();
            orderDishEntity.setOrderId(orderId);
            orderDishEntity.setDishId(dish.getId());
            orderDishEntity.setAmount(dish.getAmount());
            orderDishService.save(orderDishEntity);
            // 计算出总金额
            DishEntity dishEntity = dishService.getById(dish.getId());
//            total = total.add(dishEntity.getPrice().multiply(new BigDecimal(dish.getAmount())));
        }
        // 修改订单其他相关信息
//        orderEntity.setTotal(total);
        orderDao.updateById(orderEntity);
    }

    /**
     * 修改订单状态（开始烹饪第一道菜、烹饪完最后一道菜、用户评价结束）
     * 订单状态（0：未开始，1：进行中，2：待评价，3：已完成，4：已取消）
     * @param id
     */
    @Override
    public void updateStatus(Long id) {
        OrderEntity order = orderDao.selectById(id);
        if (order == null) throw new BusinessException(ResultConstant.ORDER_NO_EXIST_EXCEPTION);
        int status = order.getStatus();
        if (status == OrderStatus.FINISHED.getCode() || status == OrderStatus.CANCELED.getCode()) throw new BusinessException(ResultConstant.ORDER_CANT_EXCEPTION);
        order.setStatus(status+1);
        orderDao.updateById(order);
    }

    /**
     * 只有当订单状态未开始时才能被用户取消
     * @param id
     */
    @Override
    public void cancelOrder(Long id) {
        OrderEntity order = orderDao.selectById(id);
        if (order == null) throw new BusinessException(ResultConstant.ORDER_NO_EXIST_EXCEPTION);
        int status = order.getStatus();
        if (status != OrderStatus.NOT_START.getCode())
            throw new BusinessException(ResultConstant.ORDER_CANT_EXCEPTION);
        order.setStatus(OrderStatus.CANCELED.getCode());
        orderDao.updateById(order);
        // 修改订单中所有菜品状态为已取消
        OrderDishEntity entity = new OrderDishEntity();
        entity.setStatus(DishStatus.CANCELED.getCode());
        orderDishService.update(entity,new LambdaQueryWrapper<OrderDishEntity>().eq(OrderDishEntity::getOrderId,id));
    }

    @Override
    public OrderVO detail(Long id,Integer pageNum) {
        OrderEntity order = orderDao.selectById(id);
        // 验证订单是否存在
        if (order == null)
            throw new BusinessException(ResultConstant.ORDER_NO_EXIST_EXCEPTION);
        // 查询订单对应菜品的详细信息
        List<OrderDishVO> dishes = orderDao.selectDishForOrder(id,(pageNum-1)*10);
        MyPage<OrderDishVO> myPage = new MyPage<>();
        myPage.setPageNum(pageNum);
        myPage.setList(dishes);
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        orderVO.setDishes(myPage);
        return orderVO;
    }


    /**
     * 分页查询订单
     * @param search
     * @return
     */
    @Override
    public MyPage<OrderEntity> getOrders(OrderSearch search) {
        IPage<OrderEntity> page = new Page<>(search.getPageNum(),search.getPageSize());
        IPage<OrderEntity> orderPage = orderDao.selectPage(page,new LambdaQueryWrapper<OrderEntity>()
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getUserId())),OrderEntity::getUserId,search.getUserId())
                .like(StringUtils.isNotBlank(ObjectUtils.toString(search.getTableId())),OrderEntity::getTableId,search.getTableId())
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getStatus())),OrderEntity::getStatus,search.getStatus())
                .le(StringUtils.isNotBlank(ObjectUtils.toString(search.getMaxTotal())),OrderEntity::getTotal,search.getMaxTotal())
                .ge(StringUtils.isNotBlank(ObjectUtils.toString(search.getMinTotal())),OrderEntity::getTotal,search.getMinTotal()));
        MyPage<OrderEntity> myPage = MyPage.createPage(orderPage);
        return myPage;
    }

}