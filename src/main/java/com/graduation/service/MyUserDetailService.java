package com.graduation.service;

import com.graduation.bean.User;
import com.graduation.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService{

    private String type;



    @Autowired
    private UserDao userDao;
    private User userByName;
    
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println(type);
        if(type.equals("老师")){
            userByName = userDao.getTeaByNum(s);
        }else if(type.equals("学生")){
            userByName = userDao.getStuByNum(s);
        }
        UserDetails userDetails=null;
        if(userByName!=null){
            System.out.println(userByName.toString());
            Collection<GrantedAuthority> authorities = getAuthorities();
            userDetails=new org.springframework.security.core.userdetails.User(s,userByName.getPassword(),true,true,true,true, authorities);
        }
        return userDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_"+userByName.getType()));
        return authList;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
