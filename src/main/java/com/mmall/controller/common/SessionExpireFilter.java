package com.mmall.controller.common;

import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yangshaojun
 * @create 2018-06-02 下午10:24
 **/

public class SessionExpireFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    // 重写 Filter方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 从 request中获取  loginToken
        String loginToken = CookieUtil.readLoginToken(request);

        if (loginToken != null && loginToken.equals("")) {
            // 通过 loginToken从Redis中获取 用户字符串
            String userJsonStr = RedisPoolUtil.get(loginToken);
            // 获取用户，反序列化
            User user = JsonUtil.stringToObj(userJsonStr, User.class);
            if (user != null) {
               // 如果User不为空 重置 Redis中 存储的 session值 的过期时间
                RedisPoolUtil.expire(loginToken,30 * 60);
           }
        }

        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
