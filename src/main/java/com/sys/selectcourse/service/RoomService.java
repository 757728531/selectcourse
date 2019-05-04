package com.sys.selectcourse.service;
import com.sys.selectcourse.entity.Room;
import java.util.List;

public interface RoomService {
    //获取机房整体信息
    public List<Room> getRoomList();
    //获取单个机房信息
    public Room getRoom(Room room);
    //添加机房信息
    public int addRoom(Room room);
    //更新机房信息
    public int updateRoom(Room room);
    //删除机房信息
    public int deleteRoom(Room room);
}
