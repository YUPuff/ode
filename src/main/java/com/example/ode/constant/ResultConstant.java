package com.example.ode.constant;

public class ResultConstant {

    public static final String SUCCESS = "操作成功！";
    public static final String FAILURE = "操作失败！";

    public static final String IS_LOCK_EXCEPTION = "账户已锁定，请联系管理员！";

    public static final String IS_VAL_EXCEPTION = "账户未生效，请联系管理员！";

    public static final String REJECT_EXCEPTION = "账户被驳回，驳回理由是：";

    public static final String NO_EXIST_EXCEPTION = "账户不存在，操作失败";

    public static final String DISH_NO_EXIST_EXCEPTION = "菜品不存在，操作失败！";


    public static final String TYPE_NO_EXIST_EXCEPTION = "分类不存在，操作失败！";
    public static final String TYPE_EXIST_EXCEPTION = "待创建的分类代号或分类名已存在！";
    public static final String TYPE_HAS_EXCEPTION = "此分类代号或分类名下仍有相关菜品，无法修改！";


    public static final String COMMENT_PATTERN_EXCEPTION = "评论格式有误，请重试！";

    public static final String ORDER_NO_EXIST_EXCEPTION = "订单不存在，操作失败！";
    public static final String ORDER_CANT_EXCEPTION = "当前订单状态已无法修改，操作失败！";


    public static final String ORDER_DISH_NO_EXIST_EXCEPTION = "菜品不存在于订单中，操作失败！";
    public static final String ORDER_DISH_CANT_EXCEPTION = "当前菜品状态已无法修改，操作失败！";


}
