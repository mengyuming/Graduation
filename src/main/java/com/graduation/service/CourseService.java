package com.graduation.service;

import java.util.List;

import com.graduation.bean.Course;
import com.graduation.bean.User;
import com.graduation.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "course")
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    @Transactional(rollbackFor = Exception.class)
    public List<Course> getChoice(String type, String messages) {
        if (type.equals("name")) {
            List<Course> course = courseDao.getCourseByTeaName(messages);
            return course;
        } else if (type.equals("course")) {
            List<Course> course = courseDao.getCourseByName(messages);
            return course;
        } else if (type.equals("id")) {
            List<Course> course = courseDao.getCourseByTeaNo(messages);
            return course;
        }
        return null;
    }


    @Cacheable(key="#result.id")
    @Transactional(rollbackFor = Exception.class)
    public List<Course> getCourseList(User user) {
        String type = user.getType();
        if (type != null && type.equals("学生")) {
            List<Course> stuCourse = courseDao.getStuCourse(user.getProfessional(), user.getGrade());
            return stuCourse;

        } else if (type != null && type.equals("老师")) {
            List<Course> teaCourse = courseDao.getTeaCourse(user.getId());
            return teaCourse;
        }
        return null;
    }
}
