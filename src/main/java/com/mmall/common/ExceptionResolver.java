package com.mmall.common;

import org.slf4j.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangshaojun
 * @create 2018-05-31 下午9:09
 **/
@Component
public class ExceptionResolver implements HandlerExceptionResolver
{
   private static  Logger log =  LoggerFactory.getLogger(ExceptionResolver.class);

    // 实现方法
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // 打印到后台
        log.error("{} Exception", httpServletRequest.getRequestURI(), e);

        ModelAndView mv = new ModelAndView(new MappingJacksonJsonView());

        // 当使用 jackson2.x使用 MappingJackson2JsonView
        mv.addObject("status",ResponseCode.ERROR.getCode());
        mv.addObject("msg","接口异常。详情请查看服务端接口日志");
        mv.addObject("data",e.toString());
        return mv;
    }
}
