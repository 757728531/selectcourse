package com.sys.selectcourse.entity;
//公告



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

//公告栏
@TableName("BulletinBoard")
public class BulletinBoard implements Serializable{
	
	/**
	 * 
	 */
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	@TableField("bulletin_title")
	private String bulletinTitle;//标题
	@TableField("bulletin_content")
	private String bulletinContent;//内容]
	@TableField("bulletin_date")
	private Date bulletinDate;//发布时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBulletinTitle() {
		return bulletinTitle;
	}
	public void setBulletinTitle(String bulletinTitle) {
		this.bulletinTitle = bulletinTitle;
	}
	public String getBulletinContent() {
		return bulletinContent;
	}
	public void setBulletinContent(String bulletinContent) {
		this.bulletinContent = bulletinContent;
	}
	public Date getBulletinDate() {
		return bulletinDate;
	}
	public void setBulletinDate(Date bulletinDate) {
		this.bulletinDate = bulletinDate;
	}
	
	
}
