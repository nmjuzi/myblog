package com.my.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import com.my.dao.BloggerDao;
import com.my.entity.Blogger;
import com.my.service.BloggerService;
@Service
public class BloggerServiceImpl implements BloggerService {

	 @Resource
	 private BloggerDao bloggerDao;
	
	 public Blogger getBloggerData() {
		 return bloggerDao.getBloggerData();
	}

	
	public Blogger getBloggerByName(String username) {
		return bloggerDao.getBloggerByName(username);
	}

	
	public Integer updateBlogger(Blogger blogger) {
		 return bloggerDao.updateBlogger(blogger);
	}

}
