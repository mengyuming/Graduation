package com.graduation.dao;

	
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User>{

	public void stuRegister(@Param("user") User user);

	public boolean teaRegister(@Param("user") User user);

	public User stuLogin(@Param("num") String num);

	public User teaLogin(@Param("num") String num);

	public User getstuInformation(@Param("id") Integer id);

	public User getteaInformation(@Param("id") Integer id);

	public void updateStuInformation(@Param("user") User user);
	
	public void updateTeaInformation(@Param("user") User user);

    @Select("SELECT * FROM teacher where stunum=#{number}")
    public User getTeaByStunum(@Param("number") String number);

    @Select("SELECT * FROM student where stunum=#{number}")
    public User getStuByTeanum(@Param("number") String number);

    @Select("SELECT * FROM teacher where stunum=#{s}")
    public User getTeaByNum(@Param("s") String s);

    @Select("SELECT * FROM student where stunum=#{s}")
    public User getStuByNum(@Param("s") String s);

    @Select("SELECT * FROM student where id=#{id}")
    public User getStuById(@Param("id")Integer id);

    @Select("SELECT * FROM teacher where id=#{id}")
    public User getTeaById(@Param("id")Integer id);

    @Select("SELECT * FROM teacher where professional=#{professional}")
    public List<User> getMyAllTeacher(@Param("professional") String professional);

    @Select("SELECT * FROM manager where stunum=#{s}")
    public User getManager(String s);

    @Select("select * from teacher")
    List<User> getAllTeacher();
}
