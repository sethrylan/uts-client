package gov.va.common.cache;

import java.util.ArrayList;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;

public class LRUCache<K, T> extends gov.va.common.cache.AbstractCache<K,T> {

    private LRUMap cacheMap;

    @Override
    public void delete(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    @Override
    public void flush() {
        
    }

    protected class CachedObject {
        public long lastAccessed = System.currentTimeMillis();
        public T value;

        protected CachedObject(T value) {
            this.value = value;
        }
    }
    
    public static <K, V> LRUCache<K, V> getDefaultInstance() {
        loadProperties();
        return new LRUCache(defaultTimeToLiveInSeconds, defaultTimeToIdleInSeconds, defaultMaxElements, defaultPutNullsInCache);
    }
    
    public LRUCache(int timeToLiveInSeconds, final int timeToIdleInSeconds, int maxElements, boolean putNullsInCache) {
        
        this.timeToLiveInSeconds = timeToLiveInSeconds;
        this.timeToIdleInSeconds = timeToIdleInSeconds;
        this.maxElements = maxElements;
        this.putNullsInCache = putNullsInCache;

        cacheMap = new LRUMap(maxElements);

        if (timeToLiveInSeconds > 0 && timeToIdleInSeconds > 0) {
            Thread thread = new Thread(new Runnable() {
                        public void run() {
                            while (true) {
                                try {
                                    Thread.sleep(timeToIdleInSeconds * 1000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                cleanup();
                            }
                        }
                    });
            thread.setDaemon(true);
            thread.start();
        }
    }

    @Override
    public void put(K key, T value) {
        synchronized (cacheMap) {
            if(this.putNullsInCache) {
                cacheMap.put(key, new CachedObject(value));
            } else {
                if (value != null) {
                cacheMap.put(key, new CachedObject(value));
                }
            }
        }
    }

    @Override
    public T get(K key) {
        synchronized (cacheMap) {
            CachedObject c = (CachedObject)cacheMap.get(key);

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }


    @Override
    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    @SuppressWarnings("unchecked")
    public void cleanup() {
        long now = System.currentTimeMillis();
        ArrayList<K> keysToDelete = null;

        synchronized (cacheMap) {
            MapIterator itr = cacheMap.mapIterator();

            keysToDelete = new ArrayList<K>((cacheMap.size() / 2) + 1);
            K key = null;
            CachedObject c = null;

            while (itr.hasNext()) {
                key = (K)itr.next();
                c = (CachedObject)itr.getValue();

                if (c != null && 
                    (now > ((timeToLiveInSeconds * 1000) + c.lastAccessed))) {
                    keysToDelete.add(key);
                }
            }
        }

        for (K key: keysToDelete) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }
            Thread.yield();
        }
    }
}
