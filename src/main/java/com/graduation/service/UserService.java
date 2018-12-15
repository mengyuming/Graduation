package com.graduation.service;


import com.graduation.bean.User;
import com.graduation.Handler.MyAuthenticationProvider;
import com.graduation.controller.EmailMessageController;
import com.graduation.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Service
@CacheConfig(cacheNames = "user")
public class UserService {

	@Autowired
    private UserDao userDao;

	@Autowired
    EmailMessageController emailMessageController;

	@Autowired
    EmailSendService emailSendService;

	@Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    AuthenticationFailureHandler myAuthenticationFailHandler;

	@Transactional(rollbackFor = Exception.class)
    public void userRegister(String type, String stunum, String name, String school, String password, String grade, String email, String text) {
        String messages = emailMessageController.getMessages();
        //判断验证码是否正确
            if(messages.equals(text)){
                if (type != null && type.equals("学生")) {
                    User stu = new User();
                    stu.setType(type);
                    stu.setName(name);
                    stu.setStunum(stunum);
                    stu.setSchool(school);
                    stu.setPassword(password);
                    stu.setGrade(grade);
                    stu.setEmail(email);
                    userDao.stuRegister(stu);

                } else if (type != null && type.equals("老师")) {
                    User tea = new User();
                    tea.setType(type);
                    tea.setName(name);
                    tea.setStunum(stunum);
                    tea.setSchool(school);
                    tea.setPassword(password);
                    tea.setEmail(email);
                    userDao.teaRegister(tea);
                }
            }
    }

    @Transactional(rollbackFor = Exception.class)
    public void testUserLogin(String stunum, String password, String type, HttpServletRequest request, HttpServletResponse response) {
        myUserDetailService.setType(type);
        System.out.println("--------------开始认证");
        String number = stunum.trim();
        //创建一个UsernamePasswordAuthenticationToken对象，封装我们接收到的前端发来的用户名与密码
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(number,password);
        System.out.println("1");
        //调用AuthenticationManager的authenticate方法来进行登陆验证。
        //得到的是验证登陆信息之后，重新封装的创建一个UsernamePasswordAuthenticationToken对象
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        System.out.println("3");

        if(!authenticate.equals(usernamePasswordAuthenticationToken)){
            //创建一个SecurityContext对象，并且设置上一步得到的Authentication
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            HttpSession session = request.getSession();

            //将上一部得到的SecurityContext对象放入session中。到此，自定义用户信息的处理已经完成
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            System.out.println(usernamePasswordAuthenticationToken);
            System.out.println("--------------结束认证");
            System.out.println("将用户信息存储在session中");
            User user = userLogin(stunum, type);
            System.out.println(user.toString());
            session.setAttribute("user",user);
            try{
                myAuthenticationSuccessHandler.onAuthenticationSuccess(request,response,authenticate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else{
            System.out.println("--------------------------------null");
            try{
                myAuthenticationFailHandler.onAuthenticationFailure(request,response,new AuthenticationServiceException("登陆失败"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Cacheable(key="#result.id")
    @Transactional(rollbackFor = Exception.class)
    public User getInformation(User user) {
        if (user != null && user.getType().equals("学生")) {
            User newuser = userDao.getstuInformation(user.getId());
            return newuser;

        } else if (user != null && user.getType().equals("老师")) {
            User newuser = userDao.getteaInformation(user.getId());
            return newuser;
        }
      return null;
    }

    @CachePut(key="#result.id")
    @Transactional(rollbackFor = Exception.class)
    public void updateInformation(User user,String gender,String age,
                                  String depart,String professional,String telephone) {
        if(user!=null&&user.getType() != null) {
            user.setGender(gender);
            user.setAge(age);
            user.setDepart(depart);
            user.setProfessional(professional);
            user.setTelephone(telephone);
            if(user.getType().equals("老师")){
                userDao.updateTeaInformation(user);
            }else if(user.getType().equals("学生")){
                userDao.updateStuInformation(user);
            }
        }
    }

    public User getbackPasswordForEmail(String number,String type) {
	    User user=null;
	    if(type.equals("老师")){
            user = userDao.getTeaByStunum(number);

        }else if(type.equals("学生")){
           user= userDao.getStuByTeanum(number);
        }
        return user;
    }

    public void getbackPassword(Integer id,String number, String type, String password) {
        User user = new User();
        user.setId(id);
        user.setStunum(number);
        user.setPassword(password);
        if (type.equals("老师")) {
            userDao.updateTeaInformation(user);

        } else if (type.equals("学生")) {
            userDao.updateStuInformation(user);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Cacheable(key="#result.id")
    public User userLogin(String stunum, String type) {
        if (type != null && type.equals("学生")) {
            User user = userDao.stuLogin(stunum);
            return user;
        }else if(type != null && type.equals("老师")){
            User user = userDao.teaLogin(stunum);
            return user;
        }
        return null;
    }
}
