package com.qu.crowd.controller;

import com.github.pagehelper.PageInfo;
import com.qu.crowd.constant.CrowdConstant;
import com.qu.crowd.entity.Role;
import com.qu.crowd.services.api.RoleService;
import com.qu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-03 15:47
 */
@RequestMapping("/role")
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/get/role-list.json")
    @ResponseBody
    public ResultEntity<PageInfo<Role>> getRoleInfo(
            Integer pageNum,
            Integer pageSize,
            String  keyWord
    ){
        try {
            //查询过程没有异常，成果获取到数据
            PageInfo<Role> rolePageInfo = roleService.getRoleByKeyWord(pageNum,pageSize,keyWord);
            return ResultEntity.successWithData(rolePageInfo);
        }catch (Exception e){
            //查询失败，没有获取到数据
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_SELECT_FAILED);
        }

    }

    //新增功能
    @PreAuthorize("hasRole('总裁')")
    @RequestMapping("/do/addRole.json")
    @ResponseBody
    public ResultEntity addRole(Role role){
        try {
            roleService.saveRole(role);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_INSERT_FAILED);
        }
    }

    //修改功能
    @PreAuthorize("hasAnyRole('总裁','经理')")
    @RequestMapping("/do/editRole.json")
    @ResponseBody
    public ResultEntity editRole(Role role){
        try {
            roleService.updateRole(role);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_UPDATE_FAILED);
        }
    }

    @PreAuthorize("hasRole('总裁')")
    @RequestMapping("/do/deleteRole.json")
    @ResponseBody
    public ResultEntity deleteRole(@RequestBody List<Integer> roleIdList){
        try {
            roleService.deleteRoleByIds(roleIdList);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_DELETE_FAILED);
        }

    }
}
