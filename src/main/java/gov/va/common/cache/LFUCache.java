package gov.va.common.cache;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.management.ManagementService;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * Basic naive (example) EHCacheImpl (starting point, not a finished product).
 * Usage:
 * cacheMap = (Cache)LFUCache.getInstance("myCache", timeToLiveSeconds, timeToIdleSeconds, maxElements, putNullInCache);
 * timeToLiveSeconds = maximum time between creation time and when an element expires
 * timeToIdleSeconds = maximum amount of time between accesses before an element expires
 * @param <K> key type
 * @param <V> value type
 */
public final class LFUCache<K, V> extends AbstractCache<K,V> {
    private static boolean mBeanSet;
    private final CacheManager cacheManager;
    private final String cacheName;
    
    private LFUCache(final String cacheName, final int timeToLiveInSeconds, final int timeToIdleInSeconds, final int maxElements, final boolean putNullsInCache) {

        this.timeToLiveInSeconds = timeToLiveInSeconds;
        this.timeToIdleInSeconds = timeToIdleInSeconds;
        this.maxElements = maxElements;
        this.putNullsInCache = putNullsInCache;
        // this creates a Singleton ehCache mgr, so mult instances of the wrapper are fine
        // for now we don't share cache (same name, across VMs, etc), but could
        this.cacheName = cacheName;
        this.cacheManager = CacheManager.create();
        // can only set the mBean once, or get an error
        if (!LFUCache.mBeanSet) {
            LFUCache.mBeanSet = true;
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ManagementService.registerMBeans(this.cacheManager, mBeanServer, true, true, true, true, true);
        }
        // check if cache already exists, if not, create
        if (!this.cacheManager.cacheExists(cacheName)) {
            Cache cache = new Cache(new CacheConfiguration(this.getCacheName(), maxElements)
                    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
                    .timeToLiveSeconds(timeToLiveInSeconds)
                    .timeToIdleSeconds(timeToIdleInSeconds)
                    .diskPersistent(false)
                    .overflowToDisk(false)
                    .overflowToOffHeap(false)
                    .statistics(true));
            this.cacheManager.addCache(cache);
        }
    }

    public static <K, V> LFUCache<K, V> getDefaultInstance(final String cacheName) {
        loadProperties();
        return new LFUCache<K, V>(cacheName, defaultTimeToLiveInSeconds, defaultTimeToIdleInSeconds, defaultMaxElements, defaultPutNullsInCache);
    }

    public static <K, V> LFUCache<K, V> getInstance(final String cacheName, final int timeToLiveSeconds, final int timeToIdleSeconds, final int maxElements, final boolean putNullInCache) {
        return new LFUCache<K, V>(cacheName, timeToLiveSeconds, 
                                     timeToIdleSeconds, maxElements, 
                                     putNullInCache);
    }

    @Override
    public void put(final K key, final V value) {
        if (this.isPutNullsInCache()) {
            this.getCache().put(new Element(key, value));
        } else {
            if (value != null) {
                this.getCache().put(new Element(key, value));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(final K key) {
        Element element = this.getCache().get(key);
        if (element != null) {
            return (V)element.getObjectValue();
        }
        return null;
    }
    
    @Override
    public void delete(final K key) {
        this.getCache().remove(key);
    }
    
    @Override
    public void flush() {
        this.getCache().flush();
    }

    private Ehcache getCache() {
        return this.cacheManager.getEhcache(this.getCacheName());
    }

    public String getCacheName() {
        return cacheName;
    }
    
    public void removeCache(String cacheName) {
        this.cacheManager.removeCache(cacheName);
    }
    
    public void removeAllCaches() {
        this.cacheManager.removalAll();
    }

    @Override
    public int size() {
        return this.getCache().getSize();
    }

}
