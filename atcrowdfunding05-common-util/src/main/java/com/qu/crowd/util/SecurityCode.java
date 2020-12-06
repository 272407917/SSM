package com.qu.crowd.util;

/**
 * @author 瞿琮
 * @create 2020-07-29 16:10
 * 生成验证码
 */
public class SecurityCode {
    public static String generateCode(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int code = (int)(Math.random()*10);
            builder.append(code);
        }
        return builder.toString();
    }

}
