package com.sys.selectcourse.service;

import com.sys.selectcourse.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseService {
    //查询带有{第1-开头的课程
    public Course getOnlyCourseList();
    //课表查看
    public List<Course> getCourseList(String roomName);
    //课程添加
    public int addCourse(Course course);
    //课表更改
    public int updateCourse(Course course);
    //课表删除
    public int deleteCourse(Course course);
    //格式化课表
    public int deleteAllCourse();

    public Course getCourseById(Integer id);
}
