package com.example.shiroexample.config;

import com.example.shiroexample.service.TelCodeService;
import com.example.shiroexample.service.UserService;
import com.example.shiroexample.shiro.MyAuthenticationFilter;
import com.example.shiroexample.shiro.ShiroRealm;
import com.example.shiroexample.shiro.TelRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
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
public class AppConfig {

    @Bean
    public Realm realm(UserService userService) {
        return new ShiroRealm(userService);
    }

    @Bean
    public Realm telRealm(TelCodeService telCodeService) {
        return new TelRealm(telCodeService);
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/auth/login", "anon");
        chainDefinition.addPathDefinition("/**", "myauth");
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
        filterFactoryBean.setLoginUrl("/error");

        return filterFactoryBean;
    }


    @Bean
    public ShiroSessionManager sessionManager() {
        // 自定义Session
        ShiroSessionManager sessionToken = new ShiroSessionManager();
        // Session存储
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setExpire(2 * 60 * 60);
        RedisManager redisManager = new RedisManager();
        redisSessionDAO.setRedisManager(redisManager);

        sessionToken.setSessionDAO(redisSessionDAO);
        return sessionToken;
    }



}
