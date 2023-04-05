package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ode.common.BusinessException;
import com.example.ode.constant.ResultConstants;
import com.example.ode.entity.DishEntity;
import com.example.ode.entity.OrderEntity;
import com.example.ode.entity.RecommendEntity;
import com.example.ode.enums.DishStatus;
import com.example.ode.enums.OrderStatus;
import com.example.ode.service.DishService;
import com.example.ode.service.OrderService;
import com.example.ode.service.RecommendService;
import com.example.ode.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.OrderDishDao;
import com.example.ode.entity.OrderDishEntity;
import com.example.ode.service.OrderDishService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service("orderDishService")
public class OrderDishServiceImpl extends ServiceImpl<OrderDishDao, OrderDishEntity> implements OrderDishService {

    @Autowired
    private OrderDishDao orderDishDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    @Autowired
    private RecommendService recommendService;

    /**
     * 修改菜品状态（0：未烹饪，1：烹饪中，2：待上菜，3：已完成，4：已取消）
     *
     * @param id
     */
    @Override
    public void updateStatus(Long id) {
        OrderDishEntity entity = orderDishDao.selectById(id);
        if (entity == null) throw new BusinessException(ResultConstants.ORDER_DISH_NO_EXIST_EXCEPTION);
        int status = entity.getStatus();
        if (status == DishStatus.FINISHED.getCode() || status == DishStatus.CANCELED.getCode())
            throw new BusinessException(ResultConstants.ORDER_DISH_CANT_EXCEPTION);
        entity.setStatus(status+1);
        orderDishDao.updateById(entity);
        // 如果订单第一道菜开始烹饪、最后一道菜完成烹饪则分别修改订单状态
        OrderEntity order = orderService.getOne(new LambdaQueryWrapper<OrderEntity>()
                .eq(OrderEntity::getId, entity.getOrderId()));
        if (order.getStatus() == OrderStatus.NOT_START.getCode())
            orderService.updateStatus(order.getId());
        else if (status == DishStatus.WAIT_TO_SERVE.getCode()){
            // 查询当前订单是否还有未完成的菜品
            OrderDishEntity orderDishEntity = orderDishDao.selectOne(new LambdaQueryWrapper<OrderDishEntity>()
                    .lt(OrderDishEntity::getStatus, DishStatus.FINISHED.getCode())
                    .eq(OrderDishEntity::getOrderId, entity.getOrderId()));
            if (orderDishEntity == null) orderService.updateStatus(order.getId());
        }
    }

    /**
     * 撤销菜品
     * @param id
     */
    @Override
    public void cancelDish(Long id) {
        // 验证是否有对应存在的订单
        OrderDishEntity entity = orderDishDao.selectById(id);
        if (entity == null)
            throw new BusinessException(ResultConstants.ORDER_DISH_NO_EXIST_EXCEPTION);
        int status = entity.getStatus();
        // 只有未烹饪的菜品才能被取消
        if (status != DishStatus.WAIT_TO_COOK.getCode())
            throw new BusinessException(ResultConstants.ORDER_DISH_CANT_EXCEPTION);
        entity.setStatus(DishStatus.CANCELED.getCode());
        orderDishDao.updateById(entity);
        // 如果当前订单只有此菜品，则订单状态变为“已取消”
        Long orderId = entity.getOrderId();
        List<OrderDishEntity> entities = orderDishDao.selectList(new LambdaQueryWrapper<OrderDishEntity>().eq(OrderDishEntity::getOrderId, orderId));
        if (entities.size() == 1){
            orderService.cancelOrder(orderId);
        }
        // 从总订单中删除当前菜品金额
        Long dishId = entity.getDishId();
        DishEntity dish = dishService.getById(dishId);
        OrderEntity order = orderService.getById(entity.getOrderId());
        BigDecimal total = order.getTotal();
        total = total.subtract(dish.getPrice().multiply(new BigDecimal(entity.getAmount())));
        order.setTotal(total);
        orderService.updateById(order);
        // 从推荐表中更新或删除用户-菜品记录
        Long userId = order.getUserId();
        LambdaQueryWrapper<RecommendEntity> wrapper = new LambdaQueryWrapper<RecommendEntity>()
                .eq(RecommendEntity::getUserId, userId).eq(RecommendEntity::getDishId, dishId);
        RecommendEntity recommendEntity = recommendService.getOne(wrapper);
        // 判断需要更新还是删除
        if (recommendEntity.getCount() == entity.getAmount()){
            // 撤销菜品就是本次点单的菜品
            recommendService.remove(wrapper);
        }else{
            // 还有历史点此菜品的记录
            recommendEntity.setCount(recommendEntity.getCount()-entity.getAmount());
            recommendService.update(recommendEntity,wrapper);
        }
    }

    /**
     * 获取当月热销前5的菜品
     * @return
     */
    @Override
    public List<DishVO> getTop5Dishes() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdf.format(date);
        System.out.println(today);
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        //月份+1，天设置为0。下个月第0天，就是这个月最后一天
        cld.add(Calendar.MONTH, 1);
        cld.set(Calendar.DAY_OF_MONTH, 0);
        String end = sdf.format(cld.getTime());
        cld.set(Calendar.DAY_OF_MONTH,1);
        String start = sdf.format(cld.getTime());
        return orderDishDao.getTop5Dishes(start,end);
    }
}