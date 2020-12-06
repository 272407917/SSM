package com.qu.crowd.services.impl;

import com.qu.crowd.entity.Auth;
import com.qu.crowd.mapper.AuthMapper;
import com.qu.crowd.services.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-07 20:30
 */
@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAllAuth() {
        List<Auth> auths = authMapper.selectByExample(null);
        return auths;
    }

    @Override
    public List<Auth> getAuthsByRoleId(Integer roleId) {
        List<Auth> auths = authMapper.selectAuthByRoleId(roleId);
        return auths;
    }

    @Override
    public void updateAuthByRoleId(HashMap<String,Integer[]> map) {
        //先删除旧的角色权限
        authMapper.deleteOldAuthByRoleId(map.get("roleId")[0]);
        //再插入新的角色权限
        if (map.get("authIds").length>0&&map.get("authIds") != null){
            authMapper.insertNewAuthByRoleId(map.get("roleId")[0],map.get("authIds"));
        }

    }

    @Override
    public List<Auth> getAuthsByAdminId(Integer adminId) {
        List<Auth> authList = authMapper.selectByAdminId(adminId);
        return authList;
    }
}
