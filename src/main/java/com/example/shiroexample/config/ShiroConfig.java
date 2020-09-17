package com.example.shiroexample.config;

import com.example.shiroexample.service.TelCodeService;
import com.example.shiroexample.service.UserService;
import com.example.shiroexample.shiro.MyAuthenticationFilter;
import com.example.shiroexample.shiro.ShiroSessionManager;
import com.example.shiroexample.shiro.realm.UserRealm;
import com.example.shiroexample.shiro.realm.TelCodeRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /*
    用户名密码登录
     */
    @Bean
    public Realm realm(UserService userService) {
        return new UserRealm(userService);
    }

    /*
    手机号验证码登录
     */
    @Bean
    public Realm telRealm(TelCodeService telCodeService) {
        return new TelCodeRealm(telCodeService);
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/auth/login", "anon");
        chainDefinition.addPathDefinition("/**", "myauth");

        // or allow basic authentication, but NOT require it.
        // chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
        return chainDefinition;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("myauth", new MyAuthenticationFilter());

        filterFactoryBean.setFilters(filterMap);
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        filterFactoryBean.setLoginUrl("/auth/401");
        // todo: not work, because it is a authentication filter
        filterFactoryBean.setUnauthorizedUrl("/auth/403");

        return filterFactoryBean;
    }


    @Bean
    public ShiroSessionManager sessionManager() {
        // 自定义Session
        ShiroSessionManager sessionToken = new ShiroSessionManager();
        // Session存储， 默认ip+port
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setExpire(2 * 60 * 60);
        RedisManager redisManager = new RedisManager();
        redisSessionDAO.setRedisManager(redisManager);

        sessionToken.setSessionDAO(redisSessionDAO);
        return sessionToken;
    }


}
