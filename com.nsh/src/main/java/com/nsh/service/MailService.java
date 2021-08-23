package com.nsh.service;

import com.nsh.pojo.MailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author NSH
 * @create 2021-08-13 20:42
 */
@Service
public class MailService {
    //专门发送邮件的类
    @Autowired
    private JavaMailSender javaMailSender;
    //读取参数 发送方配置
    @Value("${spring.mail.username}")
    private String form;

    /**
     * 发送邮件服务
     */
    public void sendHtmlMail(MailVo mailVo){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            //封装了发送邮件的对象 表示发送的邮件是否是富文本
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            //发送方
            mimeMessageHelper.setFrom(form);
            //接收方
            mimeMessageHelper.setTo(mailVo.getTo());
            //发送的主题
            mimeMessageHelper.setSubject(mailVo.getTitle());
            //邮件主题,true表示第二个正文是否为html
            mimeMessageHelper.setText(mailVo.getContent(),true);
            //发送操作
            javaMailSender.send(mimeMessage);
            System.out.println("发送成功");

        } catch (MessagingException e) {
            System.out.println("发送失败");
        }

    }
}
