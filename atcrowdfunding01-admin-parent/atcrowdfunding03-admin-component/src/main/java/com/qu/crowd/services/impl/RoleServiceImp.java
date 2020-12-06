package com.qu.crowd.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qu.crowd.entity.Role;
import com.qu.crowd.mapper.RoleMapper;
import com.qu.crowd.services.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-03 15:47
 */
@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getRoleByKeyWord(Integer pageNum, Integer pageSize, String keyWord) {
        PageHelper.startPage(pageNum,pageSize);
       List<Role> roleList = roleMapper.selectRoleByKeyWord(keyWord);
       PageInfo<Role> RoleInfo = new PageInfo<>(roleList);
        return RoleInfo;
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void deleteRoleByIds(List<Integer> roleIds) {
        roleMapper.deleteRoleByIds(roleIds);
    }

    //查询admin已分配的角色信息
    @Override
    public List<Role> getAssignedList(Integer adminId) {
        List<Role> roleList = roleMapper.selectAssignedList(adminId);
        return roleList;
    }
    //查询admin未分配的角色信息
    @Override
    public List<Role> getunAssignedList(Integer adminId) {
        List<Role> roleList = roleMapper.selectunAssignedList(adminId);
        return roleList;
    }

    @Override
    public void saveAssignRole(Integer adminId,Integer[] assignRoleId) {
        //保存新分配的角色信息，先删除旧的
        roleMapper.deleteOldRoleByAdminId(adminId);
        if (assignRoleId!=null&&assignRoleId.length>0){
            //然后保存新的
            roleMapper.saveNewAssignRole(adminId,assignRoleId);
        }

    }
}
