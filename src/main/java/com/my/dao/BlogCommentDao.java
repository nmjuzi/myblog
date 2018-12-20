package com.my.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.my.entity.BlogComment;

@Repository
public interface BlogCommentDao {
	//分页查询评论
	public List<BlogComment> listByPage(Map<String,Object> map);
	
	//获取总记录数
	public Long getTotal(Map<String,Object> map);
	
	//根据id查询评论信息
	public BlogComment getById(Integer id);
	
	//添加评论信息
	public Integer saveBlogComment(BlogComment blogComment);
	
	//根据id删除评论
	public Integer deleteBlogComment(Integer id);
	
	//更改评论审核状态
	public Integer updateBlogComment(BlogComment blogComment);
	
	//根据博客id查询所属评论
	public List<BlogComment> queryByBlogId(Integer blogId);
	
	//根据博客id删评论信息
	public Long deleteByBlogId(Integer blogId);
}
