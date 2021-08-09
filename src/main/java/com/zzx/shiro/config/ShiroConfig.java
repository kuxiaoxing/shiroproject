package com.zzx.shiro.config;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

   @Bean
    public IniRealm iniRealm(){
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        return  iniRealm;
    }
    //使用JdbcRealm，则必须遵守JdbcRealm所需的表结构规范（权限设计）
    //因为JdbcRealm会连接数据库，有三张表：users
  /* @Bean
    public JdbcRealm getJdbcRealm(){
        JdbcRealm jdbcRealm = new JdbcRealm();
        return  jdbcRealm;
    }*/


    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(IniRealm iniRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
       //securityManager要想完成检验，需要realm
        securityManager.setRealm(iniRealm);
        return securityManager;
    }
/*   @Bean
   public DefaultWebSecurityManager getDefaultWebSecurityManager(JdbcRealm jdbcRealm ){
       DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
       //securityManager要想完成检验，需要realm
       securityManager.setRealm(jdbcRealm);
       return securityManager;
   }*/
    //配置过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean fliter = new ShiroFilterFactoryBean();
        //过滤器就是shiro进行权限校验的核心，进行认证和授权是需要SecurityMannager的
        fliter.setSecurityManager(securityManager);
        //设置shiro的拦截规则
        // anon 匿名用户可访问
        // authc 认证用户可访问
        // user 使用RemeberMe的用户可访问
        //perms 对应权限可访问
        //role 对应的角色可访问
        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("/","anon");
        filterMap.put("/login.html","anon");
        filterMap.put("/register.html","anon");
        filterMap.put("/user/login","anon");
        filterMap.put("/user/register","anon");
        filterMap.put("/static/**","anon");
        filterMap.put("/**","authc");
        fliter.setFilterChainDefinitionMap(filterMap);
        fliter.setLoginUrl("/login.html");
        //设置未授权访问的页面路径
        fliter.setUnauthorizedUrl("/login.html");
        return  fliter;
    }

}
