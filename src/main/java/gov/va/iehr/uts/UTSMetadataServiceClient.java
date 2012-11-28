package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaineys
 */
public class UTSMetadataServiceClient extends UTSClient {

    
    private UtsWsMetadataController metadataService = null;

    
    public UTSMetadataServiceClient() {
        super();
        this.metadataService = (new UtsWsMetadataControllerImplService()).getUtsWsMetadataControllerImplPort();
    }
    
    
    public UtsWsMetadataController getMetadataService() {
        return this.metadataService;
    }

    public void setMetadataService(UtsWsMetadataController metadataService) {
        this.metadataService = metadataService;
    }

    /**
     * Wrapper to UtsWsMetadataController.getAllRootSources
     * @see  https://uts.nlm.nih.gov/doc/ws/javadocs/gov/nih/nlm/umls/uts/webservice/UtsWsMetadataController.html#getAllRootSources(java.lang.String, java.lang.String)
     */
    public List<RootSourceDTO> getAllRootSources(){
        
        List<RootSourceDTO> result = null;
        try {
            result =  getMetadataService().getAllRootSources(getProxyTicket(), getUmlsVersion());
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    
    
}
