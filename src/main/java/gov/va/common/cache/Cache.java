package gov.va.common.cache;

public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);

    void delete(K key);

    void flush();

    int size();

    long getTimeToLiveInSeconds();

    long getTimeToIdleInSeconds();
    
    long getMaxElements();
    
    boolean isPutNullsInCache();
        
}