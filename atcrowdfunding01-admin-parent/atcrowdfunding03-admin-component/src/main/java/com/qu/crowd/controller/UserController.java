package com.qu.crowd.controller;

import com.github.pagehelper.PageInfo;
import com.qu.crowd.constant.CrowdConstant;
import com.qu.crowd.entity.Admin;
import com.qu.crowd.services.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-06-30 21:28
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AdminService adminService;

    //点击用户维护后，进行分页查询
    @RequestMapping("/to/user.html")
    public String toUser(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyWord",required = false,defaultValue = "")    String keyWord,
            ModelMap modelMap
    ){

        PageInfo<Admin> adminPageInfo = adminService.getAdminByKeyWord(pageNum,pageSize,keyWord);
        modelMap.addAttribute("adminPageInfo",adminPageInfo);
        return "pages/user";
    }

    @PreAuthorize("hasAnyRole('总裁','经理','部长')")
    @RequestMapping("/do/delete/{adminId}/{pageNum}/{keyWord}.html")
    public String doDeleteAdmin(
            @PathVariable(value = "adminId") Integer adminId,
            @PathVariable(value = "pageNum") Integer pageNum,
            @PathVariable(value = "keyWord") String  keyWord
    ){
        Integer removeAdminById = adminService.removeAdminById(adminId);

        return "redirect:/user/to/user.html?pageNum="+pageNum+"&keyWord="+keyWord;
    }

    //添加记录
    @PreAuthorize("hasAnyRole('总裁','经理','部长')")
    @RequestMapping("/do/addAdmin.html")
    public String doAdd(Admin admin){

        Integer addAdmin = adminService.saveAdmin(admin);
        return "redirect:/user/to/user.html";
    }

    //修改记录
    //1、跳转修改页面，因为要带参数，所以不适用mvc.xml跳转
    @PreAuthorize("hasAnyRole('总裁','经理','部长')")
    @RequestMapping("/to/admin-edit/{Id}/{pageNum}/{keyWord}.html")
    public String toAdminEdit(
            @PathVariable(value = "Id")      Integer adminId,
            @PathVariable(value = "pageNum") String  pageNum,
            @PathVariable(value = "keyWord") String  keyWord,
            ModelMap modelMap
    ){
       Admin admin = adminService.getAdminByAdminId(adminId);
       modelMap.addAttribute(CrowdConstant.ATTR_NAME_ADMIN,admin);
       modelMap.addAttribute("pageNum",pageNum);
       modelMap.addAttribute("keyWord",keyWord);
       return "pages/admin-edit";
    }

    //该注解是在所有请求方法之前执行，因为执行更新操作时数据库约束Psw不能为空，所以先要根据Id查询出
    //原始数据，然后在更新时，更新传进来的属性值，如果某一属性没有传值则保持原始数据
    @ModelAttribute
    public void getAdminById(
            @RequestParam(value = "adminId",required = false) Integer adminId,
            ModelMap modelMap
            ){
        //表示可以根据id查询数据
        if (adminId!=null){
            Admin admin = adminService.getAdminByAdminId(adminId);
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_ADMIN,admin);
        }
    }

    //2、报存修改数据,需要检查修改后的账户名是否重复
    @RequestMapping("/do/adminEdit.html")
    public String doEdit(Admin admin,String originalLoginAcct,String pageNum,String keyWord){

        Integer update = adminService.updateAdmin(admin,originalLoginAcct);

        return "redirect:/user/to/user.html?pageNum="+pageNum+"&keyWord="+keyWord;
    }

}
