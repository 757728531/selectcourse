package com.sys.selectcourse.tools;



import com.sys.selectcourse.entity.Course;
import com.sys.selectcourse.entity.Subscribe;
import com.sys.selectcourse.entity.SubscribeTable;

import java.util.Date;
import java.util.List;

public class SubTableTools {
	
	public static SubscribeTable subTools(List<Course> courseList, List<Subscribe> subscribeList, String week, Date today){
		//整合第一条数据开始
		SubscribeTable subTable=new SubscribeTable();
		String [] node = {null,null,null,null,null,null,null};
		subTable.setTime(today);
		subTable.setWeek(week);//获取星期
		for(Course course:courseList){
			if (subscribeList.size()>0){
				for(Subscribe sub:subscribeList){
					//获取当天的课表信息和预约信息(条件：星期相同，截止日期以内)
					setNode(week,today,course,node);
					if(today.equals(sub.getSubscribeDate())){
						String[] nodes = {"第1,2节","第3,4节","第5节","第6,7节","第8,9节","第10,11节","第12节"};
						for(int i=0;i<nodes.length;i++){
							if(nodes[i].equals(sub.getSubscribeNode())){
								node[i]="已预约";
							}
						}
					}
				}
			}else{
				setNode(week,today,course,node);
			}
		}
		subTable.setNode(node);
		//整合第一条数据完成
		return subTable;
	}
	//提取代码
      public static void setNode( String week, Date today,Course course,String []node){
		  if(week.equals(course.getCourseWeek())&&(today.getTime())<(course.getEndDate().getTime()+7*86400*1000)){
			  String[] nodes = {"第1,2节","第3,4节","第5节","第6,7节","第8,9节","第10,11节","第12节"};
			  for(int i=0;i<nodes.length;i++){
				  if(nodes[i].equals(course.getCourseNode())){
					  node[i]=course.getCourseName()+"<br/>"+course.getCourseTeacher()+"<br/>"+course.getWeekWeek();
				  }
			  }
		  }
	  }
}
