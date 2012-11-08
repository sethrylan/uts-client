/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaineys
 */
public class UTSSemanticServiceClient extends UTSClient {
    
    private UtsWsSemanticNetworkController semanticService = null;

    public UTSSemanticServiceClient() {
        super();
        this.semanticService = (new UtsWsSemanticNetworkControllerImplService()).getUtsWsSemanticNetworkControllerImplPort();
    }

    public UtsWsSemanticNetworkController getSemanticService() {
        return semanticService;
    }

    public void setSemanticService(UtsWsSemanticNetworkController semanticService) {
        this.semanticService = semanticService;
    }
    
    /**
     * Takes a Semantic Type Unique Identifier (TUI) as an argument, and returns information such as the 
     * Semantic Type's definition, Tree Number, number of children, etc.
     * @param tui Semantic Type Unique Identifier   
     * @return Semantic Type definition object or null if none found
     */
    public SemanticTypeDTO getSemanticType(String tui) {
        SemanticTypeDTO result = null;
        try {
            result = getSemanticService().getSemanticType(getProxyTicket(), getUmlsVersion(), tui);
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSSemanticServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Returns all Semantic Types for a given UMLS release; ~130 in the 2012AA UMLS
     * @return List of all Semantic Types in the configured UMLS release
     */
    public List<SemanticTypeDTO> getAllSemanticTypes() {
        List<SemanticTypeDTO> result = null;
        try {
            result = getSemanticService().getAllSemanticTypes(getProxyTicket(), getUmlsVersion());
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSSemanticServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Given a semantic type unique identifier (TUI), this call returns the semantic type relations 
     * that relate the given semantic type with other semantic types; e.g., "manifestation_of," as in "Pathologic Function manifestation_of Injury or Poisoning."
     * @param tui Semantic Type Unique Identifier 
     * @return List of semantic relations
     */
    public List<SemanticTypeRelationDTO> getRelations(String tui) {
        List<SemanticTypeRelationDTO> result = null;
        try {
            result = getSemanticService().getSemanticTypeRelations(getProxyTicket(), getUmlsVersion(),tui);
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSSemanticServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Returns all the relations between a pair of Semantic Types. 
     * @param tui Semantic Type Unique Identifier 
     * @param tui2 Another Semantic Type Unique Identifier 
     * @return List of semantic relations
     */
    public List<SemanticTypeRelationDTO> getRelations(String tui, String tui2) {
        List<SemanticTypeRelationDTO> result = null;
        try {
            result = getSemanticService().getSemanticTypeRelationsForPair(getProxyTicket(), getUmlsVersion(),tui, tui2);
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSSemanticServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
