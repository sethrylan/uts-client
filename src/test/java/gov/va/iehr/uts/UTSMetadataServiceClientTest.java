package gov.va.iehr.uts;

import gov.nih.nlm.umls.uts.webservice.RootSourceDTO;
import java.util.List;
import junit.framework.Assert;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author gaineys
 */
public class UTSMetadataServiceClientTest {
    
    private UTSMetadataServiceClient testClient = null;

    
    public UTSMetadataServiceClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.testClient = new UTSMetadataServiceClient();
    }
    
    @After
    public void tearDown() {
        this.testClient = null;
    }

    @Test
    public void testGetAllRootSources() {
        List<RootSourceDTO> allRootSources = testClient.getAllRootSources();
        Assert.assertNotNull(allRootSources);
        Assert.assertTrue(allRootSources.size() > 0);
        
        
//        for(RootSourceDTO rs : allRootSources) {
//            System.out.println("" + rs.getAbbreviation());
//            System.out.println("" + rs.getClassType());
//            System.out.println("" + rs.getContextType());
//            System.out.println("" + rs.getExpandedForm());
//            System.out.println("" + rs.getFamily());
//            System.out.println("" + rs.getHierarchicalName());
//            System.out.println("" + rs.getPreferredName());
//            System.out.println("" + rs.getShortName());
//            System.out.println("");
//        }
//        
        
        org.junit.Assert.assertThat(allRootSources, everyItem(Matchers.<RootSourceDTO>hasProperty("abbreviation", is(not(new String())))));
        org.junit.Assert.assertThat(allRootSources, everyItem(Matchers.<RootSourceDTO>hasProperty("shortName", is(not(new String())))));
        org.junit.Assert.assertThat(allRootSources, everyItem(Matchers.<RootSourceDTO>hasProperty("classType", is("RootSource"))));

        // test that shortname includes family
        // test that SNOMEDCT, RXNORM, ICD9CM are included
    }
}
