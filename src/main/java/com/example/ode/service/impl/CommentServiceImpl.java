package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.constant.ResultConstants;
import com.example.ode.dto.comment.CommentIns;
import com.example.ode.dto.comment.CommentSearch;
import com.example.ode.entity.DishEntity;
import com.example.ode.entity.OrderEntity;
import com.example.ode.entity.UserEntity;
import com.example.ode.enums.CommentLevel;
import com.example.ode.enums.CommentType;
import com.example.ode.enums.OrderStatus;
import com.example.ode.service.DishService;
import com.example.ode.service.OrderService;

import com.example.ode.service.UserService;
import com.example.ode.util.ObjectUtils;
import com.example.ode.vo.CommentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.CommentDao;
import com.example.ode.entity.CommentEntity;
import com.example.ode.service.CommentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * 遍历集合，逐个处理并添加至数据库
     * @param ins
     */
    @Override
    @Transactional
    public void add(CommentIns ins) {
        // 验证用户是否存在
        UserEntity user = userService.getById(ins.getUserId());
        if (user == null)
            throw new BusinessException(ResultConstants.USER_NO_EXIST_EXCEPTION);
        // 验证订单是否存在
        OrderEntity order = orderService.getById(ins.getOrderId());
        if (order == null)
            throw new BusinessException(ResultConstants.ORDER_NO_EXIST_EXCEPTION);
        // 验证订单当前状态是否为“待评论”
        if (order.getStatus() != OrderStatus.WAIT_TO_COMMENT.getCode())
            throw new BusinessException(ResultConstants.ORDER_CANT_EXCEPTION);
        // 处理各条分评论
        List<String> list = ins.getItems();
        for (String s:list){
            String[] ss = s.split("-"); //单个评价组成：类型-（目标对象id）-等级
            int len = ss.length;
            // 存储类型/对象/等级的值
            // 验证格式长度是否合法
            if (len<2 || len>3)
                throw new BusinessException(ResultConstants.COMMENT_PATTERN_EXCEPTION);
            int[] val = new int[len];
            // 验证类型、对象、等级是否均是数字
            for (int i=0;i<len;i++){
                if (!StringUtils.isNumeric(ss[i]))
                    throw new BusinessException(ResultConstants.COMMENT_PATTERN_EXCEPTION);
                val[i] = Integer.parseInt(ss[i]);
            }
            // 验证类型是否正确
            if (val[0] == CommentType.DISH.getCode()){
                // 菜品评价有目标对象id
                if (len == 3){
                    DishEntity dish = dishService.getById(val[1]);
                    // 评价的菜品不存在
                    if (dish == null) throw new BusinessException(ResultConstants.DISH_NO_EXIST_EXCEPTION);
                    generateCommentAndInsert(ins, val);
                    continue;
                }
            }else if (val[0] == CommentType.SERVICE.getCode() || val[0] == CommentType.ENVIRONMENT.getCode()){
                // 非菜品评价没有目标对象
                if (len == 2){
                    generateCommentAndInsert(ins,val);
                    continue;
                }
            }
            throw new BusinessException(ResultConstants.COMMENT_PATTERN_EXCEPTION);
        }
        // 用户完成评价后，需要修改订单状态
        orderService.updateStatus(ins.getOrderId());
    }

    /**
     * 得到某菜品好、中、差评价数
     * @param dishId
     * @return
     */
    @Override
    public Map<String, Long> getCommentCount(Long dishId) {
        DishEntity dish = dishService.getById(dishId);
        if (dish == null)
            throw new BusinessException(ResultConstants.DISH_NO_EXIST_EXCEPTION);
        Long good = commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>().eq(CommentEntity::getType, CommentType.DISH.getCode())
                .eq(CommentEntity::getTarget, dishId)
                .eq(CommentEntity::getLevel,CommentLevel.GOOD.getCode()));
        Long mid = commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>().eq(CommentEntity::getType, CommentType.DISH.getCode())
                .eq(CommentEntity::getTarget, dishId)
                .eq(CommentEntity::getLevel,CommentLevel.MEDIUM.getCode()));
        Long bad = commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>().eq(CommentEntity::getType, CommentType.DISH.getCode())
                .eq(CommentEntity::getTarget, dishId)
                .eq(CommentEntity::getLevel,CommentLevel.BAD.getCode()));
        Map<String,Long> map = new HashMap<>();
        map.put("good",good);
        map.put("mid",mid);
        map.put("bad",bad);
        return map;
    }

    /**
     * 得到服务、环境的好中差评数
     * @return
     */
    @Override
    public Map<String, Map<String, Long>> getSEComment() {
        Map<String,Long> service = new HashMap<>();
        Map<String,Long> environment = new HashMap<>();
        Integer ts = CommentType.SERVICE.getCode();
        Integer te = CommentType.ENVIRONMENT.getCode();
        Integer good = CommentLevel.GOOD.getCode();
        Integer mid = CommentLevel.MEDIUM.getCode();
        Integer bad = CommentLevel.BAD.getCode();
        service.put("good",commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>()
                .eq(CommentEntity::getType,ts)
                .eq(CommentEntity::getLevel,good)));
        service.put("mid",commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>()
                .eq(CommentEntity::getType,ts)
                .eq(CommentEntity::getLevel,mid)));
        service.put("bad",commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>()
                .eq(CommentEntity::getType,ts)
                .eq(CommentEntity::getLevel,bad)));
        environment.put("good",commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>()
                .eq(CommentEntity::getType,te)
                .eq(CommentEntity::getLevel,good)));
        environment.put("mid",commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>()
                .eq(CommentEntity::getType,te)
                .eq(CommentEntity::getLevel,mid)));
        environment.put("bad",commentDao.selectCount(new LambdaQueryWrapper<CommentEntity>()
                .eq(CommentEntity::getType,te)
                .eq(CommentEntity::getLevel,bad)));
        Map<String,Map<String,Long>> map = new HashMap<>();
        map.put("service",service);
        map.put("environment",environment);
        return map;
    }

    /**
     * 分页查看具体评论内容
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public MyPage<CommentEntity> getCommentDetail(Integer pageNum, Integer pageSize) {
        IPage<CommentEntity> page = new Page<>(pageNum,pageSize);
        IPage<CommentEntity> commentPage = commentDao.selectPage(page,new LambdaQueryWrapper<CommentEntity>()
                .groupBy(CommentEntity::getOrderId));
        MyPage<CommentEntity> myPage = MyPage.createPage(commentPage);
        return myPage;
    }


    /**
     * 分页查询评价
     * 某个菜品的评价、某个用户发表的评价、管理员查看所有类型的评价
     * @param search
     * @return
     */
    @Override
    public MyPage<CommentEntity> getComments(CommentSearch search) {
        IPage<CommentEntity> page = new Page<>(search.getPageNum(),search.getPageSize());
        IPage<CommentEntity> commentPage = commentDao.selectPage(page,new LambdaQueryWrapper<CommentEntity>()
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getType())),CommentEntity::getType,search.getType())
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getTarget())),CommentEntity::getTarget,search.getTarget())
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getLevel())),CommentEntity::getLevel,search.getLevel())
                .eq(StringUtils.isNotBlank(ObjectUtils.toString(search.getUserId())),CommentEntity::getUserId,search.getUserId()));

        MyPage<CommentEntity> myPage = MyPage.createPage(commentPage);
        return myPage;
    }

    /**
     * 根据表单总体和遍历单项处理后的信息生成评价实体
     * @param ins
     * @param val
     * @return
     */
    private void generateCommentAndInsert(CommentIns ins,int[] val){
        int len = val.length;
        // 评价等价有误
        if (val[len-1]< CommentLevel.BAD.getCode() || val[len-1]>CommentLevel.GOOD.getCode())
            throw new BusinessException(ResultConstants.COMMENT_PATTERN_EXCEPTION);
        // 创建评论对象
        CommentEntity entity = new CommentEntity();
        entity.setOrderId(ins.getOrderId());
        entity.setUserId(ins.getUserId());
        entity.setContent(ins.getContent());
        entity.setType(val[0]);
        entity.setLevel(val[len-1]);
        if (len == 3) entity.setTarget((long)val[1]);
        commentDao.insert(entity);
    }
}