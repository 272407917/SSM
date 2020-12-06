package com.qu.crowd;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author 瞿琮
 * @create 2020-06-28 22:10
 */
// 指定 Spring 给 Junit 提供的运行器类
    @RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解
    @ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class TestDataSource {
    @Autowired
    DataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {
        Connection conn = dataSource.getConnection();
        System.out.println(conn);
    }

   /* @Test
    public void insertMoreAdmin() throws SQLException {
        Connection conn = dataSource.getConnection();
        String sql = "insert into t_admin(login_acct,user_pswd,user_name,email,) values (?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        for (int i = 0; i < 1000; i++) {
            pst.setObject(1,"loginAcct"+i);
            pst.setObject(2,"E10ADC3949BA59ABBE56E057F20F883E");
            pst.setObject(3,"userName"+i);
            pst.setObject(4,"loginAcct"+i+"@aliyun.com");
            pst.executeUpdate();
        }
        pst.close();
        conn.close();
    }*/



}
