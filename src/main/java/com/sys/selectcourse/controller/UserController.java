package com.sys.selectcourse.controller;

import com.alibaba.fastjson.JSONArray;
import com.sys.selectcourse.entity.*;
import com.sys.selectcourse.service.*;
import com.sys.selectcourse.tools.MailTools;
import com.sys.selectcourse.tools.NodeTimeTools;
import com.sys.selectcourse.tools.SubTableTools;
import com.sys.selectcourse.tools.WeekDateTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RoomService roomService;
    @Resource
    private CourseService courseService;
    @Resource
    private BulletinBoardService bBoardService;
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private FirstDateService firstDateService;

    @RequestMapping("/exitUser")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:index.do";
    }

    //用户登录界面
    @RequestMapping(value = "/index")
    public String userIndex(Model model, HttpSession session) {
        List<Room> roomList = roomService.getRoomList();
//		Room room = roomList.get(0);
//		//获取第一个机房，跳转预约界面
//		String roomName = room.getRoomName();
//		model.addAttribute("room", roomName);
        session.setAttribute("roomList", roomList);
//		Date firstDate=null;//第一周开始时间
//		try{
//		Course course = courseService.getOnlyCourseList();
//		firstDate= course.getStartDate();
//
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		int weekNums= WeekDateTools.dateWeek(firstDate);
//		session.setAttribute("weekNums", weekNums);
//		session.setAttribute("firstDate", firstDate);
        return "redirect:userPersonal.do";
    }

    //用户个人信息
    @RequestMapping(value = "/userPersonal")
    public String toUserPersonal() {
        return "user/personal";
    }

    //个人信息修改页面
    @RequestMapping(value = "/toUpdatePersonal")
    public String toUpdatePersonal() {
        return "user/updatePersonal";
    }

    //处理个人信息修改
    @RequestMapping(value = "/updatePersonal", method = RequestMethod.POST)
    public String updatePersonal(@RequestParam String id, @RequestParam String userTel,
                                 @RequestParam String userId, @RequestParam String userOther, HttpSession session, RedirectAttributes red) {
        String info = "";
        int result = -1;
        User users = new User();
        users.setId(Integer.parseInt(id));
        users.setUserId(userId);
        users.setUserTel(userTel);
        users.setUserOther(userOther);
        result = userService.updateUser(users);
        if (result > 0) {
            session.setAttribute("user", users);
            return "redirect:userPersonal.do";
        } else {
            info = "个人信息修改失败，请检查后修改";
            red.addFlashAttribute(info);
            return "user/updatePersonal";
        }
    }

    //修改密码
    /*账号、密码、新密码、重复密码、验证码。。。*/
    @RequestMapping(value = "/toUpdateUserPwd")
    public String updatePwd() {
        return "user/updatePwd";
    }

    @RequestMapping(value = "/updateUserPwd", method = RequestMethod.POST)
    public String updatePwd(@RequestParam Integer id, @RequestParam String userPwd, @RequestParam String newsUserPwd, RedirectAttributes red) {
        String info = "";
        if (StringUtils.isNotBlank(userPwd) && StringUtils.isNotBlank(newsUserPwd)) {
            //判断旧密码
            User user = new User();
            user.setId(id);
            user.setUserPwd(userPwd);
            try {
                user = userService.getUser(user);
                //新密码赋值
                if (user != null) {
                    int result = -1;
                    user.setUserPwd(newsUserPwd);
                    result = userService.updateUser(user);
                    if (result > 0) {

                        info = "Y";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                info = "Error";
            }
        } else {
            info = "null";
        }
        red.addFlashAttribute(info);
        return "redirect:toUpdateUserPwd.do";

    }

    //预约表
    @RequestMapping(value = "/toSubscribeTable")
    public String subscribeTable(@RequestParam String room, Model model, HttpSession session) {


        /**
         * 思路：
         *获取课程信息表 获取已预约信息表 初始化可预约信息
         *获取当前时间，推算当前周次及当前周星期一时间，计算出一周信息
         *1.显示课程 2.显示预约信息 3.显示可预约信息
         */
        //获取本周星期一的时间和当前周次
        List<FirstDate> firstDates = firstDateService.getFirstDate();
        Date firstDate = firstDates.get(0).getFirstDate();
        int weekNum = WeekDateTools.dateWeek(firstDate);//当前周次
        Date monDate = WeekDateTools.weekDateTools(weekNum, firstDate);//当前周一时间
        //机房名称
        model.addAttribute("roomName", room);
        List<Course> courseList = courseService.getCourseList(room);
        List<Subscribe> subscribeList = subscribeService.subscribeList(room);
        List<SubscribeTable> subTableList = new ArrayList<SubscribeTable>();
        String[] weeks = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"};
        Calendar cal = Calendar.getInstance();
        //本周的数据
        for (int i = 0; i < weeks.length; i++) {
            SubscribeTable subTable = SubTableTools.subTools(courseList, subscribeList, weeks[i], monDate);
            cal.setTime(monDate);
            cal.add(cal.DATE, 1);
            monDate = cal.getTime();
            subTableList.add(subTable);
        }
        //下周数据
        for (int i = 0; i < weeks.length; i++) {
            SubscribeTable subTable = SubTableTools.subTools(courseList, subscribeList, weeks[i], monDate);
            cal.setTime(monDate);
            cal.add(cal.DATE, 1);
            monDate = cal.getTime();
            subTableList.add(subTable);
        }

        model.addAttribute("subTableList", subTableList);
        //当前时间
        model.addAttribute("today", new Date());
        //限制预约次数
        User user = (User) session.getAttribute("user");
        int subNum = -1;
        subNum = subscribeService.subscribeConfine(user.getUserId());
        model.addAttribute("subNum", subNum);
        //循环节次
        List<Integer> listIndex = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            listIndex.add(i);
        }
        model.addAttribute("i", listIndex);
        session.setAttribute("weekNums", weekNum);
        return "user/subscribeTable";
    }

    //用户预约
    @RequestMapping(value = "/toSubscribeAsk")
    public String toSubscribeAsk(@RequestParam String node, @RequestParam String week,
                                 @RequestParam long today, @RequestParam String room, HttpSession session, Model model) {
        String[] subscribeNode = {"第1,2节", "第3,4节", "第5节", "第6,7节", "第8,9节", "第10,11节", "第12节"};
        //获取预约节次
        String subNode = subscribeNode[Integer.parseInt(node)];
        Subscribe subscribe = new Subscribe();
        //预约时间
        subscribe.setSubscribeDate(new Date(today));
        //预约节次
        subscribe.setSubscribeNode(subNode);
        //预约机房
        subscribe.setSubscribeRoom(room);
        //预约星期
        subscribe.setSubscribeWeek(week);
        User user = (User) session.getAttribute("user");
        subscribe.setUserId(user.getUserId());
        subscribe.setSubscribeTime(user.getUserName());
        model.addAttribute("subscribe", subscribe);
        return "user/subscribeAsk";
    }

    //用户提交申请
    @RequestMapping(value = "/subscribeAsk", method = RequestMethod.POST)
    @ResponseBody
    public Object subscribeAsk(@RequestParam String userId, @RequestParam String subscribeRoom,
                               @RequestParam String subscribeWeek, @RequestParam long subscribeDate, @RequestParam String subscribeNode, @RequestParam String subscribeContent,HttpSession session) throws Exception {
        String info = "";
        User user = (User) session.getAttribute("user");
        Map<String, String> map = new HashMap<String, String>();
        Subscribe subscribe = new Subscribe();
        subscribe.setUserId(userId);//邮箱
        subscribe.setUserName(user.getUserName());//预约人的姓名
        subscribe.setSubscribeRoom(subscribeRoom);//机房
        subscribe.setSubscribeDate(new Date(subscribeDate));//日期
        subscribe.setSubscribeNode(subscribeNode);//节次
        subscribe.setSubscribeWeek(subscribeWeek);//星期
        subscribe.setSubscribeContent(subscribeContent);//申请内容
        subscribe.setSubscribeStatus("0");
        String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date(subscribeDate));
        String time = NodeTimeTools.nodeTime(subscribeNode);
        int result = -1;
        result = subscribeService.addSubscribe(subscribe);
        if (result > 0) {
            Mail mail = new Mail();
            MailTools mTools = new MailTools();
            String host="smtp.163.com";
            mail.setHost(host);//设置邮件服务器
            mail.setSender("a15365088191@163.com");  //发件人
            mail.setReceiver("757728531@qq.com"); // 接收人
            mail.setUsername("a15365088191@163.com"); // 登录账号
            mail.setPassword("zxc123123"); // 发件人授权码
            mail.setSubject("新审核提醒");
            mail.setMessage("<h3>尊敬的审核人员您好！</h3><br/>"
                    + "  有用户提交了机房预约申请，申请机房为" + subscribeRoom + "，时间为" + date + " " + time + "<br/>"
                    + "   请您及时处理！");
            mTools.send(mail);
            info = "Y";
        } else if (result == -2) {
            info = "S";
        } else {
            info = "N";
        }
        map.put("askInfo", info);
        return JSONArray.toJSONString(map);
    }

    //我的预约
    @RequestMapping("mySubscribe")
    public String mySubscribe(Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            List<Subscribe> userSubListAll = null;
            userSubListAll = subscribeService.userSubscribeListAll(user.getUserId());
            int sublist = -1;
            sublist = userSubListAll.size();
            model.addAttribute("userSubListAll", userSubListAll);
            model.addAttribute("sublistNum", sublist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/mySubscribe";
    }

    //撤销删除预约
    @RequestMapping("deleteSubAsk")
    public String deleteSubAsk(@RequestParam String id) {
        int result = -1;
        result = subscribeService.deleteSubscribe(Integer.parseInt(id));
        if (result > 0) {
            return "redirect:/user/mySubscribe.do";
        }
        return "";
    }

    //预约申请书查看
    @RequestMapping("lookAskBook")
    public String lookAskBook(@RequestParam String id, Model model) {
        Subscribe subscribe = subscribeService.userSubscribeList(Integer.parseInt(id));
        subscribe.setSubscribeTime(NodeTimeTools.nodeTime(subscribe.getSubscribeNode()));
        model.addAttribute("subBook", subscribe);
        return "user/lookAskBook";
    }

    //我的位置
    //调用腾讯地图接口
    @RequestMapping("homeMap")
    public String homeMap() {
        return "user/homeMap";
    }

    //公告查看
    @RequestMapping("bulletin")
    public String bulletin(Model model) {
        List<BulletinBoard> bulletinList = bBoardService.getBulletinList();
        model.addAttribute("bulletinList", bulletinList);
        return "user/bulletin";
    }

    //机房使用条例
    @RequestMapping("aboutRules")
    public String aboutRules() {

        return "aboutRules";
    }
}
