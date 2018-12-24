package com.graduation.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/email")
public class EmailMessageController {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    private String messages;

    public String getMessages() {
        return messages;
    }

    @RequestMapping(value = "/getEmailMessage",method = RequestMethod.GET)
    @ApiOperation("获取注册验证码")
    public String sendMail(String to){
        //生成随机验证码
        Integer i = (int) ((Math.random() * 9 + 1) * 100000);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("mym517868436@126.com");
        message.setSubject("注册验证码");
        message.setText(i.toString());
        message.setTo(to);
        System.out.println(javaMailSender);
        javaMailSender.send(message);
        this.messages=i.toString();
        return "success";
    }
}
