package com.example.ode.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 通用分页类
 * @Date: 2023-01-29 20:31
 **/
@Data
public class MyPage<T> {
    // 当前页码
    private Integer pageNum;
    // 当前页大小
    private Integer pageSize;
    // 总页数
    private Integer totalPageNum;
    // 总条数
    private Long totalNum;
    // 所有记录
    private List<T> list;

    /**
     * 包装mybatis-plus自带的分页类
     * @param list
     * @return
     * @param <T>
     */
    public static <T>MyPage<T> createPage(IPage<T> list){
        MyPage<T> result = new MyPage<>();
        result.setPageNum((int) list.getCurrent());
        result.setPageSize((int) list.getSize());
        result.setTotalPageNum((int) list.getPages());
        result.setTotalNum(list.getTotal());
        result.setList(list.getRecords());
        return result;
    }
    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <T> MyPage<T> createPage(Page<T> pageInfo) {
        MyPage<T> result = new MyPage<T>();
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotalPageNum(pageInfo.getTotalPages());
        result.setTotalNum(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }


}
