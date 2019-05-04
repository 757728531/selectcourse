package com.sys.selectcourse.service;
import com.sys.selectcourse.entity.BulletinBoard;
import java.util.List;
public interface BulletinBoardService {
    //添加公告
    public int addBulletin(BulletinBoard bulletinBoard);

    //删除公告
    public int deleteBulletin(int id);

    //查看公告
    public List<BulletinBoard> getBulletinList();
}
