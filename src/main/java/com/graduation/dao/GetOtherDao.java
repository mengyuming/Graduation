package com.graduation.dao;

import com.graduation.bean.Depart;
import com.graduation.bean.Professional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface GetOtherDao {


    @Select("SELECT * FROM depart where school=#{name}")
    List<Depart> getAllDepart(String name);

    @Select("SELECT * FROM professional where depart=#{name}")
    List<Professional> getAllProfession(String name);
}
