package com.shiro.learning.fifteenk.config;

import com.shiro.learning.fifteenk.realm.CustomRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
    public class ShiroConfiguration {

        @Bean(name = "shiroFilter")
        public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
            ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
            shiroFilterFactoryBean.setSecurityManager(securityManager);
            shiroFilterFactoryBean.setLoginUrl("/login");
            shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
            Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
            // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
            filterChainDefinitionMap.put("/login", "anon");
            filterChainDefinitionMap.put("/", "anon");
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            return shiroFilterFactoryBean;

        }

        @Bean
        public SecurityManager securityManager(SessionStorageEvaluator sessionStorageEvaluator) {
            DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
            //设置realm
            defaultSecurityManager.setRealm(customRealm());
            //设置subjectDao
            DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
            defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
            defaultSecurityManager.setSubjectDAO(defaultSubjectDAO);
            return defaultSecurityManager;
        }

        /**
         * 注入SessionStorageEvaluator,关闭Session存储
         */
    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        return defaultSessionStorageEvaluator;
    }

    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }
}
