package com.my.controller.admin;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.my.entity.AjaxResult;
import com.my.entity.Blog;
import com.my.entity.Blogger;
import com.my.entity.PageBean;
import com.my.lucene.BlogIndex;
import com.my.redis.RedisUtil;
import com.my.service.BlogCommentService;
import com.my.service.BlogService;
import com.my.util.ResponseUtil;

@Controller
@RequestMapping("/admin/blog")
public class BlogController {
	 @Resource
	 private BlogService blogService;
	 
	 @Resource
	 private BlogCommentService blogcommentService;
	 
	 @Resource
	 private BlogIndex blogIndex;
	 
	 @Resource
	 private RedisUtil redisUtil;
	 
	 //后台分页查询博客信息
	    @RequestMapping("/listBlog")
	    public String listBlog(
	            @RequestParam(value = "page", required = false) String page,
	            @RequestParam(value = "rows", required = false) String rows,
	            Blog s_blog,
	            HttpServletResponse response,HttpServletRequest request) throws Exception {

	        PageBean<Blog> pageBean = new PageBean<Blog>(Integer.parseInt(page), Integer.parseInt(rows));
	        Blogger currentUser = (Blogger)request.getSession().getAttribute("currentUser");
	        pageBean = blogService.listBlog(s_blog.getTitle(),currentUser.getId(),pageBean);
	        
	        //创建json对象
	        JSONObject result = new JSONObject();
	        //设置json序列化日期格式
	        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
	        //禁止对象循环引用
	        //使用默认日期格式化
	        String jsonStr = JSONObject.toJSONString(pageBean.getResult(),
	                SerializerFeature.DisableCircularReferenceDetect,
	                SerializerFeature.WriteDateUseDateFormat);
	        //得到json数组
	        JSONArray array = JSON.parseArray(jsonStr);
	        //把结果放入json
	        result.put("rows", array);
	        result.put("total", pageBean.getTotal());
	        //返回
	        ResponseUtil.write(response, result);
	        return null;
	    }
	    
	  //更新或者新增博客
	    @RequestMapping(value = "/save")
	    @ResponseBody
	    public AjaxResult saveBlog(Blog blog,HttpServletResponse response,HttpServletRequest request) throws Exception {
	        int resultTotal = 0;
	        Blogger b = new Blogger();
	        AjaxResult result= new AjaxResult();
	        Blogger currentUser = (Blogger)request.getSession().getAttribute("currentUser");
	        if(currentUser==null){
	        	result.setSuccess(false);
	        	result.setMsg("登录信息失效，请重新登录后重试！");
	        	return result;
	        }
	        b.setId(currentUser.getId());
	        blog.setBlogger(b);
	       
	        if(blog.getId()!=null){
	            //更新操作
	            resultTotal = blogService.updateBlog(blog);
	            //更新索引
	            blogIndex.updateIndex(blog);
	        }else{
	            //新增操作
	            resultTotal = blogService.saveBlog(blog);
	            //添加索引
	           blogIndex.addIndex(blog);
	        }
	        if(resultTotal > 0) {
	        	result.setSuccess(true);
	        	result.setMsg("博客发布成功！");
	        } else {
	        	result.setSuccess(false);
	        	result.setMsg("博客发布失败！");
	        }
	        return result;
	    }

	    //删除博客
	    @RequestMapping(value = "delete")
	    public String deleteBlog(@RequestParam("ids")String ids,HttpServletResponse response) throws Exception {
	        String[] idsStr = ids.split(",");
	        for(int i = 0; i < idsStr.length; i++) {
	            int id = Integer.parseInt(idsStr[i]);
	            //删除博客时，所属的评论也应该删除
	            blogcommentService.deleteCommentByBlogId(id);
	            blogService.deleteBlog(id);
	        }
	        JSONObject result = new JSONObject();
	        result.put("success", true);
	        ResponseUtil.write(response, result);
	        return null;
	    }

	    //通过id获取博客
	    @RequestMapping(value = "get")
	    public String getById(@RequestParam("id") String id,HttpServletResponse response) throws Exception {

	        Blog blog = blogService.getById(Integer.parseInt(id));
	        String jsonStr = JSONObject.toJSONString(blog);
	        JSONObject result = JSONObject.parseObject(jsonStr);
	        ResponseUtil.write(response, result);
	        return null;
	    }
	   
}
