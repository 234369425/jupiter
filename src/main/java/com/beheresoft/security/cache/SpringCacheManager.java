package com.beheresoft.security.cache;

import net.sf.ehcache.Ehcache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Repository;

import org.springframework.cache.Cache.ValueWrapper;

import java.util.*;

/**
 * @author Aladi
 */
@Repository
public class SpringCacheManager implements CacheManager {

    private org.springframework.cache.CacheManager cacheManager;

    public SpringCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        org.springframework.cache.Cache cache = cacheManager.getCache(s);
        return new SpringCache<>(cache);
    }

    /**
     * shiro cache spring implement
     *
     * @param <K> key
     * @param <V> value
     */
    static class SpringCache<K, V> implements Cache<K, V> {

        private org.springframework.cache.Cache cache;

        public SpringCache(org.springframework.cache.Cache cache) {
            this.cache = cache;
        }

        private V getValue(ValueWrapper v) {
            return v == null ? null : (V) v.get();
        }

        @Override
        public V get(K key) throws CacheException {
            return getValue(cache.get(key));
        }

        @Override
        public V put(K key, V value) throws CacheException {
            V oldValue = getValue(cache.get(key));
            cache.put(key, value);
            return oldValue;
        }

        @Override
        public V remove(K key) throws CacheException {
            V oldValue = getValue(cache.get(key));
            cache.evict(key);
            return oldValue;
        }

        @Override
        public void clear() throws CacheException {
            cache.clear();
        }

        @Override
        public int size() {
            if (cache.getNativeCache() instanceof Ehcache) {
                Ehcache ehcache = (Ehcache) cache.getNativeCache();
                return ehcache.getSize();
            }
            throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
        }

        @Override
        public Set keys() {
            if (cache.getNativeCache() instanceof Ehcache) {
                Ehcache ehcache = (Ehcache) cache.getNativeCache();
                return new HashSet(ehcache.getKeys());
            }
            throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
        }

        @Override
        public Collection values() {
            if (cache.getNativeCache() instanceof Ehcache) {
                Ehcache ehcache = (Ehcache) cache.getNativeCache();
                List<K> keys = ehcache.getKeys();
                if (!CollectionUtils.isEmpty(keys)) {
                    List values = new ArrayList(keys.size());
                    for (K key : keys) {
                        V value = get(key);
                        if (value != null) {
                            values.add(value);
                        }
                    }
                    return Collections.unmodifiableList(values);
                } else {
                    return Collections.emptyList();
                }
            }
            throw new UnsupportedOperationException("invoke spring cache abstract values method not supported");
        }
    }

}
