package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaineys
 */
public class UTSContentServiceClient extends UTSClient {

    
    private UtsWsContentController contentService = null;

    
    public UTSContentServiceClient() {
        super();
        this.contentService = (new UtsWsContentControllerImplService()).getUtsWsContentControllerImplPort();
    }
    
    
    public UtsWsContentController getContentService() {
        return this.contentService;
    }

    public void setContentService(UtsWsContentController contentService) {
        this.contentService = contentService;
    }


    /**
     * Wrapper to UtsWsContentController.getConcept
     * @param cui
     * @return 
     * @see https://uts.nlm.nih.gov/doc/ws/javadocs/gov/nih/nlm/umls/uts/webservice/UtsWsContentController.html#getConcept(java.lang.String, java.lang.String, java.lang.String)
     */
    public ConceptDTO getConcept(String cui){
        
        ConceptDTO result = null;
        try {
            result =  getContentService().getConcept(getProxyTicket(), getUmlsVersion(), cui);
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    /**
     * Wrapper to UtsWsContentController.getConcept
     * @param aui
     * @return 
     * @see https://uts.nlm.nih.gov/doc/ws/javadocs/gov/nih/nlm/umls/uts/webservice/UtsWsContentController.html#getAtom(java.lang.String, java.lang.String, java.lang.String)
     */
    public AtomDTO getAtom(String aui){
        
        AtomDTO result = null;
        try {
            result =  getContentService().getAtom(getProxyTicket(), getUmlsVersion(), aui);
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }    
    
}
