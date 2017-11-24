package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 查询是否有该用户
     *
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 登录 功能接口
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 查询邮箱是否存在
     * @param email
     * @return
     */
    int checkEmail(@Param("email") String email);

}