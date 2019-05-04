package com.sys.selectcourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

//第一周时间
@TableName("FirstDate")
public class FirstDate implements Serializable{
	
	/**
	 * 
	 */
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	@TableField("first_date")
	private Date firstDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFirstDate() {
		return firstDate;
	}
	public void setFastDate(Date firstDate) {
		this.firstDate = firstDate;
	} 
	
}
