package com.graduation.dao;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.bean.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseDao extends BaseMapper<Course>{

    CopyOnWriteArrayList<Course> getCourseByTeaName(@Param("message") String message);

    CopyOnWriteArrayList<Course> getCourseByName(@Param("message") String message);

    CopyOnWriteArrayList<Course> getCourseByTeaNo(@Param("message") String message);

    CopyOnWriteArrayList<Course> getTeaCourse(@Param("id") Integer id);

	CopyOnWriteArrayList<Course> getStuCourse(@Param("professional") String professional, @Param("grade") String grade);

    List<Course> getCourseByCno(@Param("cno")String cno);

    @Select("select * from course where depart=#{depart} and pname=#{professional} and cname=#{coursename} and tname=#{teaname}")
    List<Course> getTeaCourse(String depart, String professional, String coursename,String teaname);
}
