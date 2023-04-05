package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.common.MyPage;
import com.example.ode.dto.comment.CommentIns;
import com.example.ode.dto.comment.CommentSearch;
import com.example.ode.entity.CommentEntity;
import com.example.ode.vo.CommentVO;

import java.util.Map;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
public interface CommentService extends IService<CommentEntity> {

    void add(CommentIns ins);

    Map<String,Long> getCommentCount(Long dishId);

    Map<String,Map<String,Long>> getSEComment();

    MyPage<CommentEntity> getCommentDetail(Integer pageNum,Integer pageSize);

    MyPage<CommentEntity> getComments(CommentSearch search);
}

