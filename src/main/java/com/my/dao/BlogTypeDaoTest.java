package com.my.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.my.entity.BlogType;

@RunWith(SpringJUnit4ClassRunner.class) //使用spring测试
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class BlogTypeDaoTest {
	 @Resource
	    private BlogTypeDao blogTypeDao;


//	    @Test
//	    public void addBlogType() throws Exception {
//	        BlogType blogType = new BlogType("Mysql",19);
//	        int result = blogTypeDao.addBlogType(blogType);
//	        System.out.println("add:"+result);
//	    }

	    @Test
	    public void deleteBlogType() throws Exception {
	        int result = blogTypeDao.deleteBlogType(19);
	        System.out.println(result);
	    }

//	    @Test
//	    public void updateBlogType() throws Exception {
//	        // 先查询出要更新的记录然后修改
//	        BlogType blogType = blogTypeDao.getById(18);
//	        System.out.println(">>>>>>>>>>>>>>>>>>>"+blogType);
//	        //然后提交更新
//	        blogType.setTypeName("更新mysql");
//	        //更新
//	        blogTypeDao.updateBlogType(blogType);
//	        //查询更新后的值 并且打印
//	        System.out.println("update："+blogTypeDao.getById(18));
//
//	    }

//	    @Test
//	    public void getById() throws Exception {
//	        BlogType blogType = blogTypeDao.getById(18);
//	        System.out.println("get:"+blogType);
//	    }

	    @Test
	    public void  listByPage(){
	        Integer page = 1;  //第一页
	        Integer pageSize = 2;  //一页显示两条
	        Integer start =(page-1)*pageSize;
	        Integer end = page*pageSize;
	        List<BlogType> blogTypeList = blogTypeDao.listByPage(start,end);
	        for (BlogType b: blogTypeList) {
	            System.out.println("lsit:"+b);
	        }
	    }

	    @Test
	    public void getTotal(){
	        long total = blogTypeDao.getTotal();
	        System.out.println("total:"+total);
	    }

}
