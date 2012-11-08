/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.common.cache;

import gov.va.iehr.uts.UTSClient;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaineys
 */
public class AbstractCache<K, V> implements gov.va.common.cache.Cache<K, V> {

    
    protected static int SECONDS_PER_DAY = 86400;
    protected static int defaultTimeToLiveInSeconds;
    protected static int defaultTimeToIdleInSeconds;
    protected static int defaultMaxElements;
    protected static boolean defaultPutNullsInCache;
    
    protected int timeToLiveInSeconds;
    protected int timeToIdleInSeconds;
    protected int maxElements;
    protected boolean putNullsInCache;

    public AbstractCache() {
    }
    
    protected static void loadProperties(){
        Properties props = new Properties();
        try {
            props.load(AbstractCache.class.getClassLoader().getResourceAsStream("cache.properties"));
        } catch (IOException ex) {
            Logger.getLogger(UTSClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        defaultTimeToLiveInSeconds = Integer.valueOf(props.getProperty("default.timeToLiveInSeconds"));
        defaultTimeToIdleInSeconds = Integer.valueOf(props.getProperty("default.timeToIdleInSeconds"));
        defaultMaxElements = Integer.valueOf(props.getProperty("default.maxElements"));
        defaultPutNullsInCache = Boolean.valueOf(props.getProperty("default.putNullsInCache"));
    }

    @Override
    public void put(K key, V value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public V get(K key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getSECONDS_PER_DAY() {
        return SECONDS_PER_DAY;
    }

    @Override
    public long getTimeToLiveInSeconds() {
        return timeToLiveInSeconds;
    }

    @Override
    public long getTimeToIdleInSeconds() {
        return timeToIdleInSeconds;
    }

    @Override
    public long getMaxElements() {
        return maxElements;
    }

    @Override
    public boolean isPutNullsInCache() {
        return putNullsInCache;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
