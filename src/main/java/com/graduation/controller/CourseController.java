package com.graduation.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.graduation.bean.Course;
import com.graduation.bean.User;
import com.graduation.service.CourseService;
import com.graduation.tools.ControMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@ResponseBody
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

    private ControMessage controMessage=new ControMessage();
	@RequestMapping(value = "/choice",method = RequestMethod.GET)
    @ApiOperation("获取需要评教的课程")
	public ControMessage getChoice(String type,String messages) {
		if(type!=null) {
			List<Course> course = courseService.getChoice(type,messages);
			if(course!=null) {
                controMessage.contrlSuccess().setMessage("获取课表信息成功").setAll().add(course);
				return controMessage;
			}
		}
        controMessage.contrlError().setMessage("获取课表信息失败！请出入正确的信息");
        return controMessage;
	}

    @RequestMapping(value = "/getCourseList",method = RequestMethod.GET)
    @ApiOperation("获取用户个人课表")
	public ControMessage getCourseList(HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("user");
        Map<String, Course> courseList = courseService.getCourseList(user);
        System.out.println(courseList);
        if(courseList!=null){
            controMessage.contrlSuccess().setMessage("获取课表信息成功").setAll().add(courseList);
            return controMessage;
        }
        controMessage.contrlError()
                .setMessage("获取课表信息失败！请出入正确的信息");
        return controMessage;
	}
}
