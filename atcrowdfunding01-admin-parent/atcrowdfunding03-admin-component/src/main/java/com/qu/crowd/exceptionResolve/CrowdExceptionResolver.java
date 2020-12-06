package com.qu.crowd.exceptionResolve;

import com.google.gson.Gson;
import com.qu.crowd.constant.CrowdConstant;
import com.qu.crowd.exception.LoginAcctAlreadyExisted;
import com.qu.crowd.exception.LoginFailedException;
import com.qu.crowd.util.CrowdUtil;
import com.qu.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 瞿琮
 * @create 2020-06-30 16:26
 *
 * 异常处理类
 * 该类只能处理目标请求方法中的异常
 * 无法处理mvc拦截器中的异常
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    //处理新增、修改账户名重复异常
    @ExceptionHandler(LoginAcctAlreadyExisted.class)
    public ModelAndView handlerLoginAcctRepetition(
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String viewName = "";
        String message = exception.getMessage();
        if (message.contains("修改")){
            viewName = "pages/admin-edit";
        }else {
            viewName = "pages/admin-add";
        }

        return ommonResolveException(exception,request,response,viewName);
    }

    //处理登录失败异常
    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView handlerLoginFailedException(
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String viewName = "pages/admin-login";
       return ommonResolveException(exception,request,response,viewName);
    }


    //异常处理的公共方法
    private ModelAndView ommonResolveException(
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response,
            String viewName
    ) throws IOException {

        //1、判断请求类型，是普通请求还是ajax请求，两种请求方式处理方法不同
        boolean requestType = CrowdUtil.judgeRequestType(request);

        if (requestType){
            //是ajax请求
            //2、获取异常信息
            String message = exception.getMessage();
            //3、将获取到的message信息通过json的统一格式返回
            ResultEntity<Object> resultEntity = new ResultEntity();
            //4、将异常信息转换成JSON格式，用来输出
            Gson gson = new Gson();
            String resultJson = gson.toJson(resultEntity);

            //5、把当前JSON 字符串作为当前请求的响应体数据返回给浏览器
            //5.1、创建Write对象
            PrintWriter writer = response.getWriter();
            //5.2、通过write对象将信息输出到浏览器上
            writer.write(resultJson);

            /*6、因为方法返回值是一个ModelAndView，通过springMVC来解析并响应，
                如果返回一个null，就没有这个对象，springMVC就就不会提供响应，
                这里是由自己通过writer提供响应
            */
            return null;
        }

        //是普通请求
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

}
