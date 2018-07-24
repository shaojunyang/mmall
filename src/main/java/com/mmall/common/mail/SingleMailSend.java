package com.mmall.common.mail;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SingleMailSend {
    static ApplicationContext actx = new ClassPathXmlApplicationContext(
            "applicationContext.xml");
    static MailSender sender = (MailSender) actx.getBean("mailSender");
    static SimpleMailMessage mailMessage = (SimpleMailMessage) actx.getBean("mailMessage");

    public static void main(String args[]) throws MessagingException {
        mailMessage.setSubject("你好");
        mailMessage.setText("这个是一个通过Spring框架来发送邮件的小程序");
        mailMessage.setTo("599909652@qq.com");
        sender.send(mailMessage);
    }
}
