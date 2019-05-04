package com.sys.selectcourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

//预约信息
@TableName("Subscribe")
public class Subscribe implements Serializable{

	/**
	 * 
	 */
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableId(value = "id",type=IdType.AUTO)
	private Integer id;
	@TableField("user_id")
	private String userId;//用户名称
	@TableField("subscribe_room")
	private String subscribeRoom;//申请机房
	@TableField("subscribe_week")
	private String subscribeWeek;//预约星期
	@TableField("subscribe_node")
	private String subscribeNode;//预约节次
	//@JSONField(format="yyyy年MM月dd日")
	@TableField("subscribe_date")
	private Date subscribeDate;//申请时间
	@TableField("subscribe_content")
	private String subscribeContent;//申请原因
	@TableField("subscribe_status")
	private String subscribeStatus;//状态 0正在审核 1审核通过 2审核未通过
	@TableField("subscribe_time")
	private String subscribeTime;//时间备注
	@TableField("user_name")
	private String userName;//用户名
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubscribeRoom() {
		return subscribeRoom;
	}
	public void setSubscribeRoom(String subscribeRoom) {
		this.subscribeRoom = subscribeRoom;
	}
	public String getSubscribeWeek() {
		return subscribeWeek;
	}
	public void setSubscribeWeek(String subscribeWeek) {
		this.subscribeWeek = subscribeWeek;
	}
	public String getSubscribeNode() {
		return subscribeNode;
	}
	public void setSubscribeNode(String subscribeNode) {
		this.subscribeNode = subscribeNode;
	}
	public Date getSubscribeDate() {
		return subscribeDate;
	}
	public void setSubscribeDate(Date subscribeDate) {
		this.subscribeDate = subscribeDate;
	}
	public String getSubscribeContent() {
		return subscribeContent;
	}
	public void setSubscribeContent(String subscribeContent) {
		this.subscribeContent = subscribeContent;
	}
	public String getSubscribeStatus() {
		return subscribeStatus;
	}
	public void setSubscribeStatus(String subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
