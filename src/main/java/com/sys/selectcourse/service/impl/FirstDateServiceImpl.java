package com.sys.selectcourse.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sys.selectcourse.entity.FirstDate;
import com.sys.selectcourse.mapper.FirstDateMapper;
import com.sys.selectcourse.service.FirstDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FirstDateServiceImpl implements FirstDateService {
    @Autowired
    private FirstDateMapper firstDateMapper;
    @Override
    public List<FirstDate> getFirstDate() {
        return firstDateMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public int addFirstDate(Date firstDate) {
        FirstDate first=new FirstDate();
        first.setFastDate(firstDate);
        return firstDateMapper.insert(first);
    }

    @Override
    public int firstDateNum() {
        return firstDateMapper.firstDateNum();
    }

    @Override
    public int deleteFirstDate() {
        return firstDateMapper.delete(new QueryWrapper<>());
    }
}
