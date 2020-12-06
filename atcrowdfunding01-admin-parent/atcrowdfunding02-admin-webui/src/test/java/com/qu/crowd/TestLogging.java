package com.qu.crowd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 瞿琮
 * @create 2020-06-29 13:12
 */
// 指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class TestLogging {

    Logger logger = LoggerFactory.getLogger(TestLogging.class);
    @Test
    public void testLogging(){
        /**
         * 运行打印出Info、Debug、Warn、Error四个个，是因为默认的日志级别是Debug
         * 默认级别以下的是不会打印的，日志级别可以自己指定
         * */
        logger.trace("追踪日志.....");
        logger.debug("调试日志.....");
        logger.info("信息日志.....");
        logger.warn("警告日志.....");
        logger.error("错误日志.....");
    }
}
