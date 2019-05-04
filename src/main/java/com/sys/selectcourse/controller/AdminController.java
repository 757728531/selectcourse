package com.sys.selectcourse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sys.selectcourse.entity.*;
import com.sys.selectcourse.service.*;
import com.sys.selectcourse.tools.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


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

    //管理员界面
    @RequestMapping(value = "/index")
    public String adminIndex(HttpSession session) {
        List<User> userCheckList = null;
        userCheckList = userService.getUserCheckList();
        int checkNum = userCheckList.size();
        session.setAttribute("checkNum", checkNum);
        return "admin/index";
    }

    //管理员个人信息
    @RequestMapping(value = "/personal")
    public String personal(HttpSession session) {
        User user = new User();
        user = (User) session.getAttribute("user");
        user.setId(user.getId());
        session.setAttribute("user", userService.getUser(user));
        return "admin/personal";
    }

    //管理员个人信息修改页面
    @RequestMapping(value = "/toUpdatePersonal")
    public String toUpdatePersonal() {

        return "admin/updatePersonal";
    }

    //处理个人信息修改
    @RequestMapping(value = "/updatePersonal", method = RequestMethod.POST)
    public String updatePersonal(@RequestParam String userName, @RequestParam String userTel,
                                 @RequestParam String userId, @RequestParam String userOther, HttpSession session, RedirectAttributes red) {
        String info = "";
        int result = -1;
        User user = new User();
        user = (User) session.getAttribute("user");
        user.setId(user.getId());
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserTel(userTel);
        user.setUserOther(userOther);
        result = userService.updateUser(user);
        if (result > 0) {
            info = "修改成功！";
        } else {
            info = "个人信息修改失败，请检查后修改";
        }
        red.addFlashAttribute(info);
        return "redirect:index.do";
    }

    //密码修改
    @RequestMapping(value = "/toUpdateUserPwd")
    public String updatePwd() {
        return "admin/updatePwd";
    }

    @RequestMapping(value = "updateUserPwd", method = RequestMethod.POST)
    public String updatePwd(@RequestParam String userId, @RequestParam String userPwd, @RequestParam String newsUserPwd, RedirectAttributes red, HttpSession session) {
        String info = "";
        if (StringUtils.isNotBlank(newsUserPwd) && StringUtils.isNotBlank(userPwd)) {
            //判断旧密码
            try {
                User user = userService.getUserByUserIdAndPassword(userId, userPwd);
                //新密码赋值
                if (user != null) {
                    int result = -1;
                    user.setUserPwd(newsUserPwd);
                    result = userService.updateUser(user);
                    if (result > 0) {
                        info = "Y";
                        session.removeAttribute("user");
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


    //用户管理
    @RequestMapping(value = "/userList")
    public String userList(@RequestParam String page, Model model) {
        //每页显示条数
        int pageSize = 5;
        IPage<User> userList = userService.getUserList(Integer.parseInt(page),
                pageSize);
        //总条数
        long pageCount = userList.getTotal();
        //总页数
        long pageNum = userList.getPages();
        Integer IntPage = Integer.parseInt(page.trim());
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("userList", userList.getRecords());
        model.addAttribute("IntPage", IntPage);
        model.addAttribute("listNum", userList.getRecords().size());
        return "admin/userList";
    }

    //单用户信息
    @RequestMapping(value = "/{id}/onlyUser")
    public String onlyUser(@PathVariable Integer id, Model model) {
        User user = new User();
        user.setId(id);
        model.addAttribute("onlyUser", userService.getUser(user));
        return "admin/onlyUser";
    }

    //删除用户
    @RequestMapping(value = "deleteUser")
    public String deleteUsers(@RequestParam Integer id, @RequestParam Integer page, HttpServletRequest request) throws Exception {
        Mail mail = new Mail();
        MailTools mTools = new MailTools();
        if (id != null && page != null) {
            User user = userService.selectUserById(id);
            if (user != null) {
                if ("1".equals(user.getUserStatus()) && user.getUserRole() > 1) {
                    int i = userService.deleteUserById(id);
                    if (i > 0) {
                        mail.setHost("smtp.163.com");//设置邮件服务器
                        mail.setSender("a15365088191@163.com");  //发件人
                        mail.setReceiver(user.getUserId()); // 接收人
                        mail.setUsername("a15365088191@163.com"); // 登录账号
                        mail.setPassword("zxc123123"); // 发件人授权码
                        mail.setSubject("致客户的一封信！");
                        mail.setMessage("<h3>尊敬的用户您好！</h3><br/>"
                                + "  感谢您对南京航空航天大学金城学院计算机机房预约管理系统的支持！\n但由于您违法了计算机机房使用条款或其它原因，我们将关闭您所使用的账号，给您带来的不便请见谅！\n如有任何疑问，可以咨询管理员");
                        mTools.send(mail);
                    }
                }
            }

        }
        return "redirect:/admin/userList.do?page=" + page;
    }

    //用户审核
    @RequestMapping(value = "/toUserCheck")
    public String toUserCheck(Model model) {
        List<User> userCheckList = null;
        userCheckList = userService.getUserCheckList();
        model.addAttribute("userCheckList", userCheckList);
        model.addAttribute("listNum", userCheckList.size());
        return "admin/userCheck";
    }

    //用户审核处理
    @RequestMapping(value = "/{id}/userCheck")
    public String userCheck(@PathVariable Integer id, Model model) {
        User user = userService.selectUserById(id);
        if (user != null) {
            model.addAttribute("onlyUserCheck", user);
            return "admin/onlyUserCheck";
        }
        return "redirect:/admin/toUserCheck.do";
    }

    //通过用户审核
    @RequestMapping(value = "/{id}/updateUserCheck")
    public String passUserCheck(@PathVariable Integer id, HttpSession session) throws Exception {
        User user = userService.selectUserById(id);
        user.setUserStatus("1");
        int result = userService.updateUser(user);
        if (result > 0) {
            /*
             * 1.发送用户审核通过消息给用户
             * 2.用户的邮箱，内容，管理员邮箱，授权码
             * 步骤：通过id获取用户信息，
             */
            Mail mail = new Mail();
            MailTools mTools = new MailTools();
            String host = "";
                if ("1".equals(user.getUserStatus())) {
                mail.setHost("smtp.163.com");//设置邮件服务器
                mail.setSender("a15365088191@163.com");  //发件人
                mail.setReceiver(user.getUserId()); // 接收人
                mail.setUsername("a15365088191@163.com"); // 登录账号
                mail.setPassword("zxc123123"); // 发件人授权码
                mail.setSubject("审核结果通知");
                mail.setMessage("<h3>尊敬的用户您好！\n"
                        + "  欢迎使用南京航空航天大学金城学院计算机机房预约管理系统\n您的账号申请审核已通过，初始密码为："
                        + user.getUserPwd() + "\n温馨提示：为了您的账号安全，请尽快修改密码！请规范使用本系统，不然可是会被管理员关小黑屋的哦！</h3>");
                mTools.send(mail);
            }
            int checkNum = (Integer) session.getAttribute("checkNum");
            if (checkNum > 0) {
                session.setAttribute("checkNum", checkNum - 1);
            }
            return "redirect:/admin/toUserCheck.do";
        } else {
            return "admin/onlyUserCheck";
        }

    }

    //审核未通过
    @RequestMapping(value = "/{id}/deleteUserCheck")
    public String deleteUser(@PathVariable Integer id, HttpServletRequest request, HttpSession session) throws Exception {
        User user = userService.selectUserById(id);
        if ("2".equals(user.getUserStatus()) && user.getUserRole() > 1) {
            userService.deleteUser(user);
        }
        return "redirect:/admin/toUserCheck.do";
    }

    //机房管理
    //机房查看
    @RequestMapping(value = "seeRoom")
    public String SeeRoom(Model model) {
        List<Room> list = roomService.getRoomList();
        model.addAttribute("roomList", list);
        return "admin/room";
    }

    //机房激活与禁用
    @RequestMapping("roomStatus")
    public String updateRoomStatus(@RequestParam String id, @RequestParam String status, RedirectAttributes red) {
        Room room = new Room();
        room.setId(Integer.parseInt(id.trim()));
        room.setRoomStatus(Integer.parseInt(status.trim()));
        int result = -1;
        String info = "";
        try {
            result = roomService.updateRoom(room);
            if (result > 0) {
                info = "激活成功！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = "激活失败";
        }
        red.addAttribute(info);
        return "redirect:/admin/seeRoom.do";
    }

    //机房添加
    @RequestMapping(value = "toAddRoom")
    public String toRoom() {
        return "admin/addRoom";
    }

    @RequestMapping(value = "addRoom")
    public String toRoom(@RequestParam String roomName,
                         @RequestParam String roomSeat, @RequestParam String roomStatus) {
        int result = -1;
        Room room = new Room();
        room.setRoomName(roomName);
        room.setRoomSeat(roomSeat);
        room.setRoomStatus(Integer.parseInt(roomStatus));
        result = roomService.addRoom(room);
        if (result > 0) {

        }
        return "redirect:/admin/seeRoom.do";
    }

    //机房更改
    @RequestMapping(value = "/toUpdateRoom")
    public String toUpdateRoom(String id, Model model) {
        Room room = new Room();
        room.setId(Integer.parseInt(id));
        model.addAttribute("room", roomService.getRoom(room));
        return "admin/updateRoom";
    }

    @RequestMapping(value = "updateRoom ")
    public String updateRoom(@RequestParam String id, @RequestParam String roomSeat, @RequestParam String roomStatus) {
        int result = -1;
        Room room = new Room();
        room.setId(Integer.parseInt(id));
        room.setRoomSeat(roomSeat);
        room.setRoomStatus(Integer.parseInt(roomStatus));
        result = roomService.updateRoom(room);
        if (result > 0) {
            return "redirect:/admin/seeRoom";
        }
        return "admin/updateRoom";
    }

    //机房删除
    @RequestMapping(value = "deleteRoom")
    public String deleteRoom(@RequestParam String id) {
        Room room = new Room();
        room.setId(Integer.parseInt(id));
        int result = roomService.deleteRoom(room);
        if (result > 0) {
            return "redirect:/admin/seeRoom ";
        }
        return "admin/room";
    }

    //课表管理
    //课表查看
    //先查机房再查课表
    @RequestMapping(value = "toCourseRoom")
    public String toCourseRoom(Model model) {
        model.addAttribute("roomNames", roomService.getRoomList());
        return "admin/courseRoom";
    }

    //获取机房名称--得到课程列表
    @RequestMapping(value = "toCourse")
    public String toCourse(@RequestParam String name, Model model) {
        List<Course> courseList = null;
        model.addAttribute("roomName", name);
        courseList = courseService.getCourseList(name);
        String[] courseNode = {"第1,2节", "第3,4节", "第5节", "第6,7节", "第8,9节", "第10,11节", "第12节"};
        for (int i = 0; i < courseNode.length; i++) {
            List<Course> courseNodeList = JudgeCourse.judge(courseList, courseNode[i]);
            model.addAttribute("courseNodeList" + i, courseNodeList);
        }
        return "admin/course";
    }

    //课表添加--自动
    @RequestMapping("toAddCourse")
    public String addCourse() {
        return "admin/addCourse";
    }

    //上传课表
    @RequestMapping(value = "addCourse", method = RequestMethod.POST)
    public String addCourse(@RequestParam String firstTime, @RequestParam MultipartFile attach, HttpServletRequest request, RedirectAttributes red) {
        //上传文件路径
        String path = "hello.xls";
        //判断上传内容
        if (firstTime != "" && attach != null) {
            try {
                //指定上传位置
                String uploadFilePath = request.getSession().getServletContext().getRealPath("/static/upload");
                String newsFile = "";
                //文件非空判断
                if (!attach.isEmpty()) {
                    //新上传文件名
                    newsFile = Rename.newsName(attach.getOriginalFilename());
                    File saveFile = new File(uploadFilePath + File.separator + newsFile);
                    //文件不存在则自动创建
                    FileUtils.copyInputStreamToFile(attach.getInputStream(), saveFile);
                    //文件路径
                    path = uploadFilePath + File.separator + newsFile;
                    /*
                     * 获取上传文件路径
                     * 对文件进行解析
                     */
                    Date firstDate = new SimpleDateFormat("yyyy-MM-dd").parse(firstTime);
                    //第一周开始时间存入数据库
                    if (firstDateService.firstDateNum() == 0) {
                        firstDateService.addFirstDate(firstDate);
                    } else {
                        firstDateService.deleteFirstDate();
                        firstDateService.addFirstDate(firstDate);
                    }
                    //调用工具类解析Excel表格
                    List<Course> list = ExcelAnalyTools.excel(path, firstDate);
                    for (Course course : list) {
                        courseService.addCourse(course);
                    }
                    red.addFlashAttribute("upExcelInfo", "Y");
                    return "redirect:/admin/toAddCourse";
                } else {
                    red.addFlashAttribute("upExcelInfo", "N");
                    return "redirect:/admin/toAddCourse";
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("uploadMsg", "fileError");
                return "";

            } finally {
                //卸磨杀驴省内存^_^
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
            }
        } else {
            return "";
        }


    }

    //手动添加课表
    @RequestMapping("toCourseRoomAdd")
    public String toCourseRoomAdd(Model model) {
        //遍历机房
        model.addAttribute("roomNames", roomService.getRoomList());
        return "admin/courseRoomAdd";
    }

    //课表填充
    @RequestMapping(value = "toCourseAdd")
    public String toCourseAdd(@RequestParam String name, Model model) {
        List<Course> courseList = null;
        model.addAttribute("roomName", name);
        courseList = courseService.getCourseList(name);
        String[] courseNode = {"第1,2节", "第3,4节", "第5节", "第6,7节", "第8,9节", "第10,11节", "第12节"};
        for (int i = 0; i < courseNode.length; i++) {
            List<Course> courseNodeList = JudgeCourse.judge(courseList, courseNode[i]);
            model.addAttribute("courseNodeList" + i, courseNodeList);
        }
        return "admin/toCourseAdd";
    }

    //课程添加（手动课程添加）
    @RequestMapping("toHandAddCourse")
    public String toHandAddCourse(@RequestParam String roomName, @RequestParam String week, @RequestParam String node, Model model) {
        model.addAttribute("roomName", roomName);
        model.addAttribute("week", week);
        model.addAttribute("node", node);
        return "admin/handAddCourse";
    }

    @RequestMapping(value = "handAddCourse", method = RequestMethod.POST)
    public String handAddCourse(@RequestParam String firstTime, @RequestParam String courseName, @RequestParam String courseTeacher,
                                @RequestParam String start, @RequestParam String end, @RequestParam String week, @RequestParam String node, @RequestParam String roomName) {
        try {
            Course course = new Course();
            course.setCourseName(courseName);
            course.setCourseNode(node);
            course.setCourseTeacher(courseTeacher);
            course.setCourseWeek(week);
            course.setWeekWeek("{第" + start + "-" + end + "周}");
            course.setRoomName(roomName);
            Date firstDate = new SimpleDateFormat("yyyy-MM-dd").parse(firstTime);
            Date first = WeekDateTools.weekDateTools(Integer.parseInt(start), firstDate);
            Date last = WeekDateTools.weekDateTools(Integer.parseInt(end), firstDate);
            course.setStartDate(first);//开始时间
            course.setEndDate(last);//结束时间
            int result = -1;
            result = courseService.addCourse(course);
            if (result > 0) {
                return "redirect:/admin/toCourseRoomAdd.do";
            }
            return "";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    //单个课表删除
    @RequestMapping("toDeleteCourseRoom")
    public String deleteCourseRoom(Model model) {
        model.addAttribute("roomNames", roomService.getRoomList());
        return "admin/toDeleteCourseRoom";
    }

    @RequestMapping("toDeleteCourse")
    public String deleteCourse(@RequestParam String name, Model model) {
        List<Course> courseList = null;
        model.addAttribute("roomName", name);
        courseList = courseService.getCourseList(name);
        String[] courseNode = {"第1,2节", "第3,4节", "第5节", "第6,7节", "第8,9节", "第10,11节", "第12节"};
        for (int i = 0; i < courseNode.length; i++) {
            List<Course> courseNodeList = JudgeCourse.judge(courseList, courseNode[i]);
            model.addAttribute("courseNodeList" + i, courseNodeList);
        }
        return "admin/deleteCourseById";
    }

    @RequestMapping("deleteCourseById")
    public String deleteCourse(@RequestParam Integer id) {
        if (id!=null){
            Course course = courseService.getCourseById(id);
            if (course!=null){
                int result = courseService.deleteCourse(course);
                if (result>0){
                    return "redirect:/admin/toDeleteCourseRoom.do?name="+course.getRoomName();
                }
            }
        }
        return "redirect:/admin/toDeleteCourseRoom.do";
    }

    //格式化课表
    @RequestMapping("deleteAllCourse")
    public String deleteAllCourse(RedirectAttributes attributes) {
        String deleteInfo = "";
        try {
          int  i = courseService.deleteAllCourse();
                deleteInfo = "Y";
        } catch (Exception e) {
            e.printStackTrace();
            deleteInfo = "N";
        }
        attributes.addFlashAttribute("deleteInfo", deleteInfo);
        return "redirect:/admin/index.do";
    }

    //公告管理--查看
    @RequestMapping("toBulletin")
    public String toBulletin(Model model) {
        model.addAttribute("bulletinList", bBoardService.getBulletinList());
        model.addAttribute("listNum", bBoardService.getBulletinList().size());
        return "admin/bulletin";
    }

    //公告--添加
    @RequestMapping("toAddBulletin")
    public String toAddBulletin() {
        return "/admin/addBulletin";
    }

    @RequestMapping(value = "addBulletin", method = RequestMethod.POST)
    public String addBulletin(@RequestParam String bulletinTitle, @RequestParam String bulletinContent) {
        BulletinBoard board = new BulletinBoard();
        board.setBulletinTitle(bulletinTitle);
        board.setBulletinContent(bulletinContent);
        board.setBulletinDate(new Date());
        int result = -1;
        result = bBoardService.addBulletin(board);
        if (result > 0) {
            return "redirect:/admin/toBulletin.do";
        }
        return "";
    }

    //公告--删除
    @RequestMapping("deleteBulletin")
    public String deleteBulletin(@RequestParam String id) {
        int result = -1;
        result = bBoardService.deleteBulletin(Integer.parseInt(id));
        if (result > 0) {
            return "redirect:/admin/toBulletin.do";
        }
        return "";
    }

    //预约管理

    //查看已预约用户
    @RequestMapping("toSubscribe")
    public String toSubscribe(Model model) {
        List<Subscribe> subscribeList = subscribeService.getAllSubscribe();
        model.addAttribute("now", new Date());
        model.addAttribute("subscribeList", subscribeList);
        model.addAttribute("listNum", subscribeList.size());
        return "admin/subscribeList";
    }

    //清除已过期用户
    @RequestMapping("deleteSubscribeYes")
    public String deleteSubscribeYes(@RequestParam String id, Model model) {
        int result = -1;
        try {
            result = subscribeService.deleteSubscribe(Integer.parseInt(id.trim()));
            if (result > 0) {
                return "redirect:toSubscribe.do";
            } else {
                model.addAttribute("info", "清除失败！");
                return "admin/subscribeList";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("info", "清除失败！");
            return "admin/subscribeList";
        }

    }

    //查看预约申请书
    @RequestMapping("subScribeContent")
    public String subScribeContent(@RequestParam String id, Model model) {
        Subscribe subscribe = subscribeService.userSubscribeList(Integer.parseInt(id));
        model.addAttribute("content", subscribe.getSubscribeContent());
        return "admin/subScribeContent";
    }

    //预约审核
    @RequestMapping("toSubscribeCheck")
    public String toSubscribeCheck(Model model) {
        List<Subscribe> subscribeList = subscribeService.getAllByAudit("0");
        model.addAttribute("subCheckList", subscribeList);
        model.addAttribute("listNum", subscribeList.size());
        return "admin/toSubscribeCheck";
    }

    //机房预约审核通过
    @RequestMapping("updateSubscribe")
    public String updateSubscribe(@RequestParam Integer id) throws Exception {
        subscribeService.updateSubscribe(id, "1");
        return "redirect:/admin/toSubscribeCheck.do";

    }

    //未通过机房审核
    @RequestMapping("updateSubscribeNo")
    public String updateSubscribeNo(@RequestParam Integer id) throws Exception {
        subscribeService.updateSubscribe(id, "2");
        return "redirect:/admin/toSubscribeCheck.do";
    }

    @RequestMapping("exitUser")
    public String exitUser(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:../login.do";
    }
}
