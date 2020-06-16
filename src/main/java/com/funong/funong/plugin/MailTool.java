package com.funong.funong.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.*;

/**
 * @Author: cytern
 * @Date: 2020/6/15 11:06
 */
@Component
public class MailTool {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String title,
                          String body,
                          String email){


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2477185748@qq.com");
        message.setTo(email);
        message.setSubject(title);
        message.setText(body);
       javaMailSender.send(message);


    }

    public  void sendSureEmail(
            String email,
            Integer id,
          String time
    ){
        Context context = new Context();
        context.setVariable("id", id);
        context.setVariable("time",time);
        String emailContent = templateEngine.process("MailTem", context);
        sendEmail("来自maxshop的邮箱验证",emailContent,email);
    }
}
