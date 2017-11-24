package com.mmall.common;

/**
 * 常量类
 *
 * @author
 * @create 2017-11-24 下午8:38
 **/

public class Const {
    //    session 中表示当前用户的常量
    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";


    //    定义接口 属性 表示 角色  可以在 外部直接调用
    public interface Role {
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//管理员
    }
}
