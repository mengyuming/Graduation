package com.graduation.dao;

import com.graduation.bean.IndexSystem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IndexSystemDao {

    @Select("SELECT * FROM indexsystem where type1=#{type1} and type2=#{type2}")
    public IndexSystem getIndexsystem(String type1, String type2);
}
