package com.example.ode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ode.common.BusinessException;
import com.example.ode.common.MyPage;
import com.example.ode.constant.ResultConstant;
import com.example.ode.dto.comment.CommentIns;
import com.example.ode.dto.comment.CommentSearch;
import com.example.ode.entity.DishEntity;
import com.example.ode.service.DishService;
import com.example.ode.service.OrderService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ode.dao.CommentDao;
import com.example.ode.entity.CommentEntity;
import com.example.ode.service.CommentService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
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

    /**
     * 遍历集合，逐个处理并添加至数据库
     * @param ins
     */
    @Override
    @Transactional
    public void add(CommentIns ins) {
        List<String> list = ins.getItems();
        for (String s:list){
            String[] ss = s.split("-"); //单个评价组成：类型-（目标对象id）-等级
            int len = ss.length;
            // 存储类型/对象/等级的值
            // 验证格式长度是否合法
            if (len<2 || len>3) throw new BusinessException(ResultConstant.COMMENT_PATTERN_EXCEPTION);
            int[] val = new int[len];
            // 验证类型、对象、等级是否均是数字
            for (int i=0;i<len;i++){
                if (!StringUtils.isNumeric(ss[i])) throw new BusinessException(ResultConstant.COMMENT_PATTERN_EXCEPTION);
                val[i] = Integer.parseInt(ss[i]);
            }
            // 验证类型是否正确
            if (val[0] == 1){
                // 菜品评价有目标对象id
                if (len == 3){
                    DishEntity dish = dishService.getById(val[1]);
                    // 评价的菜品不存在
                    if (dish == null) throw new BusinessException(ResultConstant.DISH_NO_EXIST_EXCEPTION);
                    generateCommentAndInsert(ins, val);
                    continue;
                }
            }else if (val[0] == 2 || val[0] == 3){
                // 非菜品评价没有目标对象
                if (len == 2){
                    generateCommentAndInsert(ins,val);
                    continue;
                }
            }
            throw new BusinessException(ResultConstant.COMMENT_PATTERN_EXCEPTION);
        }
        // 用户完成评价后，需要修改订单状态
        orderService.updateStatus(ins.getOrderId());
    }

    /**
     * 删除某条评论
     * @param id
     */
    @Override
    public void delete(Long id) {
        commentDao.deleteById(id);
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
                .eq(StringUtils.isNotBlank(search.getType().toString()),CommentEntity::getType,search.getType())
                .eq(StringUtils.isNotBlank(search.getTarget().toString()),CommentEntity::getTarget,search.getTarget())
                .eq(StringUtils.isNotBlank(search.getLevel().toString()),CommentEntity::getLevel,search.getLevel())
                .eq(StringUtils.isNotBlank(search.getUserId().toString()),CommentEntity::getUserId,search.getUserId()));

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
        if (val[len-1]<0 || val[len-1]>2) throw new BusinessException(ResultConstant.COMMENT_PATTERN_EXCEPTION);
        CommentEntity entity = new CommentEntity();
        entity.setOrderId(ins.getOrderId());
        entity.setUserId(ins.getUserId());
        entity.setContent(ins.getContent());
        entity.setType(val[0]);
        entity.setLevel(val[len-1]);
        if (len == 3) entity.setTarget(val[1]);
        commentDao.insert(entity);
    }
}