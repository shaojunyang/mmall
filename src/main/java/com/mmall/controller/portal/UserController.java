package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 门户 用户 模块controller
 *
 * @author
 * @create 2017-11-24 下午6:47
 **/

@Controller
@RequestMapping("/user")
public class UserController {
    //controller中注入service 接口
    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录 handler
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
//        调用 service
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
//         如果 是 成功的话、把用户对象信息放入session
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 用户 登出功能
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
//        把用户的session删除
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册  controller功能
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> register(User user) {
//        调用service
        return iUserService.register(user);
    }

    /**
     * 校验用户名 和邮箱
     *
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "/check_valid.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {

        return iUserService.checkValid(str, type);
    }


}
