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
 * @create 2020-06-29 17:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class TestSpringTx {

    Logger logger = LoggerFactory.getLogger(TestLogging.class);

    @Autowired
    private TestService adminService;

    @Test
    public void testUpdate(){
        Integer updateAdmin = adminService.updateAdmin(new Admin(1, "李四", "123456",
                "aaa", "123@136.com", null));
        logger.info("更新影响条数："+updateAdmin);
    }
}
