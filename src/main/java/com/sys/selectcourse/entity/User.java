package com.sys.selectcourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

//用户
@TableName("User")
public class User implements Serializable{
	/**
	 * 
	 */
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableId(value = "id",type=IdType.AUTO)
	@TableField("id")
	private Integer id;//ID
	@TableField("user_id")
	private String userId;//登录id
	@TableField("user_pwd")
	private String userPwd;//密码
	@TableField("user_name")
	private String userName;//姓名
	@TableField("user_tel")
	private String userTel;//联系电话
	@TableField("user_img")
	private String userImg;//个人资料
	@TableField("user_other")
	private String userOther;//授权码or用途
	@TableField("user_status")
	private String userStatus;//状态  1 通过  2待审核  审核被拒绝直接删除
	@TableField("user_role")
	private Integer userRole;//角色  2是校外人员 3是教师 4是学生  0是管理员

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

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserOther() {
		return userOther;
	}

	public void setUserOther(String userOther) {
		this.userOther = userOther;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userPwd='" + userPwd + '\'' +
				", userName='" + userName + '\'' +
				", userTel='" + userTel + '\'' +
				", userImg='" + userImg + '\'' +
				", userOther='" + userOther + '\'' +
				", userStatus='" + userStatus + '\'' +
				", userRole=" + userRole +
				'}';
	}
}
