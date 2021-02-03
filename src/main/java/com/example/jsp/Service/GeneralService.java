package com.example.jsp.Service;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class GeneralService {

    protected CacheManager cacheManager;

    public GeneralService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void evictAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }
}
