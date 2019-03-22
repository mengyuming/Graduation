package com.graduation.controller;

import com.graduation.bean.User;
import com.graduation.dao.IndexSystemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/indexsystem")
public class IndexSystemController {

    @Autowired
    private IndexSystemDao indexSystemDao;

    @GetMapping("/getIndexsystem")
    public Object getIndexsystem(String type2, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null){
            String type1=user.getType();
            return indexSystemDao.getIndexsystem(type1,type2);
        }
        return null;
    }
}
