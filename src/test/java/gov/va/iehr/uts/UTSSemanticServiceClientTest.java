package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.SemanticTypeDTO;
import gov.nih.nlm.umls.uts.webservice.SemanticTypeRelationDTO;
import java.util.List;
import org.junit.*;

/**
 *
 * @author gaineys
 */
public class UTSSemanticServiceClientTest {
    
    
    private UTSSemanticServiceClient testClient = null;

    public UTSSemanticServiceClientTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        this.testClient = new UTSSemanticServiceClient();
    }
    
    @After
    public void tearDown() {
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
    public void testGetSemanticType() {
        SemanticTypeDTO semanticType = this.testClient.getSemanticType("T129");
        Assert.assertNotNull(semanticType);
        Assert.assertEquals("imft", semanticType.getAbbreviation());
        Assert.assertTrue(semanticType.getDefinition().contains("immune"));
        Assert.assertEquals("A1.4.1.1.3.5", semanticType.getTreeNumber());
        Assert.assertEquals("Immunologic Factor", semanticType.getValue());
        Assert.assertNull(semanticType.getNonHuman()); 
    }

    @Test
    public void testGetSemanticTypeNotFound() {
        SemanticTypeDTO semanticType = this.testClient.getSemanticType("T000");
        Assert.assertNull(semanticType);
    }

    @Test
    public void testGetAllSemanticTypes() {
        List<SemanticTypeDTO> allSemanticTypes = this.testClient.getAllSemanticTypes();
        Assert.assertNotNull(allSemanticTypes);
        Assert.assertTrue(allSemanticTypes.size() > 0);
    }

    @Test
    public void testGetRelations_String() {
        List<SemanticTypeRelationDTO> relations = this.testClient.getRelations("T129");
        Assert.assertNotNull(relations);
        Assert.assertTrue(relations.size() > 0);

    }

    
    @Test
    public void testGetRelations_String_String() {
        List<SemanticTypeRelationDTO> relations = this.testClient.getRelations("T059", "T031");
        Assert.assertNotNull(relations);
        Assert.assertTrue(relations.size() > 0);
        for(SemanticTypeRelationDTO relation : relations) {
            // TODO
        }
    }
    

    @Test
    public void testGetRelations_String_String_Self_Relation() {
        List<SemanticTypeRelationDTO> semanticTypes = this.testClient.getRelations("T129", "T129");
        Assert.assertNotNull(semanticTypes);
        Assert.assertEquals(0, semanticTypes.size());
    }
}
