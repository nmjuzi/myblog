package com.my.service;


import java.util.List;

import com.my.entity.BlogType;
import com.my.entity.PageBean;


public interface BlogTypeService {
	//分页查询
    PageBean<BlogType> listByPage(PageBean<BlogType> pageBean);
    
 // 添加博客类别
    public Integer addBlogType(BlogType blogType);

    // 更新博客类别
    public Integer updateBlogType(BlogType blogType);

    // 删除博客类别
    public Integer deleteBlogType(Integer id);
    
    public Long getTotal();
    
    public List<BlogType> getBlogTypeData();

}
