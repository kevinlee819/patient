package com.myzy.patient.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Parameter;
import java.time.Duration;

/**
 * 配置redis做为缓存配置
 * Spring内置的三大注解缓存是：
 * 1、Cacheable：缓存
 * 2、CacheEvict：删除缓存
 * 3、CachePut：更新缓存
 * <p>
 * 测试类TestCacheService
 *
 * @author leekejin
 * @date 2020-07-03 9:17
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    /**
     * 缓存Key的生成规则:前缀+类名+方法名+所有参数
     */
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(":");
            sb.append(target.getClass().getSimpleName());
            sb.append(".");
            sb.append(method.getName());
            sb.append(":");
            Parameter[] parameters = method.getParameters();
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    sb.append(parameters[i].getName());
                    sb.append("-");
                    sb.append(params[i]);
                    sb.append(".");
                }
            }
            return sb;
        };
    }

    /**
     * 缓存管理器
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 缓存基础配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 缓存超时时间（单位为分钟，Duration.ofSeconds修改为秒）
                .entryTtl(Duration.ofMinutes(60))
                // Json序列化，redis保存需要把对象转成字符串
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()))
                // 缓存Key的前缀，可以不加
                .computePrefixWith(cacheName -> "cache:" + cacheName)
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置value的序列化规则和 key的序列化规则
        Jackson2JsonRedisSerializer serializer = serializer();
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private Jackson2JsonRedisSerializer serializer(){
        // Redis值序列化使用 Jackson2JsonRedisSerializer（也可以使用GenericJackson2JsonRedisSerializer）
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

}
