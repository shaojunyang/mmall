package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

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

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");

    }


    //    定义接口 属性 表示 角色  可以在 外部直接调用
    public interface Role {
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//管理员
    }

    public enum ProductStatusEnum {
        ON_SALE(1, "在线");
        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

}
