package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * 用户模块 接口
 * Created by yangshaojun on 2017/11/24.
 */
public interface IUserService {
    //登录 接口
    ServerResponse<User> login(String username, String password);

    //注册 接口
    ServerResponse<String> register(User user);

    //校验用户名和邮箱是否符合 规则
    ServerResponse<String> checkValid(String str, String type);
}
