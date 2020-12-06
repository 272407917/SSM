package com.qu.crowd.controller;

import com.qu.crowd.constant.CrowdConstant;
import com.qu.crowd.entity.Admin;
import com.qu.crowd.exception.LoginFailedException;
import com.qu.crowd.services.api.TestService;
import com.qu.crowd.util.CrowdUtil;
import com.qu.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-06-29 20:17
 */
@Controller
@RequestMapping("/admin")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService adminService;

    @RequestMapping("/test")
    public String test(){
        //测试spring-web-mvc.xml文件中的异常映射
        int i= 1/0;
        return "success";
    }

    //返回的json类型数据就必须后缀名为.json，不然会报406错误
    @RequestMapping("/testAjax.json")
    @ResponseBody
    public ResultEntity<Admin> testAjax(@RequestParam(value = "a",required = false) Integer a){
        logger.info("ajax请求参数："+a);
        Admin admin = adminService.findAdmin(1);
        //操作成功，并携带数据返回
        return ResultEntity.successWithData(admin);
    }

    //传入的参数是一个数组
    @RequestMapping ("/testJson1.json")
    @ResponseBody
    public ResultEntity<Object> testJson1(
            @RequestParam(value = "ids[]",required = false) List<Integer> ids
    ){
        // 如何接受ajax请求发送过来的数据
        for(Integer id : ids){
            System.out.println("id = " + id);
        }
        //操作成功，不携带数据返回
        return ResultEntity.successWithoutData();
    }


    @RequestMapping("/textJson2.json")
    @ResponseBody
    public ResultEntity<String> testJson2(
            @RequestBody List<Integer> ids,
            Exception exception
    ){
        for(Integer id : ids){
            System.out.println("id = " + id);
        }
        Admin admin = adminService.findAdmin(1);
        admin.setLoginAcct("张三");
        Integer integer = adminService.updateAdmin(admin);
        return ResultEntity.failed(exception.getMessage());
    }

    //测试工具类中judgeRequestType方法，判断请求是否是ajax请求
    @RequestMapping("/testjudgeRequestType.json")
    @ResponseBody
    public String testjudgeRequestType(
            HttpServletRequest request
    ){
        boolean b = CrowdUtil.judgeRequestType(request);
        System.out.println(b);
        return "success";
    }

    @RequestMapping("/testCrowdExceptionResolver.html")
    public void testCrowdExceptionResolver(){
        for (int i = 0; i < 5; i++) {
            if (i==4){
                //异常处理
                throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
            }
        }
    }

}
