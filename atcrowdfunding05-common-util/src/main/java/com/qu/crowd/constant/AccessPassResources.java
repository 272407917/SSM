package com.qu.crowd.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 瞿琮
 * @create 2020-08-02 21:09
 * ZuulFilter执行登录拦截时排除的请求
 */
public class AccessPassResources {
    //存放不拦截的请求
    public static final Set<String> PASS_RES_SET = new HashSet<String>();
    //静态代码块，类加载的时候初始化赋值
    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/do/login");
        PASS_RES_SET.add("/auth/do/logout");
        PASS_RES_SET.add("/auth/member/to/reg/page.html");
        PASS_RES_SET.add("/auth/member/to/login/page.html");
        PASS_RES_SET.add("/auth/member/get/code/message.json");
        PASS_RES_SET.add("/auth/do/member/register");
    }

    //存放静态资源
    public static final Set<String> STATIC_RES_SET = new HashSet<String>();
    //由于静态资源的请求是/aaa/bbb/ccc.css，所以只写第一层是无法排除的
    //所以写一个方法，拍判断请求的第一层是不是STATIC_RES_SET内的，如果是那就是静态资源就放行
    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    //servletPath，请求路径
    public static boolean judgeCurrentServletPathWetherStaticResource(String servletPath){
        //1、判断请求路径是否有效
        if (servletPath == null || servletPath.length() == 0){
            throw new RuntimeException(CrowdConstant.MESSAGE_INVALID_STRING);
        }

        //2、字符串有效，拆分字符串，并判断STATIC_RES_SET中是否存在
        String[] split = servletPath.split("/");

        //3、取出请求第一层的目录名，因为请求路径是以/开头的，所以数组第一位是""，
        // 因为split会将/两边的字符以此保存进数组中
        String firstLevelPath = split[1];

        //4、判断是否在set集合中
        boolean contains = STATIC_RES_SET.contains(firstLevelPath);
        return contains;
    }
}
