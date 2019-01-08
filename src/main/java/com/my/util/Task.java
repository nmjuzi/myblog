package com.my.util;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.my.controller.BloggerController;

@Component
public class Task {
	@Resource
	private BloggerController bloggerController;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private static int times = 0;
	
	@Scheduled(cron = "0 00 03 * * ? ") // 每天3点定时对锁定数据进行删除  cron = "0 18 15 * * ? "  是15:18
	public void everyDay() {
		logger.info("删除锁定用户表------>开始--》共有"+bloggerController.getLockUserMap().size()+"个账户被锁定"+(times++));
		if(bloggerController.getLockUserMap().size()>0){
			bloggerController.getLockUserMap().clear();
		}
	}
	
/*	  @Scheduled(cron = "0/5 * * * * ?")//每隔5秒隔行一次 
	  public void test2()
	  {
		  logger.info("测试logger");
	     System.out.println("测试一哈子");
	  } */
}
