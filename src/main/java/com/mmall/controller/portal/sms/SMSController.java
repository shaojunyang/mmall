package com.mmall.controller.portal.sms;

import com.alipay.api.internal.util.StringUtils;
import com.mmall.common.ServerResponse;
import com.mmall.common.mail.PwdMailSender;
import com.mmall.dao.LiuyanMapper;
import com.mmall.pojo.Liuyan;
import com.mmall.sms.JavaSmsApi;
import com.mmall.util.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangshaojun
 * @create 2018-07-23 下午10:14
 **/
@Controller
@RequestMapping("/sms")
public class SMSController {

    // 发送邮件对象
    @Autowired
    private PwdMailSender pwdMailSender;


    // 留言对象dao
    @Autowired
    LiuyanMapper liuyanMapper;

    /**
     * 发送验证码信息
     * @param mobile 手机号
     * @param callback
     * @param response
     * @param request
     * @return 验证码json字符串
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/send-msg.do")
    public String send_msg(@RequestParam(value = "mobile") String mobile, @RequestParam(value = "callback") String callback, HttpServletResponse response, HttpServletRequest request) throws IOException {

        String ApiKEY = "82ed39a542435fbeabd2ea47fa53af0f";
        Integer random = createRandom();
        String text = "【云合万家】您的验证码是" + random + "。如非本人操作，请忽略本短信";

        String result = JavaSmsApi.sendSms(ApiKEY, text, mobile);

        Map<String, String> params = new HashMap<String, String>();
        params.put("result", result);
        params.put("code", random.toString());

//        String s = JsonUtil.objToString(params);

        String r = "{'code':" + random + "}";
        //    加上返回参数
        r = callback + "(" + r + ")";
        return r;

    }


    /**
     * 生成4位验证码
     * @return
     */
    private Integer createRandom() {
        Integer randomNum = (int) (Math.random() * 9000) + 1000;
        return randomNum;
    }

    public static void main(String[] args) {
        System.out.println(new SMSController().createRandom());
    }


    @ResponseBody
    @RequestMapping("/save_liuyan.do")
    public ServerResponse<String> save_liuyan(Liuyan liuyan, HttpServletResponse response, HttpServletRequest request) {

        if (StringUtils.isEmpty(liuyan.getEmail())) {
            liuyan.setEmail("空");
        }
        if (StringUtils.isEmpty(liuyan.getCompany())) {
            liuyan.setCompany("空");
        }
        // ajax允许跨域
        openOrigin(response, request);

        Integer insert = liuyanMapper.insert(liuyan);
        if (insert > 0) {
            // 发送邮件
            send_mail(liuyan);
            return ServerResponse.createBySuccessMessage("提交成功");
        }
        return ServerResponse.createBySuccessMessage("提交失败，请稍后60s重试");
    }

    /**
     * 发送邮件
     *
     * @param liuyan 邮件相关信息对象
     */
    private void send_mail(Liuyan liuyan) {
//        String toMail = "yasong_wang@araya.cn";
        String toMail = "shaojun_yang@araya.cn";
        String subject = "阿赖雅官网收到一条解决方案申请试用留言-请联系客户";
        String content = "时间: " + dateToString() + "\n" + "姓名: " + liuyan.getName() + "     刚刚 在网站提交了试用申请留言 。" + "\n" + " 手机号：  " + liuyan.getMobile() + "\n" + "邮箱 :" + liuyan.getEmail() + "\n" + "公司名称：" + liuyan.getCompany() + "\n" + " 回复内容为： " + liuyan.getContent() + "，请及时联系客户";
        pwdMailSender.sendMail(toMail, subject, content);

    }


    /**
     * 当前时间转字符串
     * @return 转换之后的字符串 yyyy-MM-dd HH:mm:ss
     */
    public String dateToString() {
        //截取当前系统时间
        Date currentTime = new Date();
        //改变输出格式（自己想要的格式）
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到字符串时间
        String s8 = formatter.format(currentTime);
        return s8;
    }

    @Test
    public void test1() {
        System.out.println(dateToString());
    }


    /**
     * 查询申请试用留言的列表
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/select_liuyan.do")
    public ServerResponse<List<Liuyan>> select_liyan(HttpServletRequest request, HttpServletResponse response) {

        openOrigin(response, request);

        List<Liuyan> all = liuyanMapper.findAll();
        return ServerResponse.createBySuccess(all);
    }


    /**
     * 设置允许跨域请求的方法
     *
     * @param response
     * @param request
     */
    private void openOrigin(HttpServletResponse response, HttpServletRequest request) {

        response.setHeader("Access-Control-Allow-Origin", "http://www.araya.cn");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    }

//
//    /**
//     * 发送邮件
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/send_mail.do")
//    public void send_mail(){
//
//        pwdMailSender.sendMail("1570482304@qq.com", "在这里填写你所需要的内容", "邮件内容");
//
//    }

    @ResponseBody
    @RequestMapping("/test.do")
    public String test() {

        return "334";

    }


    @Test
    public void t() throws IOException {

        String ApiKEY = "82ed39a542435fbeabd2ea47fa53af0f";
        Integer random = createRandom();
        String text = "【云合万家】您的验证码是" + random + "。如非本人操作，请忽略本短信";

        String result = JavaSmsApi.sendSms(ApiKEY, text, "13273091192");

        Map<String, String> params = new HashMap<String, String>();
        params.put("result", result);
        params.put("code", random.toString());

        String s = JsonUtil.objToString(params);

        System.out.println();
    }
}
