package com.my.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.my.entity.Blogger;
import com.my.redis.RedisUtil;
import com.my.service.BloggerService;

import javax.annotation.Resource;

/**
 * @Description 自定义realm
 */
public class MyRealm extends AuthorizingRealm{

    @Resource
    private BloggerService bloggerService;
    
   


    /**
     * 为当前登陆的用户授予角色和权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //因为我们是个人博客 所以不存在角色权限
    	//如果帐号不存在，输出
        throw new UnknownAccountException();
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进来了。。。");
    	String username = (String) authenticationToken.getPrincipal(); //获取用户名
    	System.out.println(username);
        Blogger blogger = bloggerService.getBloggerByName(username);   //重数据库查询用户信息
        System.out.println(blogger);
        if (blogger != null) {
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger);//把当前用户存到session中
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
                    blogger.getUserName(), blogger.getPassword(), "MyRealm");
            return authcInfo;
        } else {
            return null;
        }
    }
}
