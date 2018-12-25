package com.graduation.config;

import com.graduation.bean.Course;
import com.graduation.bean.Index;
import com.graduation.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

    @Autowired
    CacheProperties myCacheProperties;
    @Autowired
    CacheManagerCustomizers myCacheManagerCustomizers;





    @Bean("userredisTemplate")
    public RedisTemplate<Object, User> userredisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws Exception {

        RedisTemplate<Object, User> usertemplate = new RedisTemplate<>();
        usertemplate.setConnectionFactory(redisConnectionFactory);
        //设置序列化机制
        Jackson2JsonRedisSerializer<User> serializer =
                new Jackson2JsonRedisSerializer<User>(User.class);
        usertemplate.setDefaultSerializer(serializer);
        return usertemplate;
    }
    //同时，还得注册改变redisCacheManager
    @Bean("userRedisCacheManager")
    public RedisCacheManager userCacheManager(RedisTemplate<Object, User> userredisTemplate) {
        RedisCacheManager userRedisCacheManager = new RedisCacheManager(userredisTemplate);
        //默认将缓存的name作为key的前缀
        userRedisCacheManager.setUsePrefix(true);
        return userRedisCacheManager;
    }


    @Bean("courseredisTemplate")
    public RedisTemplate<Object, Course> courseredisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws Exception {

        RedisTemplate<Object, Course> coursetemplate = new RedisTemplate<>();
        coursetemplate.setConnectionFactory(redisConnectionFactory);
        //设置序列化机制
        Jackson2JsonRedisSerializer<Course> serializer =
                new Jackson2JsonRedisSerializer<Course>(Course.class);
        coursetemplate.setDefaultSerializer(serializer);
        return coursetemplate;
    }
    //同时，还得注册改变redisCacheManager
    @Bean("courseRedisCacheManager")
    public RedisCacheManager courseCacheManager(RedisTemplate<Object, Course> courseredisTemplate) {
        RedisCacheManager courseRedisCacheManager = new RedisCacheManager(courseredisTemplate);
        //默认将缓存的name作为key的前缀
        courseRedisCacheManager.setUsePrefix(true);
        return courseRedisCacheManager;
    }


    @Bean("indexredisTemplate")
    public RedisTemplate<Object, Index> indexredisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws Exception {

        RedisTemplate<Object, Index> indexredisTemplate = new RedisTemplate<>();
        indexredisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置序列化机制
        Jackson2JsonRedisSerializer<Index> serializer =
                new Jackson2JsonRedisSerializer<Index>(Index.class);
        indexredisTemplate.setDefaultSerializer(serializer);
        return indexredisTemplate;
    }
    //同时，还得注册改变redisCacheManager
    @Bean("indexRedisCacheManager")
    public RedisCacheManager indexCacheManager(RedisTemplate<Object, Index> indexredisTemplate) {
        RedisCacheManager indexRedisCacheManager = new RedisCacheManager(indexredisTemplate);
        //默认将缓存的name作为key的前缀
        indexRedisCacheManager.setUsePrefix(true);
        return indexRedisCacheManager;
    }

    //注入springboot默认的cacheManager
    @Primary
    @Bean("cacheManager")
    public RedisCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        List<String> cacheNames = this.myCacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            cacheManager.setCacheNames(cacheNames);
        }

        return (RedisCacheManager)this.myCacheManagerCustomizers.customize(cacheManager);
    }
}
