/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.AtomDTO;
import gov.nih.nlm.umls.uts.webservice.UtsFault_Exception;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gaineys
 */
public class UTSServiceTest {

    private UTSContentServiceClient testClient = null;
    
    @Before
    public void setUp() {
        this.testClient = new UTSContentServiceClient();
    }
    
    @After
    public void tearDown() {
        this.testClient = null;
    }
    
    @Test
    public void testDirectAccess() {
        AtomDTO atom = null;
        try {
            atom = testClient.getContentService().getAtom( testClient.getSecurityService().getProxyTicket(testClient.ticketGrantingTicket, testClient.serviceName), testClient.getUmlsVersion(), "A18667789");
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSContentServiceClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Assert.assertNotNull(atom);
    }
    
    @Test
    public void testSecurityService() {
        try {
            String ticketGrantingTicket = testClient.ticketGrantingTicket;

            String proxyTicket = testClient.getSecurityService().getProxyTicket(ticketGrantingTicket, testClient.serviceName);

            Assert.assertEquals("sethrylan", testClient.getSecurityService().validateProxyTicket(proxyTicket, testClient.serviceName));
            
            testClient.getContentService().getAtom( proxyTicket, testClient.getUmlsVersion(), "A18667789");
            
            Assert.assertNull( testClient.getSecurityService().validateProxyTicket(proxyTicket, testClient.serviceName));
            
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSContentServiceClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
}
