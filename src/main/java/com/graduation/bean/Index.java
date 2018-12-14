package com.graduation.bean;

import org.apache.ibatis.type.Alias;

@Alias("index")
public class Index {
    private Integer id;
    //评价者
    private String number1;

    //被评者
    private String number2;

    //评价的日期
    private String time;

    //评价的课程信息
    private Course course;

}
