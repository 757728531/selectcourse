package com.sys.selectcourse.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sys.selectcourse.entity.BulletinBoard;

import com.sys.selectcourse.mapper.BulletinBoardMapper;
import com.sys.selectcourse.service.BulletinBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BulletinBoardServiceImpl implements BulletinBoardService {

    @Autowired
    private BulletinBoardMapper bulletinBoardMapper;

    @Override
    public int addBulletin(BulletinBoard bulletinBoard) {
        return bulletinBoardMapper.insert(bulletinBoard);
    }

    @Override
    public int deleteBulletin(int id) {
        return bulletinBoardMapper.deleteById(id);
    }

    @Override
    public List<BulletinBoard> getBulletinList() {
        return bulletinBoardMapper.selectList(new QueryWrapper<>());
    }
}
