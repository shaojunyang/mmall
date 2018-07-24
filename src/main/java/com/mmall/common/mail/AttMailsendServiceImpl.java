package com.mmall.common.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class AttMailsendServiceImpl{





    private static final JavaMailSender javaMailSender = new JavaMailSenderImpl();

    /**(非 Javadoc)
     * <p>Title: sendMessage</p>
     * <p>Description(描述):发送带附件的邮件 </p>
//     * @see org.service.IMailsendService#sendMessage()
     */

    public void sendMessage() {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom("jerry@mail.com");
            helper.setTo("599909652@qq.com");
            helper.setSubject("带附件的邮件");
            //普通格式的
            helper.setText("发送一个附件内容！<a href='http://www.baidu.com'>百度搜索</a>");
//            html格式的
//            helper.setText("发送一个附件内容！<a href='http://www.baidu.com'>百度搜索</a>",true);
            //添加附件1
//            ClassPathResource file1 = new ClassPathResource("/org/doc/doc.txt");
//            helper.addAttachment(file1.getFilename(),file1.getFile());
            //添加附件2
//            ClassPathResource file2 = new ClassPathResource("/org/doc/text.txt");
//            helper.addAttachment(file2.getFilename(), file2.getFile());
            javaMailSender.send(message);

        } catch (MessagingException e) {
            // TODO 异常执行块！
            e.printStackTrace();
        } catch (Exception e) {
            // TODO 异常执行块！
            e.printStackTrace();
        }

    }

//    public JavaMailSender getJavaMailSender() {
//        return javaMailSender;
//    }
//
//    public void setJavaMailSender(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
}
