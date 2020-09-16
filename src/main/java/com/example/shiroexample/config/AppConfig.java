package com.example.shiroexample.config;

import com.example.shiroexample.service.TelCodeService;
import com.example.shiroexample.service.UserService;
import com.example.shiroexample.shiro.MyAuthenticationFilter;
import com.example.shiroexample.shiro.MyAuthentizing;
import com.example.shiroexample.shiro.ShiroRealm;
import com.example.shiroexample.shiro.TelRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
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
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/hello", "anon");
        chainDefinition.addPathDefinition("/auth/login", "anon");

        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("auth", new MyAuthenticationFilter());
        filterFactoryBean.setFilters(filterMap);

        chainDefinition.addPathDefinition("/**", "auth");

        // or allow basic authentication, but NOT require it.
        // chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
        return chainDefinition;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("auth", new MyAuthenticationFilter());
        filterFactoryBean.setFilters(filterMap);
        return filterFactoryBean;
    }


    @Bean
    public SessionManager sessionManager() {
        // 自定义Session
        ShiroSessionToken sessionToken = new ShiroSessionToken();
        // Session存储
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setExpire(2 * 60 * 60);
        RedisManager redisManager = new RedisManager();
        redisSessionDAO.setRedisManager(redisManager);

        sessionToken.setSessionDAO(redisSessionDAO);
        return sessionToken;
    }

    @Bean
    public Authorizer authorizer() {
        MyAuthentizing authentizing = new MyAuthentizing();

        return authentizing;
    }



}
