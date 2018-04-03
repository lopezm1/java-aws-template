package net.app.core.interfaces;

/**
 * Used by Memcached implmentations.
 */
public interface ICacheManager {

    Object getCacheItem(String key);

    void setCacheItem(String key, Object value);

    void setCacheItem(String key, int TTL, Object value);
}
