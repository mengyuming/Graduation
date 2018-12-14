package com.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailSendService {

    private ConcurrentHashMap<String,String> map=new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, String> getMap() {
        return map;
    }

    @Autowired
    private JavaMailSenderImpl javaMailSender;


    public void sendEmail(String email) {

        //生成随机验证码
        Integer i = (int) ((Math.random() * 9 + 1) * 100000);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("517868436@qq.com");
        message.setSubject("注册验证码");
        message.setText(i.toString());
        message.setTo(email);
        System.out.println(javaMailSender);
        javaMailSender.send(message);
        map.put(email,i.toString());
    }
}
