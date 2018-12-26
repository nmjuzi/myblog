package com.my.dao;

import org.springframework.stereotype.Repository;

import com.my.entity.Blogger;

/**
 * Created by wyw on 2018/9/3.
 * 博主dao接口
 */
@Repository //注册为持久层的bean
public interface BloggerDao {
	
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
    
    /**
     * 通过id查询博主信息
     * @param username
     * @return
     */
    Blogger getBloggerById(Integer id);
}
