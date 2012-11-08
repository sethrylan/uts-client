package gov.va.iehr.uts;

/**
 *
 * @author gaineys
 */
public enum FinderSearchTarget {
    
    AUI ("aui"),
    ATOM ("atom"),
    CODE ("code"),
    CONCEPT ("concept"),
    SOURCECONCEPT ("sourceConcept"),
    SOURCEDESCRIPTOR ("sourceDescriptor");
    
    private String value;

    FinderSearchTarget(String value){
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
}
