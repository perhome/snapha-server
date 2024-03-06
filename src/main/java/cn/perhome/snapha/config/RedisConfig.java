package cn.perhome.snapha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 配置自定义redisTemplate
     *
     * @return
     */
    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 设置值（value）的序列化采用Jackson2JsonRedisSerializer。
        //  redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    RedisMessageListenerContainer redisContainer(final RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        return container;
    }

}

