package com.graduation.bean;

import io.swagger.models.auth.In;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("index")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Index implements Serializable{

    private Integer id;
    //评价者
    private String pnumber;

    //被评者
    private String bnumber;

    //评价的日期
    private String times;

    //评价的课程信息
    private String cno;

    private String other;

    private Double q1;
    private Double q2;
    private Double q3;
    private Double q4;
    private Double q5;
    private Double q6;
    private Double q7;
    private Double q8;
    private Double q9;
    private Double q10;
    private Double q11;
    private Double q12;
    private Double q13;
    private Double q14;
    private Double q15;
    private Double q16;
    private Double q17;
    private Double q18;
    private Double q19;
    private Double q20;
    private Double total;

}
