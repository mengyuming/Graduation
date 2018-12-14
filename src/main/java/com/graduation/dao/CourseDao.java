package com.graduation.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.bean.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseDao extends BaseMapper<Course>{

	List<Course> getCourseByTeaName(@Param("message") String message);

	List<Course> getCourseByName(@Param("message") String message);

	List<Course> getCourseByTeaNo(@Param("message") String message);

	List<Course> getTeaCourse(@Param("id") Integer id);

	List<Course> getStuCourse(@Param("professional") String professional, @Param("grade") String grade);

}
