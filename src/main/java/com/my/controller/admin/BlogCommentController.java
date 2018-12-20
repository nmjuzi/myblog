package com.my.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.my.entity.BlogComment;
import com.my.entity.PageBean;
import com.my.service.BlogCommentService;
import com.my.util.ResponseUtil;

@Controller
@RequestMapping(value = "admin/comment")
public class BlogCommentController {

	@Resource
	private BlogCommentService blogCommentService;
	
	//评论分页显示
	@RequestMapping(value="/list")
	public String listByPage( @RequestParam(value = "page",required = false) String page,
            @RequestParam(value = "rows" , required = false) String rows,
            @RequestParam(value = "state",required = false) String state,
            			BlogComment blogComment,
                             HttpServletResponse response) throws Exception{
		
		 PageBean<BlogComment> pageBean = new PageBean<BlogComment>(Integer.parseInt(page),Integer.parseInt(rows));
	        pageBean.getMap().put("state",state);
	        pageBean = blogCommentService.listByPage(pageBean);
	        JSONObject result = new JSONObject();
	        JSON.DEFFAULT_DATE_FORMAT="yyyy-MM-dd";
	        String jsonStr = JSONObject.toJSONString(pageBean.getResult(),
	            SerializerFeature.WriteDateUseDateFormat,
	            SerializerFeature.DisableCircularReferenceDetect);
	        JSONArray jsonArray = JSON.parseArray(jsonStr);
	        result.put("total",pageBean.getTotal());
	        result.put("rows",jsonArray);
	        ResponseUtil.write(response,result);
	        return null;
	}
	 //删除评论
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String deleteComment(@RequestParam(value = "ids",required = false)  String ids,HttpServletResponse response) throws Exception {
        String[] idStr = ids.split(",");
        for (int i = 0; i < idStr.length; i++) {
        	blogCommentService.deleteComment(Integer.parseInt(idStr[i]));
        }
        JSONObject result = new JSONObject();
        result.put("success",true);
        ResponseUtil.write(response,result);
        return null;
    }

    @RequestMapping(value = "review")
    public String reviewComment(
            @RequestParam(value = "ids",required = false) String ids,
            @RequestParam(value = "state",required = false) String state,HttpServletResponse response) throws Exception {
        String []idStr = ids.split(",");
        for (int i = 0; i < idStr.length; i++) {
            BlogComment comment = new BlogComment();
            comment.setId(Integer.parseInt(idStr[i]));
            comment.setState(Integer.parseInt(state));
            blogCommentService.updateComment(comment);
        }
        JSONObject result = new JSONObject();
        result.put("success",true);
        ResponseUtil.write(response,result);
        return null;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
