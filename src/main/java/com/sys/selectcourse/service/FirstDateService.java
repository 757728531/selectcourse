package com.sys.selectcourse.service;

import com.sys.selectcourse.entity.FirstDate;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FirstDateService {

    //获取第一周时间
    public List<FirstDate> getFirstDate();
    //添加第一周时间
    public int addFirstDate( Date firstDate);
    //获取时间总数
    public int firstDateNum();
    //删除第一周时间
    public int deleteFirstDate();
}
