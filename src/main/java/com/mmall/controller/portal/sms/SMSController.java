package com.mmall.controller.portal.sms;

import com.mmall.common.ServerResponse;
import com.mmall.common.mail.PwdMailSender;
import com.mmall.dao.LiuyanMapper;
import com.mmall.pojo.Liuyan;
import com.mmall.sms.JavaSmsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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


    @Autowired
    LiuyanMapper liuyanMapper;

    @ResponseBody
    @RequestMapping("/send-msg.do")
    public String send_msg(@RequestParam(value = "mobile") String mobile, @RequestParam(value = "callback") String callback, HttpServletResponse response, HttpServletRequest request) throws IOException {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//
        String ApiKEY = "82ed39a542435fbeabd2ea47fa53af0f";
        Integer random = createRandom();
        String text = "【云合万家】您的验证码是" + random + "。如非本人操作，请忽略本短信";
        String result = JavaSmsApi.sendSms(ApiKEY, text, mobile);
//
        Map<String, String> params = new HashMap<String, String>();
        params.put("result", result);
        params.put("code", random.toString());
//        return ServerResponse.createBySuccess(params);

        String r = "{'code':" + random + "}";
//    加上返回参数
        r = callback + "(" + r + ")";
        return r;

    }


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

        openOrigin(response, request);

        Integer insert = liuyanMapper.insert(liuyan);
        if (insert > 0) {
            // 发送邮件
            send_mail(liuyan);
            return ServerResponse.createBySuccessMessage("提交成功");
        }
        return ServerResponse.createBySuccessMessage("提交失败，请稍后重试");
    }

    /**
     * 发送邮件
     * @param liuyan 邮件内容
     */
    private void send_mail(Liuyan liuyan) {
       String content =   liuyan.getCreateTime().toString() + liuyan.getName() + "刚刚 在网站提交了试用申请留言，手机号码是： " +  liuyan.getMobile() + "回复内容为： " + liuyan.getContent() + "，请及时联系客户";
        pwdMailSender.sendMail("1570482304@qq.com", "收到一条网站申请试用留言-请及时处理", content);

    }


    @ResponseBody
    @RequestMapping("/select_liuyan.do")
    public ServerResponse<List<Liuyan>> select_liyan(HttpServletRequest request, HttpServletResponse response) {

        openOrigin(response, request);

        List<Liuyan> all = liuyanMapper.findAll();
        return ServerResponse.createBySuccess(all);
    }


    private  void  openOrigin(HttpServletResponse response,HttpServletRequest request) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    }

    @Autowired
   private PwdMailSender pwdMailSender;

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



}
