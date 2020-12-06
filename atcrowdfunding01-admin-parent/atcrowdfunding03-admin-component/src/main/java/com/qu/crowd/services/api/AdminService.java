package com.qu.crowd.services.api;

import com.github.pagehelper.PageInfo;
import com.qu.crowd.entity.Admin;
import com.qu.crowd.entity.Role;

import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-06-30 18:04
 */
public interface AdminService {
    //登录验证
    Admin getAdminByLoginAcctAndPaswd(String loginAcct,String userPswd);
    //查询所有并分页展示
    PageInfo<Admin> getAdminByKeyWord(Integer pageNum, Integer pageSize, String keyWord);
    //根据AdminId删除数据
    Integer removeAdminById(Integer id);

    Integer saveAdmin(Admin admin);

    Integer updateAdmin(Admin admin, String originalLoginAcct);

    Admin getAdminByAdminId(Integer adminId);

}
