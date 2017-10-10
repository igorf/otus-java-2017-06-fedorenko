package com.otus.hw16.serverapp.config;

import net.sf.ehcache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return CacheManager.newInstance();
    }
}