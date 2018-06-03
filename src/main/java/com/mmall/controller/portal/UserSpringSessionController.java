package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisSharedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 门户 用户 模块controller
 *
 * @author
 * @create 2017-11-24 下午6:47
 **/

@Controller
@RequestMapping("/user/spring-session")
public class UserSpringSessionController {
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
    @RequestMapping(value = "/login.do")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse response) {
//        调用 service
        ServerResponse<User> res = iUserService.login(username, password);
        if (res.isSuccess()) {
//         如果 是 成功的话、把用户对象信息放入session
            session.setAttribute(Const.CURRENT_USER, res.getData());

            System.out.println(1);
        }
        return res;
    }

    /**
     * 用户 登出功能
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//        把用户的session删除
        session.removeAttribute(Const.CURRENT_USER);

        return ServerResponse.createBySuccess();
    }

    /**
     * 获取用户信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_user_info.do")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session,HttpServletRequest request) {

//        从session中获取当前用户
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户没有登录");
        }

        //返回用户信息
        return ServerResponse.createBySuccess(user);


    }

}


