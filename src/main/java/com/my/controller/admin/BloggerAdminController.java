package com.my.controller.admin;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.my.entity.Blogger;
import com.my.service.BloggerService;
import com.my.util.DateUtil;
import com.my.util.MD5Util;
import com.my.util.PathUtil;
import com.my.util.ResponseUtil;

@Controller
@RequestMapping(value = "/admin/blogger")
public class BloggerAdminController {
	@Resource
    private BloggerService bloggerService;
	
	 //获取博主信息
    @RequestMapping(value = "getBloggerInfo")
    public String getBloggerData(HttpServletResponse response) throws Exception {
        Blogger blogger = bloggerService.getBloggerData();
        String jsonStr = JSONObject.toJSONString(blogger);
        JSONObject object = JSONObject.parseObject(jsonStr);
        ResponseUtil.write(response,object);
        return null;
    }

    //更新博主信息
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String saveBlogger(@RequestParam(value = "imageFile",required = false) MultipartFile imageFile, Blogger blogger,
                              HttpServletRequest request,HttpServletResponse response) throws Exception {
        //判断是否有上图片 有就更新
        if(!imageFile.isEmpty()){
           // String filePath = PathUtil.getRootPath(); //获取服务器根路径
        	String filePath = request.getSession().getServletContext().getRealPath("/");
        	System.out.println(">>>>>>>>>>>>>>>>>"+filePath);
            String imageName = DateUtil.getCurrentDateStr() + "." + imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath + "/static/userImages/" + imageName));
            blogger.setImageName(imageName);
        }
        int resultTotal = bloggerService.updateBlogger(blogger);
        JSONObject result = new JSONObject();
        if(resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    //更新博主密码
    @RequestMapping(value = "modtifyPassword",method = RequestMethod.POST)
    public String modityBloggerPassword(Blogger blogger,HttpServletResponse response) throws Exception {
        //加密
        String newPassword = MD5Util.md5(blogger.getPassword(),"wyw");
        blogger.setPassword(newPassword);
        int resultTotal = bloggerService.updateBlogger(blogger);
        JSONObject result = new JSONObject();
        if(resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }
}