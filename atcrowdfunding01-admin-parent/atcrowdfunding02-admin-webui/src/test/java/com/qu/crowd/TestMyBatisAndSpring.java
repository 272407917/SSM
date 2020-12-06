package com.qu.crowd;

import com.qu.crowd.entity.Admin;
import com.qu.crowd.services.api.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 瞿琮
 * @create 2020-06-28 22:39
 */
// 指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解
@ContextConfiguration(locations={"classpath:spring-persist-mybatis.xml"})
public class TestMyBatisAndSpring {

    Logger logger = LoggerFactory.getLogger(TestLogging.class);

    @Autowired
    private TestService adminService;

    @Test
    public void testFindAdmin(){
        Integer id = 1;
        Admin admin = adminService.findAdmin(id);
        logger.info("查询到id="+id+"的记录名称为："+admin.getUserName());
    }
}
