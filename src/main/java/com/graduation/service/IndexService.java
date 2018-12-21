package com.graduation.service;

import com.graduation.bean.Index;
import com.graduation.bean.User;
import com.graduation.dao.IndexDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class IndexService {

    @Autowired
    private IndexDao indexsystemDao;

    @Transactional(rollbackFor = Exception.class)
    public void addAllIndex(Index index,HttpServletRequest request) {
        index.setTimes(LocalDate.now().toString());
        User user = (User)request.getSession().getAttribute("user");
        UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernumber1 = userDetail.getUsername();
        System.out.println(usernumber1);
        String usernumber = user.getStunum();
        if(usernumber!=null && user!=null){
            if(user.getType().equals("学生")){
                indexsystemDao.addSquestion(index);
            }else if(user.getType().equals("老师")){
                indexsystemDao.addTquestion(index);
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public CopyOnWriteArrayList<Index> getMyIndex(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        //从springsecurity中获取登陆用户的信息
        String usernumber1 = SecurityContextHolder.getContext().getAuthentication().getName();
        //获取登陆的用户账号
        ;
        System.out.println(usernumber1);
        String usernumber = user.getStunum();

        //获取用户的权限，本系统则为用户的角色，即老师还是学生
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            System.out.println(role);
        }
        if(usernumber!=null){
                if(user.getType().equals("学生")){
                    CopyOnWriteArrayList<Index> myIndex = indexsystemDao.getSquestionMyIndex(usernumber);
                    return myIndex;
                }else if(user.getType().equals("老师")){
                    CopyOnWriteArrayList<Index> myIndex = indexsystemDao.getTquestionMyIndex(usernumber);
                    return myIndex;
                }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public CopyOnWriteArrayList<Index> getOtherIndex(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        String usernumber1 = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(usernumber1);
        String usernumber=user.getStunum();
        if(usernumber!=null){
                if(user.getType().equals("学生")){
                    CopyOnWriteArrayList<Index> myIndex = indexsystemDao.getSquestionOtherIndex(usernumber);
                    return myIndex;
                }else if(user.getType().equals("老师")){
                    CopyOnWriteArrayList<Index> myIndex = indexsystemDao.getTquestionOtherIndex(usernumber);
                    return myIndex;
                }
            return null;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public ConcurrentHashMap<String,List<Index>> getAllIndex() {

        String usernumber = SecurityContextHolder.getContext().getAuthentication().getName();
        ConcurrentHashMap<String,List<Index>> map=new ConcurrentHashMap<>();
        if(usernumber!=null){
            CopyOnWriteArrayList<Index> mySIndex = indexsystemDao.getAllSquestionIndex();
            CopyOnWriteArrayList<Index> myTIndex = indexsystemDao.getAllTquestionIndex();
            map.put("s",mySIndex);
            map.put("t",myTIndex);
            return map;
        }
        return null;
    }
}
