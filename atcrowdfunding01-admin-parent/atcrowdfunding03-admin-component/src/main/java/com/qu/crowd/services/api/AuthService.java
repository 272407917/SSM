package com.qu.crowd.services.api;

import com.qu.crowd.entity.Auth;

import java.util.HashMap;
import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-07 20:29
 */
public interface AuthService {
    List<Auth> getAllAuth();

    List<Auth> getAuthsByRoleId(Integer roleId);

    void updateAuthByRoleId(HashMap<String,Integer[]> map);

    List<Auth> getAuthsByAdminId(Integer id);
}
