package com.qu.crowd.config;

import com.qu.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 瞿琮
 * @create 2020-07-11 12:18
 */
public class CrowdUserDetail extends User {


    private Admin OriginalAdmin;

    public CrowdUserDetail(Admin admin, Collection<? extends GrantedAuthority> authorities) {
        super(admin.getLoginAcct(), admin.getUserPswd(), authorities);
        OriginalAdmin = admin;
    }

    public Admin getOriginalAdmin() {
        return OriginalAdmin;
    }

    public void setOriginalAdmin(Admin originalAdmin) {
        OriginalAdmin = originalAdmin;
    }
}
