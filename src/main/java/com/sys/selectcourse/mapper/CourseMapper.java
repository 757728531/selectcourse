package com.sys.selectcourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sys.selectcourse.entity.Course;
import org.apache.ibatis.annotations.Select;

public interface CourseMapper  extends BaseMapper<Course> {
    //查询带有{第1-开头的课程
    @Select("select * from course where weekWeek LIKE '{第1%' LIMIT 1")
    public Course getOnlyCourseList();
}
