package com.sys.selectcourse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sys.selectcourse.entity.Course;
import com.sys.selectcourse.mapper.CourseMapper;
import com.sys.selectcourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;


    @Override
    public Course getCourseById(Integer id) {
        return courseMapper.selectById(id);
    }

    @Override
    public Course getOnlyCourseList() {
        return courseMapper.getOnlyCourseList();
    }

    @Override
    public List<Course> getCourseList(String roomName) {
        return courseMapper.selectList(new QueryWrapper<Course>().eq("room_name",roomName));
    }

    @Override
    public int addCourse(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateById(course);
    }

    @Override
    public int deleteCourse(Course course) {
        return courseMapper.deleteById(course);
    }

    @Override
    public int deleteAllCourse() {
        return courseMapper.delete(new QueryWrapper<>());
    }
}
