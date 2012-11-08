package gov.va.iehr.uts;

/**
 *
 * @author gaineys
 */
public enum FinderSearchType {
    
    EXACT ("exact"),
    APPROXIMATE ("approximate"),
    LEFTTRUNCATION ("leftTruncation"),
    RIGHTTRUNCATION ("rightTruncation"),
    WORDS ("words"),
    NORMALIZEDWORDS ("normalizedWords"),
    NORMALIZEDSTRING ("normalizedString");
    
    private String value;

    FinderSearchType(String value){
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }

    
}
