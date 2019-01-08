package com.my.entity;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class BlogComment implements Serializable {
	private Integer id;
	private String userIp;
	private String content;
	private Date commentDate;
	private Integer state;  //审核状态  0待审核 1通过 2未通过
	private Blog blog;
	
	public BlogComment(){
		
	}
	public BlogComment(Integer id, String userIp, String content, Date commentDate, Integer state, Blog blog) {
        this.id = id;
        this.userIp = userIp;
        this.content = content;
        this.commentDate = commentDate;
        this.state = state;
        this.blog = blog;
    }

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	
	
}
