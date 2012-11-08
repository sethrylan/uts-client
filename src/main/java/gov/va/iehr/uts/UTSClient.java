/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

    private UtsWsSecurityController securityService = null;
    private String ticketGrantingTicket = null;
    private String serviceName = null;
    private String username = null;
    private String password = null;
    private String umlsVersion = null;
    
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
        
        this.setPassword(props.getProperty("password"));
        this.setUsername(props.getProperty("username"));
        this.setServiceName(props.getProperty("serviceName"));        
        this.setUmlsVersion(props.getProperty("umlsVersion"));
    }
    
    /**
     * Returns a single-use ticket
     * @return String representation of a single-use ticket
     */
    protected String getProxyTicket() {
        try {
            return getSecurityService().getProxyTicket(getTicketGrantingTicket(), getServiceName());
        } catch (Exception e) {
            return "";
        }
    }    
    
    public UtsWsSecurityController getSecurityService() {
        return securityService;
    }

    public void setSecurityService(UtsWsSecurityController securityService) {
        this.securityService = securityService;
    }

    public String getTicketGrantingTicket() {
        return ticketGrantingTicket;
    }

    public void setTicketGrantingTicket(String ticketGrantingTicket) {
        this.ticketGrantingTicket = ticketGrantingTicket;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUmlsVersion() {
        return umlsVersion;
    }

    public void setUmlsVersion(String umlsVersion) {
        this.umlsVersion = umlsVersion;
    }
}
