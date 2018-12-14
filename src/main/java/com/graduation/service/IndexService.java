package com.graduation.service;

import com.graduation.bean.Index;
import com.graduation.dao.IndexDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class IndexService {

    @Autowired
    private IndexDao indexsystemDao;

    public void addAllIndex(Index indexsystem) {
        indexsystemDao.addAllIndex(indexsystem);
    }

    public List<Index> getMyIndex() {
        //从springsecurity中获取登陆用户的信息
        UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取登陆的用户账号
        String usernumber = userDetail.getUsername();
        System.out.println(usernumber);
        //获取用户的权限，本系统则为用户的角色，即老师还是学生
        Collection<? extends GrantedAuthority> authorities = userDetail.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            System.out.println(role);
        }

        List<Index> myIndex = indexsystemDao.getMyIndex(usernumber);
        return myIndex;
    }

    public List<Index> getOtherIndex() {
        //从springsecurity中获取登陆用户的信息
        UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取登陆的用户账号
        String usernumber = userDetail.getUsername();
        if(usernumber!=null){
            List<Index> myIndex = indexsystemDao.getOtherIndex(usernumber);
            return myIndex;
        }
        return null;
    }

    public List<Index> getAllIndex() {
        UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernumber = userDetail.getUsername();
        if(usernumber!=null){
            List<Index> myIndex = indexsystemDao.getAllIndex();
            return myIndex;
        }
        return null;
    }
}
