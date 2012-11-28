package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaineys
 */
public class UTSFinderServiceClient extends UTSClient {

    private UtsWsFinderController finderService = null;

    public UTSFinderServiceClient() {
        super();
        this.finderService = (new UtsWsFinderControllerImplService()).getUtsWsFinderControllerImplPort();
    }

    public UtsWsFinderController getFinderService() {
        return finderService;
    }

    public void setFinderService(UtsWsFinderController finderService) {
        this.finderService = finderService;
    }

    
    /**
     * Returns list of UMLS CUIs and their associated preferred names
     * Includes up to 500 results in unspecified ascending order; includes obsolete, suppressible items. Case-insensitive.
     * @param finderSearchTarget    Element on which to search; note: ATOM must be used for String searches
     * @param finderSearchType      Method of string matching
     * @param searchString          String on which to search
     * @return                      List of UiLabels
     */
    public List<UiLabel> findConcepts(FinderSearchTarget finderSearchTarget, FinderSearchType finderSearchType, String searchString) {
        Psf psf = new Psf();
        List<UiLabel> uiLabels = null;
        try {
            uiLabels = this.getFinderService().findConcepts(this.getProxyTicket(), this.getUmlsVersion(), finderSearchTarget.getValue(), searchString, finderSearchType.getValue(), psf);
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSFinderServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return uiLabels;
    }

    
    /**
     * Given a UMLS release, a searchType, and the string or code you would like to search, returns a set of atom unique identifiers (AUI) each with its corresponding atom name (Label), that meet your search criteria
     * Includes up to 500 results in unspecified ascending order; includes obsolete, suppressible items. Case-insensitive.
     * @param finderSearchTarget    Element on which to search
     * @param finderSearchType      Method of string matching
     * @param searchString          String on which to search
     * @return                      List of UiLabels
     */
    public List<UiLabel> findAtoms(FinderSearchTarget finderSearchTarget, FinderSearchType finderSearchType, String searchString) {
        Psf psf = new Psf();
        List<UiLabel> uiLabels = null;
        try {
            uiLabels = this.getFinderService().findAtoms(this.getProxyTicket(), this.getUmlsVersion(), finderSearchTarget.getValue(), searchString, finderSearchType.getValue(), psf);
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSFinderServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return uiLabels;
    }
    
    /**
     * Starting with an atom, source concept, source descriptor, or aui, returns first 500 codes, their preferred names, and the source vocabularies that provide them
     * Includes up to 500 results in unspecified ascending order; includes obsolete, suppressible items. Case-insensitive.
     * Atoms with a code of "NOCODE" or case insensitive variations are removed; Some atoms from source vocabularies are assigned "NOCODE" during source processing, and will not be useful in the context of most finder search results.
     * @param finderSearchTarget    Element on which to search
     * @param finderSearchType      Method of string matching
     * @param searchString          String on which to search
     * @return                      List of UiLabelRootSource
     */
    public List<UiLabelRootSource> findCodes(FinderSearchTarget finderSearchTarget, FinderSearchType finderSearchType, String searchString) {
        Psf psf = new Psf();
        List<UiLabelRootSource> uiLabels = null;
        try {
            uiLabels = this.getFinderService().findCodes(this.getProxyTicket(), this.getUmlsVersion(), finderSearchTarget.getValue(), searchString, finderSearchType.getValue(), psf);
        } catch (UtsFault_Exception ex) {
            Logger.getLogger(UTSFinderServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<UiLabelRootSource> filteredUiLabels = new ArrayList<UiLabelRootSource>();
        for(UiLabelRootSource uiLabel : uiLabels) {
            if(!uiLabel.getUi().equalsIgnoreCase("nocode")) {
                filteredUiLabels.add(uiLabel);
            }
        }
        return filteredUiLabels;
    }    
}
