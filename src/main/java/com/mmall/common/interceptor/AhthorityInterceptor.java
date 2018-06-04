package com.mmall.common.interceptor;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yangshaojun
 * @create 2018-06-01 下午9:18
 **/
// 拦截器类 - 实现接口
@Slf4j
public class AhthorityInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService iUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入Controller之前，就执行这个方法，接收 处理 之前。，");
        // 请求中 Controler的方法名称和方法参数
        HandlerMethod handlerMethod = (HandlerMethod) handler;
//         解析 handlerMethod
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        // 解析 Controller中方法参数
        StringBuilder requestParamBuffer = new StringBuilder();
        Map<String, String[]> parameterMap = request.getParameterMap();
        // 通过 迭代器 获取 map中的key-value
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();

        // 遍历
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> next = iterator.next();
            String key = next.getKey();
            String[] mapValue = next.getValue();
            String value = Arrays.toString(mapValue);
//             打印 参数
            requestParamBuffer.append(key).append("=").append(value);
        }
        log.info("Controller方法参数是 -> {}", requestParamBuffer);



        User user = null;

        // 从Session中获取User
        user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        // 判断User权限
        if (user == null || (user.getRole() != Const.Role.ROLE_ADMIN)) {

            response.reset();// 要 添加 reset，否则报异常
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();

            if (user == null) {

               //  判断是否是富文本上传
                if (StringUtils.equals(className,"ProductManageController") && StringUtils.equals(methodName,   "richtextImgUpload") ){
                    Map<Object, Object> resultMap = Maps.newHashMap();
                    resultMap.put("success",false);
                    resultMap.put("msg","请登录管理员");
                    out.print(JsonUtil.objToString(resultMap));

                }else{

                    out.print(JsonUtil.objToString(ServerResponse.createByErrorMessage("拦截器拦截。用户没有登录")));
                }

            } else {
                //  判断是否是富文本上传
                if (StringUtils.equals(className,"ProductManageController") && StringUtils.equals(methodName,   "richtextImgUpload") ){
                    Map<Object, Object> resultMap = Maps.newHashMap();
                    resultMap.put("success",false);
                    resultMap.put("msg","没有权限操作");
                    out.print(JsonUtil.objToString(resultMap));

                }else{

                    out.print(JsonUtil.objToString(ServerResponse.createByErrorMessage("拦截器拦截，用户无权限")));
                }
            }
            out.flush();
            out.close();
            // return false 不会去调用Controller 中的方法
            return false;
        }
        // 放行
        return true;// 返回 true。 拦截器放行，会继续执行 Controller方法
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("处理 之后 。，");
    }

    // 完成之后
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("完成之后");
    }
}
