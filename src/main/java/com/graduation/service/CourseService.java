package com.graduation.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.bean.Course;
import com.graduation.bean.Index;
import com.graduation.bean.User;
import com.graduation.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "course",cacheManager = "courseRedisCacheManager")
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    @Qualifier("courseRedisCacheManager")
    @Autowired
    CacheManager courseRedisCacheManager;

    @Autowired
    RedisTemplate redisTemplate;


    @Transactional(rollbackFor = Exception.class)
    public List<Course> getChoice(String type, String messages) {
        if (type.equals("姓名")) {
            List<Course> course = courseDao.getCourseByTeaName(messages);
            return course;
        } else if (type.equals("课程")) {
            List<Course> course = courseDao.getCourseByName(messages);
            return course;
        } else if (type.equals("工号")) {
            List<Course> course = courseDao.getCourseByTeaNo(messages);
            return course;
        }
        return null;
    }


    @Transactional(rollbackFor = Exception.class)
    public Map<String, Course> getCourseList(User user) {
        String type = user.getType();
        if (type != null && type.equals("学生")) {
//            Map<String,Course> maps= redisTemplate.opsForHash().entries(user.getId()+"map1");
//            if(maps!=null&&!maps.isEmpty()){
//                return maps;
//            }
            List<Course> stuCourse = courseDao.getStuCourse(user.getProfessional(), user.getGrade());
            ConcurrentHashMap<String, Course> map = new ConcurrentHashMap<>();
            for (int i = 0; i < stuCourse.size(); i++) {
                map.put(i + "1", stuCourse.get(i));
            }
            //redisTemplate.opsForHash().putAll(user.getId()+"map1",map);

            return map;

        } else if (type != null && type.equals("老师")) {
//            Map<String,Course> maps= redisTemplate.opsForHash().entries(user.getId()+"map2");
//            if(maps!=null&&!maps.isEmpty()){
//                return maps;
//            }
            List<Course> stuCourse = courseDao.getTeaCourse(user.getId());
            ConcurrentHashMap<String, Course> map = new ConcurrentHashMap<>();
            for (int i = 0; i < stuCourse.size(); i++) {
                map.put(i + "1", stuCourse.get(i));
            }
            //redisTemplate.opsForHash().putAll(user.getId()+"map2",map);

            return map;

        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Course> getCourseByCno(String cno) {
        return courseDao.getCourseByCno(cno);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Course> getTeaCourse(String depart, String professional, String coursename,String teaname) {
        return courseDao.getTeaCourse(depart,professional,coursename,teaname);
    }
}

