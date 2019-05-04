package com.sys.selectcourse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sys.selectcourse.entity.Subscribe;
import com.sys.selectcourse.mapper.SubscribeMapper;
import com.sys.selectcourse.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private SubscribeMapper subscribeMapper;

    @Override
    public int addSubscribe(Subscribe subscribe) {
        return subscribeMapper.insert(subscribe);
    }

    @Override
    public int updateSubscribe(Subscribe subscribe) {
        return subscribeMapper.updateById(subscribe);
    }

    @Override
    public int deleteSubscribe(int id) {
        return subscribeMapper.deleteById(id);
    }

    @Override
    public Subscribe userSubscribeList(int id) {
        return subscribeMapper.selectById(id);
    }

    @Override
    public List<Subscribe> userSubscribeListAll(String userId) {
        return subscribeMapper.userSubscribeListAll(userId);
    }

    @Override
    public List<Subscribe> subscribeListByStatus(String status) {
        return subscribeMapper.subscribeListByStatus(status);
    }

    @Override
    public int updateSubscribe(Integer id, String status) {
        Subscribe subscribe = subscribeMapper.selectById(id);
        if (subscribe != null) {
            subscribe.setSubscribeStatus(status);
            return subscribeMapper.updateById(subscribe);
        }
        return 0;
    }


    @Override
    public List<Subscribe> subscribeList(String roomName) {
        return subscribeMapper.selectList(new QueryWrapper<Subscribe>().eq("subscribe_room", roomName).in("subscribe_status", Arrays.asList(0, 1)));
    }

    @Override
    public int subscribeConfine(String userId) {
        return 0;
    }

    @Override
    public int synSubscribe(Subscribe subscribe) {
        return 0;
    }

    @Override
    public List<Subscribe> getAllSubscribe() {
        return subscribeMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<Subscribe> getAllByAudit(String status) {
        return subscribeMapper.selectList(new QueryWrapper<Subscribe>().eq("subscribe_status",status));
    }


}
