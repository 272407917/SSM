package com.qu.crowd.intercepters;

import com.qu.crowd.constant.CrowdConstant;
import com.qu.crowd.entity.Admin;
import com.qu.crowd.exception.AccessForbiddenException;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 瞿琮
 * @create 2020-06-30 21:34
 * 登录拦截器，防止未登录直接访问main等其他页面，未登录时访问其他页面会自动跳转到登录页面
 * preHandle：拦截于请求刚进入时，进行判断，需要boolean返回值，如果返回true将继续执行，如果返回false，将不进行执行。一般用于登录校验。
 * postHandle：拦截于方法成功返回后，视图渲染前，可以对modelAndView进行操作。
 * afterCompletion：拦截于方法成功返回后，视图渲染前，可以进行成功返回的日志记录。
 */
public class LoginIntercepter extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、取出session中admin对象
        HttpSession session = request.getSession();
        Admin admin =(Admin) session.getAttribute(CrowdConstant.ATTR_NAME_ADMIN);

        //2、判断session中是否有admin对象
        if (admin==null){
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        }
        //3、如果存在admin表示已经登录，放行，还需要在mvc.xml文件中配置拦截器
        return true;
    }
}
