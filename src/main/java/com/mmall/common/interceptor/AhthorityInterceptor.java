package com.mmall.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yangshaojun
 * @create 2018-06-01 下午9:18
 **/
// 拦截器类 - 实现接口
    @Slf4j
public class AhthorityInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        log.info("处理 之前。，");
        // 请求中 Controler的方法名称
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//         解析 handlerMethod
//        String methodName = handlerMethod.getMethod().getName();
//        String className = handlerMethod.getBean().getClass().getName();
        // 解析参数
//        StringBuilder requestParamBuffer = new StringBuilder();
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();

        // 遍历
//        while (iterator.hasNext()) {
//            Map.Entry<String, String[]> next = iterator.next();
//            String key = next.getKey();
//            String[] value = next.getValue();
//             打印 参数
//            requestParamBuffer.append(key).append("=").append(value);
//        }

        return true;
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
