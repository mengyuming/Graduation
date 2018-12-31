package com.graduation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.graduation.bean.User;
import com.graduation.service.EmailSendService;
import com.graduation.service.MyUserDetailService;
import com.graduation.service.UserService;
import com.graduation.tools.ControMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
    private MyUserDetailService myUserDetailService;

	@Autowired
    private EmailSendService emailSendService;

	@Autowired
    private PasswordEncoder passwordEncoder;

    private ControMessage controMessage=new ControMessage();
	//用户注册
    @ApiOperation("用户注册")
    @RequestMapping(value = "/userRegister",method = RequestMethod.POST)
	public ControMessage userRegister(String type, String stunum, String name, String school, String password, String grade,
			String email,String text,HttpServletRequest request) {
        String encodepassword = passwordEncoder.encode(password);
        userService.userRegister(type,stunum,name,school,encodepassword,grade,email,text);

        User user = userService.userLogin(stunum,type);
        if (user != null) {
            controMessage.setStatus(200).setMessage("操作成功");
            request.getSession().setAttribute("user",user);
            return controMessage;
        }
        controMessage.setStatus(100).setMessage("注册失败");
        return controMessage;
	}

	//用户登陆
    @ApiOperation("用户登陆")
	@RequestMapping(value="/logins",method=RequestMethod.POST)
    public void userLogin(String username, String password, String type, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(username+"---"+password+"---"+type);
            myUserDetailService.setType(type);
        userService.testUserLogin(username, password,type,request,response);
        User user = (User)request.getSession().getAttribute("user");
        System.out.println("查询session中的user");

	}

	//获取已经登陆的用户的信息
    @ApiOperation("获取已经登陆的用户的信息")
    @RequestMapping(value = "/getMine",method = RequestMethod.GET)
	public ControMessage getMine(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            controMessage.setStatus(200).setMessage("获取用户信息成功！").setAll().add(user);
            return controMessage;
        }
        controMessage.setStatus(100).setMessage("获取用户信息失败，请登陆之后再获取！");
		return controMessage;
	}

	//获取用户的详细信息
    @ApiOperation("获取用户的详细信息")
    @RequestMapping(value = "/getInformation",method = RequestMethod.GET)
	public ControMessage getInformation(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
        User newuser = userService.getInformation(user);
        if(newuser!=null){
            controMessage.contrlSuccess().setMessage("获取用户信息成功").setAll().add(newuser);
            return controMessage;
        }
        controMessage.contrlError().setMessage("获取用户信息失败！请登陆之后再尝试");
        return controMessage;
	}

	// 更新用户信息
    @ApiOperation("更新用户信息")
	@RequestMapping(value = "/updateinformation",method = RequestMethod.POST)
	public ControMessage updateInformation(String gender, String age, String depart, String professional, String telephone,
			String email,String nation,String signature,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		userService.updateInformation(user,gender,age,depart,professional,telephone,email,nation,signature);
        controMessage.contrlSuccess().setMessage("更新成功");
        return controMessage;
	}

    //发送验证码
    @ApiOperation("根据用户名获取注册时填写的邮箱，然后发送验证码")
    @RequestMapping(value = "/getbackPasswordForEmail",method = RequestMethod.GET)
    public ControMessage getbackPasswordForEmail(String number,String type){

        User user = userService.getbackPasswordForEmail(number, type);
        if(user!=null){
            String email=user.getEmail();
            System.out.println(user);
            emailSendService.sendEmail(email);
            controMessage.contrlSuccess().setMessage("验证码已经发送");
            return controMessage;
        }
        controMessage.contrlError().setMessage("该用户不存在！");
        return controMessage;
    }
    //找回密码，修改密码
    @ApiOperation("检查验证码是否匹配，进而更改密码")
    @RequestMapping(value="/getbackPassword",method = RequestMethod.POST)
    public ControMessage getbackPassword(String number,String type,String message,String password){
        User user = userService.getbackPasswordForEmail(number, type);
        if(user!=null){
            if(emailSendService.getMap().get(user.getEmail()).equals(message)){
                User newuser=new User();
                String encode = passwordEncoder.encode(password);
                newuser.setId(user.getId());
                newuser.setStunum(number);
                newuser.setPassword(encode);
                userService.getbackPassword(newuser,type);
                controMessage.contrlSuccess().setMessage("密码修改成功");
                return controMessage;
            }
        }
        controMessage.contrlError().setMessage("验证码不匹配，请重发");
        return controMessage;
    }

    @ApiOperation("修改密码")
    @RequestMapping(value="/updatePassword",method = RequestMethod.POST)
    public ControMessage updatePassword(String oldpassword,String newpassword,HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        boolean matches = passwordEncoder.matches(oldpassword, user.getPassword());
        if(matches){
            user.setPassword(newpassword);
            userService.updatePassword(user);
            controMessage.contrlSuccess().setMessage("更新成功");
            return controMessage;
        }
        controMessage.contrlError().setMessage("更新失败！原密码错误！");
        return controMessage;
    }

    @ApiOperation("获取本专业所有老师，不一定每个老师都有带课")
    @RequestMapping(value="/getMyAllTeacher",method = RequestMethod.GET)
    public ControMessage getMyAllTeacher(HttpServletRequest request){
        List<User> user= userService.getMyAllTeacher(request);
        if(user!=null&&user.size()>0){
            controMessage.contrlSuccess().setMessage("操作成功").setAll().add(user);
            return controMessage;
        }
        controMessage.contrlError().setMessage("操作失败！");
        return controMessage;
    }
}
