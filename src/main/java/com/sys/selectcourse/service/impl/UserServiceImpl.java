package com.sys.selectcourse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.selectcourse.entity.User;
import com.sys.selectcourse.mapper.UserMapper;
import com.sys.selectcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<User> getUserList(Integer page, Integer pageSize) {
       Page<User> userPage=new Page<>(page,pageSize );
        return userMapper.selectPage(userPage,new QueryWrapper<User>());
    }

    @Override
    public User getUser(User user) {
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().eq("user_id", user.getUserId()));
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public List<User> getUserCheckList() {
        return userMapper.selectList(new QueryWrapper<User>().eq("user_status",2 ));
    }

    @Override
    public User loginUser(String userId, String password) {
        Map param = new HashMap<>();
        param.put("user_id", userId);
        param.put("user_pwd", password);
        List<User> list = userMapper.selectByMap(param);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int deleteUser(User user) {
        return userMapper.deleteById(user);
    }

    @Override
    public User getUserByUserIdAndPassword(String UserID, String password) {
        return loginUser(UserID, password);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectById(id);
    }


}
