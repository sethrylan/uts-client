package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.*;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaineys
 */
public abstract class UTSClient {

    UtsWsSecurityController securityService = null;
    String ticketGrantingTicket = null;
    String serviceName = null;
    String username = null;
    String password = null;
    String umlsVersion = null;
    
    public UTSClient() {
        this.loadProperties();
        this.securityService = (new UtsWsSecurityControllerImplService()).getUtsWsSecurityControllerImplPort();
        try {
            this.ticketGrantingTicket = securityService.getProxyGrantTicket(username, password);
            //get the Proxy Grant Ticket - this is good for 8 hours and is needed to generate single use tickets.
            //use the ticketGrantingTicket to generate a Single Use Ticket with each call
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSClient.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(UTSClient.class.getName()).log(Level.SEVERE, null, "Please check your username/password for UTS.");
        }
    }
    
    private void loadProperties(){
        Properties props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("uts.properties"));
        } catch (IOException ex) {
            Logger.getLogger(UTSClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.password = props.getProperty("password");
        this.username = props.getProperty("username");
        this.serviceName = props.getProperty("serviceName");        
        this.umlsVersion = props.getProperty("umlsVersion");
    }
    
    /**
     * Returns a single-use ticket
     * @return String representation of a single-use ticket
     */
    protected String getProxyTicket() {
        try {
            return getSecurityService().getProxyTicket(ticketGrantingTicket, serviceName);
        } catch (Exception e) {
            return "";
        }
    }    
    
    public UtsWsSecurityController getSecurityService() {
        return securityService;
    }

    public String getUsername() {
        return username;
    }

    public String getUmlsVersion() {
        return umlsVersion;
    }

}
