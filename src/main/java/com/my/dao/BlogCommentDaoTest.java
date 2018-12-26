/*package com.my.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.my.entity.BlogComment;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class BlogCommentDaoTest {
	@Resource
	BlogCommentDao blogCommentDao;
	
	@Test
	public void listByPage(){
		Integer page = 1;
		Integer pageSize = 2;
		Integer start = (page-1)*pageSize;
		Integer end = page*pageSize;
		List<BlogComment> list = blogCommentDao.listByPage(start,end);
		for(BlogComment b:list){
			System.out.println(b);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
*/