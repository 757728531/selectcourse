package com.sys.selectcourse.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sys.selectcourse.entity.User;

import java.util.List;

public interface UserService {
    //多用户查询
    public IPage<User> getUserList(Integer page, Integer pageSize);

    //获取单用户信息
    public User getUser(User user);

    //获取待审核用户
    public List<User> getUserCheckList();

    //登录
    public User loginUser(String userId,String password);

    //新增、注册用户
    public int insertUser(User user);

    //更改用户信息
    public int updateUser(User user);

    //删除用户信息
    public int deleteUser(User user);



    //根据userId password获取用户
    public User getUserByUserIdAndPassword(String userId,String password);

    public int deleteUserById(Integer id);

    public User selectUserById(Integer id);
}
