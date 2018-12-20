package com.my.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.my.dao.BlogCommentDao;
import com.my.entity.BlogComment;
import com.my.entity.PageBean;
import com.my.service.BlogCommentService;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {
	
	@Resource
	private BlogCommentDao blogCommentDao;

	
	public PageBean<BlogComment> listByPage(PageBean<BlogComment> pageBean) {
		 pageBean.getMap().put("start",pageBean.getStart());
	        pageBean.getMap().put("end",pageBean.getEnd());
	        pageBean.setResult(blogCommentDao.listByPage(pageBean.getMap()));
	        pageBean.setTotal(blogCommentDao.getTotal(pageBean.getMap()));
	        return pageBean;
	}

	
	public Long getTotal(Map<String, Object> map) {
		return blogCommentDao.getTotal(map);
	}

	
	public BlogComment getById(Integer id) {
		return blogCommentDao.getById(id);
	}

	
	public Integer saveComment(BlogComment comment) {
		return blogCommentDao.saveBlogComment(comment);
	}

	
	public Integer deleteComment(Integer id) {
		return blogCommentDao.deleteBlogComment(id);
	}

	
	public Integer updateComment(BlogComment comment) {
		return blogCommentDao.updateBlogComment(comment);
	}

	
	public Long deleteCommentByBlogId(Integer blogId) {
		return blogCommentDao.deleteByBlogId(blogId);
	}

	
	public List<BlogComment> getCommentData(Map<String, Object> map) {
		return blogCommentDao.listByPage(map);
	}

}
