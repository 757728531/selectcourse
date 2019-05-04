package com.sys.selectcourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

//课程表
@TableName("Course")
public class Course implements Serializable{
	/**
	 * 
	 */
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	@TableField("course_week")
	private String courseWeek;//上课星期
	@TableField("course_node")
	private String courseNode;//上课节次
	@TableField("course_name")
	private String courseName;//课程名称
	@TableField("course_teacher")
	private String courseTeacher;//上课教师
	@TableField("room_name")
	private String roomName;//上课机房
	@TableField("start_date")
	private Date startDate;//开始时间
	@TableField("end_date")
	private Date endDate;//结束时间
	@TableField("week_week")
	private String weekWeek;//起始周次
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCourseWeek() {
		return courseWeek;
	}
	public void setCourseWeek(String courseWeek) {
		this.courseWeek = courseWeek;
	}
	public String getCourseNode() {
		return courseNode;
	}
	public void setCourseNode(String courseNode) {
		this.courseNode = courseNode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseTeacher() {
		return courseTeacher;
	}
	public void setCourseTeacher(String courseTeacher) {
		this.courseTeacher = courseTeacher;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getWeekWeek() {
		return weekWeek;
	}
	public void setWeekWeek(String weekWeek) {
		this.weekWeek = weekWeek;
	}
	
	
}
