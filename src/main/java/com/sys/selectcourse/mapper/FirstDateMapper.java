package com.sys.selectcourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sys.selectcourse.entity.FirstDate;
import org.apache.ibatis.annotations.Select;

public interface FirstDateMapper extends BaseMapper<FirstDate> {
    @Select("select count(*) from firstdate")
    public  int firstDateNum();
}
