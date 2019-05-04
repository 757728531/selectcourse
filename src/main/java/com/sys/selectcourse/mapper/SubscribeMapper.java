package com.sys.selectcourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sys.selectcourse.entity.Subscribe;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SubscribeMapper extends BaseMapper<Subscribe> {
    //用户预约列表--查看
    @Select("select * from Subscribe subscribe,User user where subscribe.user_id=user.user_id  and subscribe.user_id=#{userId}")
    public List<Subscribe> userSubscribeListAll(@Param("userId") String userId);

    //审批人员查看审核的预约
    @Select("SELECT * FROM Subscribe s,User u WHERE  s.user_Id =u.user_id AND s.subscribe_status=#{status}")
    public List<Subscribe> subscribeListByStatus(@Param("status")String status);



}
