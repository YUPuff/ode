package com.example.ode.constant;

public interface ResultConstants {

      String SUCCESS = "操作成功！";
      String FAILURE = "操作失败！";

      String USER_IS_LOCK_EXCEPTION = "用户已锁定，请联系管理员！";
      String USER_IS_VAL_EXCEPTION = "用户未生效，请联系管理员！";
      String USER_REJECT_EXCEPTION = "用户被驳回，驳回理由是：";
      String USER_NO_EXIST_EXCEPTION = "用户不存在，操作失败";


      String DISH_NO_EXIST_EXCEPTION = "菜品不存在!";


      String TYPE_NO_EXIST_EXCEPTION = "分类不存在，操作失败！";
      String TYPE_EXIST_EXCEPTION = "待创建的分类名已存在！";
      String TYPE_HAS_EXCEPTION = "此分类名下仍有相关菜品，无法修改！";


      String COMMENT_PATTERN_EXCEPTION = "评论格式有误，请重试！";


      String ORDER_NO_EXIST_EXCEPTION = "订单不存在，操作失败！";
      String ORDER_CANT_EXCEPTION = "当前订单状态无法修改，操作失败！";


      String ORDER_DISH_NO_EXIST_EXCEPTION = "菜品不存在于订单中，操作失败！";
      String ORDER_DISH_CANT_EXCEPTION = "当前菜品状态已无法修改，操作失败！";


}
