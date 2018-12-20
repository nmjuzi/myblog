package com.my.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Author wyw
 * @Description MD5加密工具类
 * @Date 2018/12/20
 */
public class MD5Util {
    /**
     * @Description 使用Shiro中的md5加密
     * @param str
     * @param salt
     * @return
     */
    public static String md5(String str,String salt){
        //Md5Hash是Shiro中的一个方法
        return new Md5Hash(str, salt).toString();
    }
}
