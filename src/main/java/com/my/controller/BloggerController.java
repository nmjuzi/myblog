package com.my.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.alibaba.fastjson.JSONObject;
import com.my.entity.Blogger;
import com.my.redis.RedisUtil;
import com.my.util.MD5Util;
import com.my.util.ResponseUtil;


@Controller  //注册为控制器bean
@RequestMapping(value = "/blogger")    //请求路径
public class BloggerController {
	 	@Resource
	 	private RedisUtil redisUtil;
	 	
	 	private RedisTemplate<String, String> redisTemplate;
	 	
	 	private static Map<String, Integer> lockUserMap = new HashMap<String, Integer>();  

	 	public static Map<String, Integer> getLockUserMap() {
			return lockUserMap;
		}

		public static void setLockUserMap(Map<String, Integer> lockUserMap) {
			BloggerController.lockUserMap = lockUserMap;
		}
		
		private final Logger logger = Logger.getLogger(this.getClass());
		
		@RequestMapping(value = "login", method = RequestMethod.POST)
	    public String login(Blogger blogger, HttpServletRequest request) throws UnknownAccountException {
			String username = blogger.getUserName();
			//用户锁定次数判断
			logger.info("lockUserMap:"+lockUserMap);
			if(lockUserMap.containsKey(username)){
				int lockNum = lockUserMap.get(username);
				if(lockNum>2){
					request.setAttribute("errorInfo", "您的账户已锁定，请24小时后再次尝试");
		            return "login";
				}
			}
	        //获取登录实体
	        Subject subject = SecurityUtils.getSubject();
	        //获取加密后密码
	        String password = MD5Util.md5(blogger.getPassword(), "wyw");
	        //获取用户密码登录token
	        UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(), password);
	        System.out.println("token:"+token);
	        try {
	            //根据token登录 会调用MyRealm中的doGetAuthenticationInfo方法进行身份认证
	            subject.login(token);
	            //取消用户锁定  假如在3次之内登录成功应清除用户锁定
				if(lockUserMap.containsKey(username)){
					lockUserMap.remove(username);
				}  
	            return "redirect:/admin/main.jsp";
	        } catch (AuthenticationException e) {
	            e.printStackTrace();
	            if(e.getClass().toString().indexOf("UnknownAccountException")>-1){
	            	request.setAttribute("errorInfo", "您输入的账号不存在，请确认您的输入");
	            }else{
	            	//用户锁定次数增加
					if(lockUserMap.containsKey(username)){
						int lockNum = lockUserMap.get(username);
						lockNum=lockNum+1;
						request.setAttribute("errorInfo", "您已输错密码"+lockNum+"次，输错3次您的账户将被限制登录");
						if(lockNum==3){
							request.setAttribute("errorInfo", "您已输错密码3次，您的账户已锁定，请24小时后再次尝试");
						}
						lockUserMap.put(username,lockNum);
					}else{
						request.setAttribute("errorInfo", "您已输错密码1次，输错3次您的账户将被限制登录");
						lockUserMap.put(username,1);
					}
	            }
	            
	            return "login";
	        }
	    }
	 	
	 	@RequestMapping(value = "logout", method = RequestMethod.POST)
	    public String logout(Blogger blogger, HttpServletRequest request,HttpServletResponse response) throws Exception {
	 		//清空currentUser
	 		//request.getSession().removeAttribute("currentUser");
	 		 System.out.println("logout方法的ceshikey："+redisUtil.get("ceshikey"));
//	         redisUtil.deleteKeys("ceshikey");
//	         System.out.println("删除后："+redisUtil.get("ceshikey"));
	 		request.getSession().invalidate();
	 		 JSONObject result = new JSONObject();
	         result.put("success", true);
	         ResponseUtil.write(response, result);
	         return null;
	    }
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
}
