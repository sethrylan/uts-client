package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.UiLabel;
import gov.nih.nlm.umls.uts.webservice.UiLabelRootSource;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author gaineys
 */
public class UTSFinderServiceClientTest {
    
    private UTSFinderServiceClient testClient = null;
    
    public UTSFinderServiceClientTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        this.testClient = new UTSFinderServiceClient();
    }
    
    @After
    public void tearDown() {
        this.testClient = null;
    }

    @Test
    public void testGetProxyTicket(){
        Assert.assertNotNull(testClient.getProxyTicket());
    }

    @Test
    public void testFindConcepts() {
        
        List<String> lousCuis = new ArrayList<String>() {{
           add("C0002736");
           add("C2317803");
           add("C2317805");
           add("C1862939");
           add("C1862940");
           add("C2317804");
           add("C2956919");           
        }};
        
        List<UiLabel> uiLabels = testClient.findConcepts(FinderSearchTarget.ATOM, FinderSearchType.WORDS,"lou gehrig disease");
        Assert.assertTrue(uiLabels.size() > 0);
//        org.junit.Assert.assertThat(uiLabels, everyItem(Matchers.<RootSourceDTO>hasProperty("label", either(containsString("sclerosis")).or(containsString("lou")))));
        for(UiLabel uiLabel : uiLabels) {
//            System.out.println(uiLabel.getUi() + "  |  " + uiLabel.getLabel());
            Assert.assertTrue(uiLabel.getLabel().toLowerCase().contains("sclerosis") || uiLabel.getLabel().toLowerCase().contains("lou"));
            Assert.assertTrue("[C]UI format should be C#######.", uiLabel.getUi().matches("C\\d{7}"));

        }
                
        List<UiLabel> uiLabelsUpperCase = testClient.findConcepts(FinderSearchTarget.ATOM, FinderSearchType.WORDS,"lou gehrig disease".toUpperCase());
        Assert.assertTrue(uiLabelsUpperCase.size() > 0);
        Assert.assertEquals(uiLabels.size(), uiLabelsUpperCase.size() );
//        Assert.assertTrue(uiLabels.containsAll(uiLabelsUpperCase));    Objects have different reference, but are the same
        for(UiLabel uiLabel : uiLabelsUpperCase) {
            Assert.assertTrue(uiLabel.getLabel().toLowerCase().contains("sclerosis") || uiLabel.getLabel().toLowerCase().contains("lou"));
            Assert.assertTrue("[C]UI format should be C#######.", uiLabel.getUi().matches("C\\d{7}"));
        }
                

    }
    
    @Test
    public void testFindAtoms() {
        
        List<UiLabel> uiLabels = testClient.findAtoms(FinderSearchTarget.ATOM, FinderSearchType.EXACT,"lou gehrig disease");
        
        Assert.assertTrue(uiLabels.size() > 0);
        
        for(UiLabel uiLabel : uiLabels) {
            Assert.assertEquals("lougehrigdisease", uiLabel.getLabel().toLowerCase().replaceAll("\\s",""));
            Assert.assertTrue("AUI format should be A######...", uiLabel.getUi().matches("A\\d{7,10}"));
        }
    }    

    
    
    @Test
    public void testFindAtomsNormalized() {
        
        List<UiLabel> uiLabels = testClient.findAtoms(FinderSearchTarget.ATOM, FinderSearchType.NORMALIZEDSTRING,"lou gehrig disease");
        
        Assert.assertTrue(uiLabels.size() > 0);
        
        for(UiLabel uiLabel : uiLabels) {
            // unlike EXACT, NORMALIZEDSTRING will match orthographic and stylistic variations
            Assert.assertTrue(uiLabel.getLabel().toLowerCase().contains("lou") && uiLabel.getLabel().toLowerCase().contains("gehrig"));
            Assert.assertTrue("AUI format should be A######...", uiLabel.getUi().matches("A\\d{7,10}"));
        }
    }    
    
    
    
    @Test
    public void testFindCodes() {
        
        List<UiLabelRootSource> uiLabels = testClient.findCodes(FinderSearchTarget.ATOM, FinderSearchType.EXACT,"lou gehrig disease");
        
        Assert.assertTrue(uiLabels.size() > 0);
        for(UiLabelRootSource uiLabel : uiLabels) {
            Assert.assertNotSame("nocode", uiLabel.getUi().toLowerCase());
            // codes UIs can be pretty much anything
            Assert.assertTrue(uiLabel.getLabel().toLowerCase().contains("sclerosis") || uiLabel.getLabel().toLowerCase().contains("lou"));
        }
    }    

    
    

}
