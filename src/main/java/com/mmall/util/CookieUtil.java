package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangshaojun
 * @create 2018-06-02 下午8:59
 **/
@Slf4j
public class CookieUtil {
    private static final String COOKID_DOMAIN = "localhost";
    private static final String COOKID_NAME = "mmall_login_token";

    /**
     * 读Cookie
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie ck : cookies) {
                log.info("cookieName :{} , cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKID_NAME)) {
                    log.info("读  CookieName {},CookieValue ",ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    // X:domain= ".baidu.com" path ="/" 可以共享 以下4个域名的cookie

    // a:A.baidu.com  cookie:domain= A.baidu.com :path="/"
    // b:A.baidu.com   cookie:domain= A.baidu.com :path="/"
    // c:A.baidu.com/test   cookie:domain= A.baidu.com :path="/test"
    // d:A.baidu.com/test/cc   cookie:domain= A.baidu.com :path="/test/cc"
    /**
     * 写入cookie
     * @param response
     * @param token
     */
    public static void writerLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKID_NAME,token);
        ck.setDomain(COOKID_DOMAIN);
        ck.setPath("/");// 表示设置在 根目录
        ck.setHttpOnly(true); // 禁止脚本攻击
        // cookie有效期，-1代表 永久，秒
        // 如果MaxAge不设置，cookie不会写入硬盘，会写在内存，只在当前页面有效
        ck.setMaxAge(60 * 60 * 24 * 365);
        log.info("写 cookieName:{ } ,cookieValue : {}", ck.getName(), ck.getValue());
        // 写入cookie
        response.addCookie(ck);
    }

    /**
     * 用户登出时。删除 cookie
     * @param request
     * @param response
     */
    public static void delLogoutCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie ck : cookies) {
                if (StringUtils.equals(ck.getName(), COOKID_NAME)) {
                    ck.setDomain(COOKID_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0); // 设置为0 ,代表删除 cookie
                    // log.info("删除 cookieName:{ } ,cookieValue : {}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}
