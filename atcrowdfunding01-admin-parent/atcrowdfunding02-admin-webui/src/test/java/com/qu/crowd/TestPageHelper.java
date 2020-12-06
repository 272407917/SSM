package com.qu.crowd;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qu.crowd.entity.Admin;
import com.qu.crowd.mapper.AdminMapper;
import com.qu.crowd.services.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author 瞿琮
 * @create 2020-07-01 13:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class TestPageHelper {
    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testPageHelper(){
        PageHelper.startPage(9,10);
        List<Admin> admins = adminMapper.selectByExample(null);

        //传入查询到的分页记录，传入进去，就可以得到多种分页信息
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);
        System.out.println("当前页："+pageInfo.getPageNum());
        System.out.println("当前总记录数："+pageInfo.getTotal());
        System.out.println("每页显示记录条数："+pageInfo.getSize());
        System.out.println("下一页页码为："+pageInfo.getNextPage());
        System.out.println("上一页页码为："+pageInfo.getPrePage());
        System.out.println("是否有上一页："+pageInfo.isHasPreviousPage());
        System.out.println("是否有下一页："+pageInfo.isHasNextPage());

        //去除放进pageInfo中的查询结果
        List<Admin> list = pageInfo.getList();
        for (Admin admin:list) {
            System.out.println(admin);
        }
        //在页面中联系显示的页面数，就是页面最低的页面导航所需要的的数
        int[] navigatepageNums = pageInfo.getNavigatepageNums();
        for(int i : navigatepageNums){
            System.out.print(i + ",");
        }
        System.out.println();
    }
}
