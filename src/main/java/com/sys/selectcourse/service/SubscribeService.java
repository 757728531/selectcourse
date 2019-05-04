package com.sys.selectcourse.service;

import com.sys.selectcourse.entity.Subscribe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubscribeService {

    //预约流程
    //用户提交预约申请--添加
    public int addSubscribe(Subscribe subscribe);

    //审批人员审批用户申请--修改(状态)
    public int updateSubscribe(Subscribe subscribe);

    //用户删除预约信息--删除
    public int deleteSubscribe(int id);

    //用户预约情况
    public Subscribe userSubscribeList(int id);

    //用户预约列表--查看
    public List<Subscribe> userSubscribeListAll(String userId);

    //审批人员根据状态查看预约
    public List<Subscribe> subscribeListByStatus(String Status);

   //根据id审核预约
    public int updateSubscribe(Integer id,String status);

    //显示所有预约信息（不包括拒绝预约信息）
    public List<Subscribe> subscribeList(String roomName);

    //限制用户预约次数
    public int subscribeConfine(String userId);

    //抢占资源判断
    public int synSubscribe(Subscribe subscribe);

    //获取所有预约信息
    public List<Subscribe> getAllSubscribe();

    //根据获取需要审核的预约
    public List<Subscribe> getAllByAudit(String status);
}
