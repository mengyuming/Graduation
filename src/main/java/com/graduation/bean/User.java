package com.graduation.bean;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("user")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

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

//    public String getText() {
//        return text;
//    }
//    public void setText(String text) {
//        this.text = text;
//    }
//    public String getGrade() {
//		return grade;
//	}
//	public void setGrade(String grade) {
//		this.grade = grade;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getDepart() {
//		return depart;
//	}
//	public void setDepart(String depart) {
//		this.depart = depart;
//	}
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	public String getStunum() {
//		return stunum;
//	}
//	public void setStunum(String stunum) {
//		this.stunum = stunum;
//	}
//	public String getSchool() {
//		return school;
//	}
//	public void setSchool(String school) {
//		this.school = school;
//	}
//	public String getProfessional() {
//		return professional;
//	}
//	public void setProfessional(String professional) {
//		this.professional = professional;
//	}
//	public String getTelephone() {
//		return telephone;
//	}
//	public void setTelephone(String telephone) {
//		this.telephone = telephone;
//	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getGender() {
//		return gender;
//	}
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//	public String getAge() {
//		return age;
//	}
//	public void setAge(String age) {
//		this.age = age;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "text='" + text + '\'' +
//                ", id=" + id +
//                ", stunum='" + stunum + '\'' +
//                ", school='" + school + '\'' +
//                ", professional='" + professional + '\'' +
//                ", telephone='" + telephone + '\'' +
//                ", type='" + type + '\'' +
//                ", password='" + password + '\'' +
//                ", gender='" + gender + '\'' +
//                ", age=" + age +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", depart='" + depart + '\'' +
//                ", grade='" + grade + '\'' +
//                '}';
//    }
}
