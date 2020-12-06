package com.qu.crowd.config;

import com.qu.crowd.entity.Admin;
import com.qu.crowd.entity.Auth;
import com.qu.crowd.entity.Role;
import com.qu.crowd.services.api.AdminService;
import com.qu.crowd.services.api.AuthService;
import com.qu.crowd.services.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-11 10:39
 */
@Component
public class CrowdUserDetailService implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {
        //1、根据登录名查询用户信息
        Admin adminByLoginAcct = adminService.getAdminByLoginAcctAndPaswd(loginAcct, null);

        //2、装在对应admin的角色和权限
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();

        //2.1、查询到对应admin的角色
        List<Role> roleList = roleService.getAssignedList(adminByLoginAcct.getId());
        if (roleList!=null&&roleList.size()>0){
            for (Role role:roleList) {
                String roleName = role.getRoleName();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+roleName);
                authorities.add(authority);
            }
        }


        //2.2、查询对应admin的权限
        List<Auth> authList = authService.getAuthsByAdminId(adminByLoginAcct.getId());
        if (authList!=null&&authList.size()>0){
            for (Auth auth:authList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(auth.getName());
                authorities.add(authority);
            }
        }
        CrowdUserDetail userDetail = new CrowdUserDetail(adminByLoginAcct,authorities);
        return userDetail;
    }
}
