package com.my.service;

import com.my.entity.Blogger;



public interface BloggerService {
	/**
     * 通过id查询博主信息
     * @param id
     * @return
     */
    Blogger getBloggerById(Integer id);
	 
    /**
     * 通过用户名查询博主信息
     * @param username
     * @return
     */
    Blogger getBloggerByName(String username);

    /**
     * 更新博主信息
     * @param blogger
     * @return
     */
    Integer updateBlogger(Blogger blogger);
}
