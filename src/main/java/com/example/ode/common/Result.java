package com.example.ode.common;

import com.example.ode.constant.ResultConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: project1
 * @Author: lyl
 * @Description: 返回结果通用类
 * @Date: 2023-01-29 16:03
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result <R> implements Serializable {
    private static final long serialVersionUID = 7574078101944305355L;

    /**
     *  状态码
     */
    private String code;

    /**
     *  返回信息
     */
    private String message;

    /**
     *  返回数据
     */
    private R data;

    /**
     *  返回的附加数据
     */
    private Map<String, Object> otherData = new HashMap();


    public Result(String code){this.code = code;}

    public Result(String code,R data){
        this.code = code;
        this.data = data;
    }

    public Result(Throwable e){this.message = e.getMessage();}

    public static <R> Result<R> message(String message){
        Result<R> result = new Result<>();
        result.message = message;
        return result;
    }

    public static <R> Result<R> success(R data){
        Result<R> result = new Result<>();
        result.data = data;
        return result;
    }

    public static <R> Result<R> success(){
        return message(ResultConstants.SUCCESS);
    }

    public static <R> Result<R> success(R data,String message){
        Result<R> result = success(data);
        result.message = message;
        return result;
    }

    public static <R> Result<R> failure(String message){
        return message(message);
    }

    public Result<R> addOtherData(String key, Object value) {
        this.otherData.put(key, value);
        return this;
    }

    public Result<R> removeOtherData(String key) {
        this.otherData.remove(key);
        return this;
    }
}
