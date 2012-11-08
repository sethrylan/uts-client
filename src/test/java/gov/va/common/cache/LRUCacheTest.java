/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.common.cache;

import org.junit.*;

/**
 *
 * @author gaineys
 */
public class LRUCacheTest extends CacheTest<LRUCache> {
    
    public LRUCacheTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        testCache = LRUCache.getDefaultInstance();
    }
    
    @After
    public void tearDown() {
        testCache = null;
    }

    @Test
    public void testSize() {
    }

    @Test
    public void testCleanup() {
    }
}
