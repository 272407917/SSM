package com.qu.crowd.controller;

import com.qu.crowd.constant.CrowdConstant;
import com.qu.crowd.entity.Auth;
import com.qu.crowd.entity.Role;
import com.qu.crowd.services.api.AdminService;
import com.qu.crowd.services.api.AuthService;
import com.qu.crowd.services.api.RoleService;
import com.qu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-07 12:44
 */
@Controller
@RequestMapping("/assign")
public class AssingController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    //分配角色页面跳转
    @PreAuthorize("hasAnyRole('总裁','经理','部长','组长')")
    //@PreAuthorize("hasAnyAuthority('user:delete')")
    @RequestMapping("/to/assignRole/{Id}/{pageNum}/{keyWord}.html")
    public String toAssignRole(
            @PathVariable(value = "Id")      Integer adminId,
            @PathVariable(value = "pageNum") String  pageNum,
            @PathVariable(value = "keyWord") String  keyWord,
            ModelMap modelMap
    ){

        //查询该admin已分配的角色
        List<Role> assignRoleList = roleService.getAssignedList(adminId);
        //查询该admin为分配的角色
        List<Role> unAssignRoleList = roleService.getunAssignedList(adminId);

        modelMap.addAttribute("assignedList",assignRoleList);
        modelMap.addAttribute("unAssignRoleList",unAssignRoleList);
        modelMap.addAttribute("adminId",adminId);
        modelMap.addAttribute("pageNum",pageNum);
        modelMap.addAttribute("keyWord",keyWord);
        return "pages/role-assign";
    }

    //保存新分配的角色信息
    @PreAuthorize("hasAnyRole('总裁','经理','部长','组长')")
    @RequestMapping("/do/assignRole.html")
    public String doAssignRole(
            Integer adminId,
            Integer pageNum,
            String  keyWord,
            Integer[] assignRoleIds
    ){
        if (assignRoleIds!=null&&assignRoleIds.length>0){
            roleService.saveAssignRole(adminId,assignRoleIds);
        }

        return "redirect:/user/to/user.html?pageNum="+pageNum+"&keyWord="+keyWord;
    }

    @RequestMapping("/get/allAuth.json")
    @ResponseBody
    public ResultEntity<List<Auth>> getAllAuth(){
        try {
            List<Auth> auths = authService.getAllAuth();
            return ResultEntity.successWithData(auths);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_SELECT_FAILED);
        }
    }

    @RequestMapping("/get/authByRoleId.json")
    @ResponseBody
    public ResultEntity<List<Auth>> getAuthsByRoleId(Integer roleId){
        try {
            List<Auth> auths = authService.getAuthsByRoleId(roleId);
            return ResultEntity.successWithData(auths);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_SELECT_FAILED);
        }
    }

    //角色修改权限
    @PreAuthorize("hasAnyRole('总裁','经理')")
    @RequestMapping("/do/editAuth.json")
    @ResponseBody
    public ResultEntity editAuthByRoleId(@RequestBody HashMap<String,Integer[]> map ){
        try {
            authService.updateAuthByRoleId(map);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_UPDATE_FAILED);
        }
    }

}
