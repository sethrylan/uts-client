package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.AtomDTO;
import gov.nih.nlm.umls.uts.webservice.ConceptDTO;
import static org.hamcrest.Matchers.*;
import org.junit.*;

/**
 *
 * @author gaineys
 */
public class UTSContentServiceClientTest {
    
    private UTSContentServiceClient testClient = null;
    
    public UTSContentServiceClientTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        this.testClient = new UTSContentServiceClient();
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
    public void testGetConcept() {
        ConceptDTO concept = testClient.getConcept("C0032212");
        Assert.assertNotNull(concept);
        Assert.assertNotNull(concept.getSemanticTypes());
        Assert.assertTrue(concept.getSemanticTypes().size() > 0);
        Assert.assertEquals("Ornithorhynchus anatinus", concept.getDefaultPreferredName());
        Assert.assertTrue(concept.getAtomCount() > 0);
    }
    
        
    @Test
    public void testGetConceptCaseSensitivity() {
        ConceptDTO upperCaseConcept = testClient.getConcept("C0032212");
        Assert.assertNotNull(upperCaseConcept);
        
        ConceptDTO lowerCaseConcept = testClient.getConcept("c0032212");
        Assert.assertNull(lowerCaseConcept);
    }

    
    @Test
    public void testGetAtom() {
        AtomDTO atom = testClient.getAtom( "A18667789");
        Assert.assertNotNull(atom);
//        System.out.println("RootSource=" + atom.getRootSource());
//        System.out.println("UI=" + atom.getUi());
//        System.out.println("Handle=" + atom.getHandle());
//        System.out.println("ClassType=" + atom.getClassType());
//        System.out.println("TermType=" + atom.getTermType());
//        System.out.println("SourceUI=" + atom.getSourceUi());
//        System.out.println("AtomRelationCount=" + atom.getAtomRelationCount());
//        System.out.println("CodeRelationCount=" + atom.getCodeRelationCount());
//        System.out.println("CocCount=" + atom.getCocCount());
//        System.out.println("ConceptRelationCount=" + atom.getConceptRelationCount());
//        System.out.println("CvMemberCount=" + atom.getCvMemberCount());
//        System.out.println("DefinitionCount=" + atom.getDefinitionCount());
//        System.out.println("RelationCount=" + atom.getRelationCount());
//        System.out.println("SourceConceptRelationCount=" + atom.getSourceConceptRelationCount());
//        System.out.println("SourceDescriptorRelationCount=" + atom.getSourceDescriptorRelationCount());
//        System.out.println("SubsetMemberRelationCount=" + atom.getSubsetMemberCount());
//        System.out.println("TreePositionCount=" + atom.getTreePositionCount());
//        System.out.println("SourceConceptPreferredName=" + atom.getSourceConcept().getDefaultPreferredName());
        
        Assert.assertEquals("0000034722", atom.getSourceUi()); // ??? Kiwi Fruit???
        Assert.assertEquals("CHV", atom.getRootSource()); // "Consumer Health Vocabulary"
        Assert.assertEquals(atom.getConcept().getUi(), testClient.getConcept("C0032212").getUi());
        Assert.assertTrue(atom.getSourceConcept().getDefaultPreferredName().toLowerCase().contains("platypus"));
    }
    
//    C0032212/scud=0000009843/code=0000009843 = platypus (atom =[A18667789/CHV/PT])
//    C0018787 = heart
    
}
