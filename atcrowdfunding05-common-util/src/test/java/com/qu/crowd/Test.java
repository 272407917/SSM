package com.qu.crowd;

import com.qu.crowd.util.CrowdUtil;

/**
 * @author 瞿琮
 * @create 2020-06-30 17:41
 */
public class Test {

    @org.junit.Test
    public void test(){
        String md5 = CrowdUtil.md5("123456");
        System.out.println(md5);

    }
}
