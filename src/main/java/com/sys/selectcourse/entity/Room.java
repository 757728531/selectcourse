package com.sys.selectcourse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

//机房
@TableName("Room")
public class Room implements Serializable{

	/**
	 * 
	 */
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	@TableField("room_name")
	private String roomName;//机房名称
	@TableField("room_seat")
	private String roomSeat;//机房座位数
	@TableField("room_status")
	private Integer roomStatus;//机房状态   1 正常使用 2机房已关闭
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomSeat() {
		return roomSeat;
	}
	public void setRoomSeat(String roomSeat) {
		this.roomSeat = roomSeat;
	}
	public Integer getRoomStatus() {
		return roomStatus;
	}
	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}
	
	
}

