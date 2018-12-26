package com.my.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.my.entity.Blogger;
/**
 * 未登陆用户不可请求controller操作（即不可查询，只能请求登陆方法）
 * @author wyw
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	private List<String> passList=new ArrayList<>();

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//允许放行的url路径
		String url=request.getRequestURI();
		System.out.println("url-->"+url);
		for(String passUrl:passList) {
			System.out.println("passUrl-->"+passUrl);
			if(url.indexOf(passUrl)>0) {
				return true;
			}
		}
		//已经登录未退出的可放行
		Blogger currentUser = (Blogger)request.getSession().getAttribute("currentUser");
		if( currentUser != null){
			return true;
		}
		request.getRequestDispatcher("login.jsp").include(request, response);
		return false;
	}

	public List<String> getPassList() {
		return passList;
	}

	public void setPassList(List<String> passList) {
		this.passList = passList;
	}
	
}
