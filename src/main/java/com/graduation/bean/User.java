package com.graduation.bean;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("user")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable{

    private String text;
	private Integer id;
	private String stunum;
	private String school;
	private String professional;
	private String telephone;
	private String type;
	private String password;
	private String gender;
	private String age;
	private String name;
	private String email;
	private String depart;
	private String grade;
	private String nation;
	private String signature;

//
}
