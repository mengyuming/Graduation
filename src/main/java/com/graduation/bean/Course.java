package com.graduation.bean;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("course")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable{

	private Integer id;
	private String cno;
	private Integer weekth;
	private Integer time;
	private Integer tid;
	private Integer grade;
	private String cname;
	private String day;
	private String pname;
	private String place;
	
//	public String getPlace() {
//		return place;
//	}
//	public void setPlace(String place) {
//		this.place = place;
//	}
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	public String getCno() {
//		return cno;
//	}
//	public void setCno(String cno) {
//		this.cno = cno;
//	}
//	public Integer getWeekth() {
//		return weekth;
//	}
//	public void setWeekth(Integer weekth) {
//		this.weekth = weekth;
//	}
//	public Integer getTime() {
//		return time;
//	}
//	public void setTime(Integer time) {
//		this.time = time;
//	}
//	public Integer getTid() {
//		return tid;
//	}
//	public void setTid(Integer tid) {
//		this.tid = tid;
//	}
//	public Integer getGrade() {
//		return grade;
//	}
//	public void setGrade(Integer grade) {
//		this.grade = grade;
//	}
//	public String getCname() {
//		return cname;
//	}
//	public void setCname(String cname) {
//		this.cname = cname;
//	}
//	public String getDay() {
//		return day;
//	}
//	public void setDay(String day) {
//		this.day = day;
//	}
//	public String getPname() {
//		return pname;
//	}
//	public void setPname(String pname) {
//		this.pname = pname;
//	}
//	@Override
//	public String toString() {
//		return "Course [id=" + id + ", cno=" + cno + ", weekth=" + weekth + ", time=" + time + ", tid=" + tid
//				+ ", grade=" + grade + ", cname=" + cname + ", day=" + day + ", pname=" + pname + ", place=" + place
//				+ "]";
//	}
	
}
