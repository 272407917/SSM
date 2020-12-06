package com.qu.crowd.services.impl;

import com.qu.crowd.entity.Admin;
import com.qu.crowd.mapper.AdminMapper;
import com.qu.crowd.services.api.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 瞿琮
 * @create 2020-06-28 22:42
 */
@Service
public class TestServiceImp implements TestService {

    @Autowired
    private AdminMapper adminMapper;


    public Admin findAdmin(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    public Integer updateAdmin(Admin admin) {
        int update = adminMapper.updateByPrimaryKey(admin);
        //测试事务是否生效
        //int i=1/0;
        //throw new RuntimeException("测试错误");
        return update;
    }
}
