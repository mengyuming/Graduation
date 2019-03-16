package com.graduation.dao;

import com.graduation.bean.Site;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface Site1Dao {

    @Insert("insert into site (cno,name,city,longitude,latitude) values (#{cno},#{name},#{city},#{longitude},#{latitude})")
    public void addSite(Site site);


    @Insert("insert into sitevalue (cno,date,hour,type,value) values (#{cno},#{date},#{hour},#{type},#{value})")
    public void updateSite(Site site1);
}
