package com.example.ode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ode.common.MyPage;
import com.example.ode.dto.comment.CommentIns;
import com.example.ode.dto.comment.CommentSearch;
import com.example.ode.entity.CommentEntity;

import java.util.Map;

/**
 * @author yilin
 * @date 2023-01-28 14:23:41
 */
public interface CommentService extends IService<CommentEntity> {

    void add(CommentIns ins);

    void delete(Long id);

    MyPage<CommentEntity> getComments(CommentSearch search);
}

