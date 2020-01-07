package cn.ff.dionysus.common.basal.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * Redis 配置类
 *
 * @author fengfan 2020/1/7
 */

@Configuration
@EnableCaching
@AllArgsConstructor
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport {

    private final RedisConnectionFactory factory;
    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public CacheManager cacheManager(/*RedisConnectionFactory factory*/) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //redis缓存 持续一天
                .entryTtl(Duration.ofDays(1))
                .serializeKeysWith(SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(SerializationPair.fromSerializer(jackson2JsonRedisSerializer()))
                .disableCachingNullValues();
        return RedisCacheManager.builder(factory).cacheDefaults(config).transactionAware().build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(/*RedisConnectionFactory factory*/) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        RedisSerializer<String> keySerializer = RedisSerializer.string(); // 设置key序列化类，否则key前面会多了一些乱码
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setDefaultSerializer(jackson2JsonRedisSerializer());;// 设置value序列化
        template.afterPropertiesSet();
        template.setEnableTransactionSupport(true);
        //redis连接是懒加载的 第一次加载会特别慢 所以启动时手动加载连接
        template.opsForValue().get("");
        log.info("redis连接初始化完成...");
        return template;
    }

    @Bean
    static RedisSerializer<Object> jackson2JsonRedisSerializer() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.setDefaultPropertyInclusion(Include.NON_NULL);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }
}
