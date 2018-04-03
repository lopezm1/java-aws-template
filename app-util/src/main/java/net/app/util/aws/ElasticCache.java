package net.app.util.aws;

import lombok.extern.slf4j.Slf4j;
import net.app.core.interfaces.ICacheManager;
import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;

// The CacheManager class implements the lazy caching approach to populate
// the cache and retrieve items from cache

/**
 * CacheManager for Memcahched Client.
 */
@Slf4j
public class ElasticCache implements ICacheManager {

    private static final String EC_CONFIG_ENDPOINT = "ELASTICACHE_CONFIGURATION_ENDPOINT";
    private static final String MCD_DEFAULT_TTL = "MEMCACHED_DEFAULT_TTL_VALUE";

    // Set the endpoint for the ElastiCache node cluster
    private static final String CLUSTER_CONFIG_ENDPOINT = System.getenv(EC_CONFIG_ENDPOINT);
    private static final int DEFAULT_TTL_VALUE = Integer.parseInt(System.getenv(MCD_DEFAULT_TTL));
    private static final int CLUSTER_CONFIG_ENDPOINT_PORT = 11211;

    private static ElasticCache elasticCache;
    private static MemcachedClient memcachedClient;

    private ElasticCache() {
        try {
            memcachedClient = new MemcachedClient(new InetSocketAddress(CLUSTER_CONFIG_ENDPOINT, CLUSTER_CONFIG_ENDPOINT_PORT));
        } catch(IOException ioe) {
            log.error("Error constructing MemcachedClient " + ioe.getMessage());
        }
    }

    public static ElasticCache getInstance(){
        if(elasticCache == null) {
            elasticCache = new ElasticCache();
        }
        return elasticCache;
    }

    /**
     * Retrieve value from cache for the given- key
     *
     * @param key   Key of the value to get
     * @return      Value of the key
     */
    @Override
    public Object getCacheItem(String key) {
        Object valueFromCache = null;
        valueFromCache = memcachedClient.get(key);
        return valueFromCache;
    }

    /**
     * Store the given key-value pair in cache
     *
     * @param key     Key of the value to set
     * @param value   The value of the key to set
     */
    @Override
    public void setCacheItem(String key, Object value) {
        memcachedClient.set(key, DEFAULT_TTL_VALUE, value);
    }

    /**
     * Store the given key-value pair in cache
     *
     * @param key     Key of the value to set
     * @param TTL     Time-To-Live value of cached value
     * @param value   The value of the key to set
     */
    @Override
    public void setCacheItem(String key, int TTL, Object value) { memcachedClient.set(key, TTL, value); }
}

