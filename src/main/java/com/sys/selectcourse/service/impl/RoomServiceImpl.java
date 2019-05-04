package com.sys.selectcourse.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sys.selectcourse.entity.Room;
import com.sys.selectcourse.mapper.RoomMapper;
import com.sys.selectcourse.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Room> getRoomList() {
        return roomMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Room getRoom(Room room) {
        return roomMapper.selectById(room);
    }

    @Override
    public int addRoom(Room room) {
        return roomMapper.insert(room);
    }

    @Override
    public int updateRoom(Room room) {
        return roomMapper.updateById(room);
    }

    @Override
    public int deleteRoom(Room room) {
        return roomMapper.deleteById(room);
    }
}
