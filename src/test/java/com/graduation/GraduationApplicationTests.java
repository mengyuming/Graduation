package com.graduation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.graduation.bean.Course;
import com.graduation.bean.User;
import com.graduation.dao.CourseDao;
import com.graduation.dao.UserDao;
import com.graduation.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GraduationApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CourseDao courseDao;
	@Test
	public void contextLoads() {
        //生成随机验证码
//        Integer i = (int) ((Math.random() * 9 + 1) * 100000);
//        SimpleMailMessage message=new SimpleMailMessage();
//        message.setFrom("517868436@qq.com");
//        message.setSubject("注册验证码");
//        message.setText(i.toString());
//        message.setTo("517868436@qq.com");
//        System.out.println(javaMailSender);
//        javaMailSender.send(message);

        String encode = passwordEncoder.encode("111");
        System.out.println("第一次对111加密"+encode);
        String encode1=passwordEncoder.encode("111");
        System.out.println("第二次对111加密"+encode1);

        boolean matches1 = passwordEncoder.matches("111", encode);
        System.out.println("比较没加密的跟第一次加密之后得到的密文"+matches1);
        boolean matches2 = passwordEncoder.matches("222", encode1);
        System.out.println("比较没加密的跟第二次加密之后得到的密文"+matches2);
//        IPage<Course> id = courseDao.selectPage(
//                new Page<Course>(2, 3),
//                new QueryWrapper<Course>()
//                        .eq("id", 2)
//        );
//
//        System.out.println(id.getCurrent());
//        System.out.println(id.getPages());
//        System.out.println(id.getRecords());
//        System.out.println(id.getSize());
//        System.out.println(id.getTotal());
    }

}
