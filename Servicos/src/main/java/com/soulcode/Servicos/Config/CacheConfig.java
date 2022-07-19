package com.soulcode.Servicos.Config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class CacheConfig {

    private final RedisSerializationContext.SerializationPair<Object> serializationPair =
            RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());

    @Bean // BEAN SERVE PARA UTILIZARMOS UMA CLASSE QUE JA EXISTE, INSTANCIAMOS ELA E DEPOIS PODEMOS ALTERÁ-LA
    public RedisCacheConfiguration cacheConfiguration (){
        return RedisCacheConfiguration.defaultCacheConfig() //default, porem vamos customizar
                .entryTtl(Duration.ofMinutes(5)) //todos caches terão 5 min padrão
                .disableCachingNullValues() //nao salva valores nulos
                .serializeValuesWith(serializationPair); //converter do redis para json
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(){
        return (builder) -> builder
                .withCacheConfiguration("clientesCache", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(30)).serializeValuesWith(serializationPair))
                .withCacheConfiguration("chamadosCache", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(10)).serializeValuesWith(serializationPair))
                .withCacheConfiguration("userCache", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(5)).serializeValuesWith(serializationPair))
                .withCacheConfiguration("authUserDetailServiceCache", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofHours(1)).serializeValuesWith(serializationPair));
    }
}
