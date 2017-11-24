package com.mmall.service.Impl;

import com.google.zxing.common.StringUtils;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

/**
 * 用户service实现 接口
 *
 * @author
 * @create 2017-11-24 下午7:11
 **/
@Service("iUserService") //注入 service 接口
public class UserServiceImpl implements IUserService {
    //    注入 userMapper
    private UserMapper userMapper;

    /**
     * 用户 登录
     *
     * @param username
     * @param pasword
     * @return
     */
    @Override
    public ServerResponse<User> login(String username, String pasword) {
//        先查询用户是否存在
        int count = userMapper.checkUsername(username);
        if (count == 0) {
            return ServerResponse.createBySuccessMessage("用户名不存在");
        }
//        密码登录 MD5 //
        String md5Password = MD5Util.MD5EncodeUtf8(pasword);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
//        把 他的密码置为空
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);

    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    public ServerResponse<String> register(User user) {
        //        先查询用户是否存在
        int count = userMapper.checkUsername(user.getUsername());
        if (count > 0) {
            return ServerResponse.createBySuccessMessage("用户名已经存在");
        }//查询邮箱是否存在
        int emailCount = userMapper.checkEmail(user.getEmail());
        if (emailCount > 0) {
            return ServerResponse.createBySuccessMessage("邮箱已经存在");
        }
//        给该用户设置 权限
        user.setRole(Const.Role.ROLE_CUSTOMER);
//        md5 加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
//        保存用户到数据库
        int insertCount = userMapper.insert(user);
        if (insertCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    /**
     * 效验 用户名和 邮箱
     * @param str
     * @param type
     * @return
     */
    public ServerResponse<String> checkValid(String str, String type) {
//        判断 传过来的参数
        if (org.apache.commons.lang3.StringUtils.isBlank(type)) {
//            开始校验
            if (Const.USERNAME.equals(type)) {
//                校验用户名
                int count = userMapper.checkUsername(str);
                if (count > 0) {
                    return ServerResponse.createBySuccessMessage("用户名已经存在");
                }
            }
//            校验email
            if (Const.EMAIL.equals(type)) {
                int emailCount = userMapper.checkEmail(str);
                if (emailCount > 0) {
                    return ServerResponse.createBySuccessMessage("邮箱已经存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }
}
