//package com.sys.selectcourse.mapper;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.sys.selectcourse.entity.User;
//import com.sys.selectcourse.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
//@ContextConfiguration(locations = {"classpath:spring/spring-mvc.xml"})
//public class StudentMapperTest {
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void test1() {
//        IPage<User> userList = userService.getUserList(1, 2);
//        System.out.println(userList.getTotal());
//    }
//}