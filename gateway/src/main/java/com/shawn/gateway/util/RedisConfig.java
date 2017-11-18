package com.shawn.gateway.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
@EnableConfigurationProperties(Redis.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private Redis redis;

    @Bean("redisConnectionFactory")
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redis.getHost());
        factory.setDatabase(StringUtils.isNotBlank(redis.getDatabase()) ? Integer.valueOf(redis.getDatabase()) : 0);
        factory.setPort(redis.getPort());
        factory.setTimeout(redis.getTimeout()); // 设置连接超时时间
        return factory;
    }

    @Bean("redisTemplate")
    public RedisTemplate redisTemplate(@Qualifier("redisConnectionFactory")
                                               JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        setSerializer(redisTemplate); // 设置序列化工具，这样ReportBean不需要实现Serializable接口
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
