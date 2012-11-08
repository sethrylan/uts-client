/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.UiLabel;
import gov.nih.nlm.umls.uts.webservice.UiLabelRootSource;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;

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
    public void testGetServiceName() {
        Assert.assertNotNull(testClient.getServiceName());
        // TODO: check that serviceName matches properties file
    }

    @Test
    public void testGetUsername() {
        Assert.assertNotNull(testClient.getUsername());
        // TODO: check that username matches properties file
    }

    @Test
    public void testGetPassword() {
        Assert.assertNotNull(testClient.getPassword());
        // TODO: check that password matches properties file
    }

    @Test
    public void testGetUmlsVersion() {
        Assert.assertNotNull(testClient.getUmlsVersion());
        // TODO: check that version matches properties file
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
        
        List<UiLabel> uiLabels = null;
        uiLabels = testClient.findConcepts(FinderSearchTarget.ATOM, FinderSearchType.WORDS,"lou gehrig disease");
        
        Assert.assertTrue(uiLabels.size() > 0);
        
        for(UiLabel uiLabel : uiLabels) {
            Assert.assertTrue(uiLabel.getLabel().toLowerCase().contains("sclerosis") || uiLabel.getLabel().toLowerCase().contains("lou"));
        }
    }
    
    @Test
    public void testFindAtoms() {
        
        List<UiLabel> uiLabels = null;
        uiLabels = testClient.findAtoms(FinderSearchTarget.ATOM, FinderSearchType.EXACT,"lou gehrig disease");
        
        Assert.assertTrue(uiLabels.size() > 0);
        
        for(UiLabel uiLabel : uiLabels) {
            Assert.assertEquals("lougehrigdisease", uiLabel.getLabel().toLowerCase().replaceAll("\\s",""));
        }
    }    

    
    @Test
    public void testFindCodes() {
        
        List<UiLabelRootSource> uiLabels = testClient.findCodes(FinderSearchTarget.ATOM, FinderSearchType.EXACT,"diabetic foot");
        
        Assert.assertTrue(uiLabels.size() > 0);
        
        for(UiLabelRootSource uiLabel : uiLabels) {
            Assert.assertNotSame("nocode", uiLabel.getUi().toLowerCase());
            Assert.assertTrue(uiLabel.getLabel().toLowerCase().contains("diabetic foot"));

        }
    }    

    
    

}
