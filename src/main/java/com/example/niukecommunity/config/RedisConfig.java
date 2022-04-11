package com.example.niukecommunity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        //实例化RedisTemplate
        RedisTemplate<String,Object> template=new RedisTemplate<>();
        //通过工厂连接
        template.setConnectionFactory(factory);
        //设置普通key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        //设置普通value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        //设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        //设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());
        //触发生效
        template.afterPropertiesSet();
        return template;
    }
}
