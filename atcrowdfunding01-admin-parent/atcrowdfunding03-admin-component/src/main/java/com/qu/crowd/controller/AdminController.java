package com.qu.crowd.controller;

import com.qu.crowd.constant.CrowdConstant;
import com.qu.crowd.entity.Admin;
import com.qu.crowd.services.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;


/**
 * @author 瞿琮
 * @create 2020-06-30 17:50
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/do/doLogin.html")
    public String doLogin(String loginAcct, String userPswd, HttpSession session){
        //1、检验账号密码是否正确
        Admin adminLogin = adminService.getAdminByLoginAcctAndPaswd(loginAcct, userPswd);

        //2、如果验证通过，需要通过session保存记录
        session.setAttribute(CrowdConstant.ATTR_NAME_ADMIN,adminLogin);

        /*3、返回主页面,return "main"如果直接这样返回一个字符串，那么在main页面点击刷新
            将会重复发送/admin/do/doLogin.html请求，因为main页面地址栏没有变
            想要避免重复请求，那么就需要改变地址栏，使用从定向到main页面那么地址栏就是crowd/to/main.html
            通过springMVC.xml文件中的视图映射器完成main页面的展示
          */
        return "redirect:/to/main.html";
    }

    //退出系统
    @RequestMapping("/do/doLogout.html")
    public String doLogout(HttpSession session){
        //注销session
        session.invalidate();
        return "redirect:/admin/to/admin-login.html";
    }



}
