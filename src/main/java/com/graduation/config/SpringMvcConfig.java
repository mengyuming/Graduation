package com.graduation.config;

import com.graduation.Handler.MyAuthenticationFailHandler;
import com.graduation.Handler.MyAuthenticationProvider;
import com.graduation.Handler.MyAuthenticationSuccessHandler;
import com.graduation.bean.Course;
import com.graduation.bean.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executor;

@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter{



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/pages/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }




    @Bean
    public AuthenticationProvider myAuthenticationProvider(){
        return new MyAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MessageDigestPasswordEncoder("MD5");
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new MyAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new MyAuthenticationFailHandler();
    }

    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator(){
        KeyGenerator keyGenerator = new KeyGenerator() {

            @Override
            public Object generate(Object target, Method method, Object... params) {
                System.out.println(target.getClass());
                if(target.getClass().equals(User.class)){
                    User user = (User) target;
                    return user.getType() + ":" + user.getId();
                }
                return null;
            }
        };
        return keyGenerator;
    }

    @Bean("myCacheProperties")
    public CacheProperties cacheProperties(){
        return new CacheProperties();
    }
    @Bean("myCacheManagerCustomizers")
    public CacheManagerCustomizers cacheManagerCustomizers(ObjectProvider<List<CacheManagerCustomizer<?>>> customizers) {
        return new CacheManagerCustomizers((List)customizers.getIfAvailable());
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
