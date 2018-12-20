package com.my.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.my.dao.BlogTypeDao;
import com.my.entity.BlogType;
import com.my.entity.PageBean;
import com.my.service.BlogTypeService;

import javax.annotation.Resource;

@Service
public class BlogTypeServiceImpl implements BlogTypeService {
	
	
		@Resource
	    private BlogTypeDao blogTypeDao;
	 
	    public PageBean<BlogType> listByPage(PageBean<BlogType> pageBean) {
	        //查询分页结果
		 System.out.println(">>start"+pageBean.getStart()+",>>end"+pageBean.getEnd());
	        pageBean.setResult(blogTypeDao.listByPage(pageBean.getStart(),pageBean.getEnd()));
	        //查询记录总数
	        pageBean.setTotal(blogTypeDao.getTotal());
	        return pageBean;
	    }
	    
	    public Integer addBlogType(BlogType blogType) {
	        return blogTypeDao.addBlogType(blogType);
	    }

	    public Integer updateBlogType(BlogType blogType) {
	        return blogTypeDao.updateBlogType(blogType);
	    }

	    public Integer deleteBlogType(Integer id) {
	        return blogTypeDao.deleteBlogType(id);
	    }
	
	    public Long getTotal() {
	        return blogTypeDao.getTotal();
	    }
	    
	    public List<BlogType> getBlogTypeData(){return  blogTypeDao.getBlogTypeData();}

}
