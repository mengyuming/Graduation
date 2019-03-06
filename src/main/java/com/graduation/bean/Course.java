package com.graduation.bean;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("course")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Course implements Serializable{

	private Integer id;
	private String cno;
	private String weekth;
	private String time;
	private Integer tid;
	private Integer grade;
	private String cname;
	private String day;
	private String pname;
	private String place;
	private String depart;
	private String campus;
	private Integer studentnumber;
	private String tname;
	private Integer type;

	
}
