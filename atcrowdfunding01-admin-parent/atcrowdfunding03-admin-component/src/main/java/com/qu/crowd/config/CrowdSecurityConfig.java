package com.qu.crowd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 瞿琮
 * @create 2020-07-10 17:29
 */
//将本类声明成一个配置类
@Configuration
//开启Security权限控制功能
@EnableWebSecurity
//表示开启全局方法权限控制，项目中所有方法都可以通过注解进行权限控制
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CrowdSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CrowdUserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/bootstrap/**",
                            "/css/**",
                            "/fonts/**",
                            "/img/**",
                            "/jquery/**",
                            "/js/**",
                            "/layer/**",
                            "/pagination/*",
                            "/script/**",
                            "/ztree/**")
                .permitAll()
                .antMatchers("/user/to/user.html")
                .hasAnyRole("总裁","经理","部长","组长")
                .antMatchers("/role/to/role-list.html")
                .hasAnyRole("总裁","经理")
                .antMatchers("/menu/to/menu.html")
                .hasAnyRole("总裁","经理","部长")
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()//取消令牌验证
                .formLogin()
                .loginPage("/admin/to/admin-login.html")
                .loginProcessingUrl("/security/do/doLogin.html")
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .defaultSuccessUrl("/to/main.html")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/security/do/doLogout.html")
                .logoutSuccessUrl("/admin/to/admin-login.html")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("message","对不起，你没有权限！");
                        httpServletRequest.getRequestDispatcher("/WEB-INF/error/no-authority.jsp").forward(httpServletRequest,httpServletResponse);
                    }
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }
}
