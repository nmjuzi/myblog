package com.my.controller;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.alibaba.fastjson.JSONObject;
import com.my.entity.Blogger;
import com.my.service.BloggerService;
import com.my.util.MD5Util;
import com.my.util.ResponseUtil;


@Controller  //注册为控制器bean
@RequestMapping(value = "/blogger")    //请求路径
public class BloggerController {
	 	@Resource
	    private BloggerService bloggerService;
	 	
	 	@RequestMapping(value = "login", method = RequestMethod.POST)
	    public String login(Blogger blogger, HttpServletRequest request) {
	        //获取登录实体
	        Subject subject = SecurityUtils.getSubject();
	        //获取加密后密码
	        String password = MD5Util.md5(blogger.getPassword(), "wyw");
	        System.out.println(password);
	        //获取用户密码登录token
	        UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(), password);
	        try {
	            //根据token登录 会调用MyRealm中的doGetAuthenticationInfo方法进行身份认证
	            subject.login(token);
	            return "redirect:/admin/main.jsp";
	        } catch (AuthenticationException e) {
	            e.printStackTrace();
	            request.setAttribute("bloger", blogger);
	            //提示信息
	            request.setAttribute("errorInfo", "用户名或密码错误");
	            return "login";
	        }
	    }
	 	
	 	@RequestMapping(value = "logout", method = RequestMethod.POST)
	    public String logout(Blogger blogger, HttpServletRequest request,HttpServletResponse response) throws Exception {
	 		//清空currentUser
	 		//request.getSession().removeAttribute("currentUser");
	 		request.getSession().invalidate();
	 		 JSONObject result = new JSONObject();
	         result.put("success", true);
	         ResponseUtil.write(response, result);
	         return null;
	    }
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
}
