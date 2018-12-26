package com.my.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.my.entity.BlogType;
import com.my.entity.Blogger;
import com.my.service.BlogTypeService;
import com.my.service.BloggerService;

@Component
public class InitBloggerData implements ServletContextListener,
		ApplicationContextAware {
	private static ApplicationContext applicationContext;
	//此方法不好用，不能及时更新信息（如果是个人博客完全可以将博主信息存入使用此方法比较方便）
	
	public void contextInitialized(ServletContextEvent sce) {
        /*//先获取servlet上下文
        ServletContext application = sce.getServletContext();
        //同上，获取博客类别信息service
        BlogTypeService blogTypeService = applicationContext.getBean(BlogTypeService.class);
        //获取博主信息service
        BloggerService bloggerService = applicationContext.getBean(BloggerService.class);
        
        //获取博客类别信息放入
        List<BlogType> blogTypeList = blogTypeService.getBlogTypeData();
        application.setAttribute("blogTypeList", blogTypeList);
        //获取博主信息放入
      	Blogger blogger = bloggerService.getBloggerData();
      	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>blogger:"+blogger);
      	//隐藏密码
      	blogger.setPassword(null);
      	application.setAttribute("blogger",blogger);*/
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub

    }

    public void setApplicationContext(ApplicationContext applicationContext) 
            throws BeansException {
        InitBloggerData.applicationContext = applicationContext;
    }

}
