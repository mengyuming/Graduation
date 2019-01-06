package com.graduation.service;

import com.graduation.bean.Depart;
import com.graduation.bean.Professional;
import com.graduation.dao.GetOtherDao;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOtherService {

    @Autowired
    private GetOtherDao getOtherDao;


    public List<Depart> getAllDepart(String name) {
        return getOtherDao.getAllDepart(name);
    }

    public List<Professional> getAllProfession(String name) {
        return getOtherDao.getAllProfession(name);
    }
}
