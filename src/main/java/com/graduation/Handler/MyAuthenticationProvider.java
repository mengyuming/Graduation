package com.graduation.Handler;

import com.graduation.service.MyUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

import java.util.Collection;

public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;


    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("前端传过来的明文密码:" + password);
        String encode = passwordEncoder.encode(password);
        System.out.println("加密之后的密码："+encode);
        //调用UserDetailService的loadUserByUsername方法，来获取从数据库得到的对象信息
        UserDetails user = myUserDetailService.loadUserByUsername(username);

          if(user==null){
              System.out.println("用户不存在");
              return authentication;
          }
        //加密过程在这里体现
        System.out.println("结果CustomUserDetailsService后，已经查询出来的数据库存储密码:" + user.getPassword());
        //判断前端传过来的密码与从数据库得到的是否一致。
        if (!passwordEncoder.matches(password,user.getPassword())) {
            System.out.println("密码错误！！！");
            return authentication;
        }
        //封装用户的权限，将其封装成如下类型的集合
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        //重新封装UsernamePasswordAuthenticationToken对象，并将其返回。
        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, authentication.getCredentials(), authorities);
        System.out.println("2");
        System.out.println(usernamePasswordAuthenticationToken.toString());
        usernamePasswordAuthenticationToken.setDetails(user);
        return usernamePasswordAuthenticationToken;
    }
 
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}