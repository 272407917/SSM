package com.qu.crowd;

import com.qu.crowd.entity.Role;
import com.qu.crowd.mapper.RoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 瞿琮
 * @create 2020-07-03 13:02
 */
// 指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class TestRoleMapper {

    @Autowired
    private RoleMapper roleMapper;

    /*@Test
    public void testAddMoreRole(){
        for (int i = 0; i < 500; i++) {
            roleMapper.insert(new Role(null,"roleName"+i));
        }

    }*/
}
