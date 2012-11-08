/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.UtsFault_Exception;
import gov.va.iehr.TestUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.junit.*;

/**
 *
 * @author gaineys
 */
public class UTSPerformanceTest {
    
    private UTSContentServiceClient testClient = null;
    
    public UTSPerformanceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        this.testClient = new UTSContentServiceClient();
    }
    
    @After
    public void tearDown() {
        this.testClient = null;
    }


    @Test
    @Ignore
    public void testTime() {
        DescriptiveStatistics clientInstStats = new DescriptiveStatistics();
        DescriptiveStatistics proxyTicketStats = new DescriptiveStatistics();
        DescriptiveStatistics getConceptStats = new DescriptiveStatistics();
        
        for(int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            testClient = new UTSContentServiceClient();
            clientInstStats.addValue(System.currentTimeMillis() - start);
            
            start = System.currentTimeMillis();
            String proxyTicket = testClient.getProxyTicket();
            proxyTicketStats.addValue(System.currentTimeMillis() - start);
            
            start = System.currentTimeMillis();
            try {
                testClient.getContentService().getConcept( proxyTicket, testClient.getUmlsVersion(), "C0032212");
            } catch (UtsFault_Exception ex) {
                Logger.getLogger(UTSPerformanceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            getConceptStats.addValue(System.currentTimeMillis() - start);
            
        }
        
        System.out.println("clientInstantiation: \n" + TestUtils.statSummary(clientInstStats));
        System.out.println("getProxyTicket: \n" + TestUtils.statSummary(proxyTicketStats));
        System.out.println("getConcept: \n" + TestUtils.statSummary(getConceptStats));
    }
    
    
    
        
}
