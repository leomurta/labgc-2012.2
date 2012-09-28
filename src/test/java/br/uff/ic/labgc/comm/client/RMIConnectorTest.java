/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.exception.ServerException;
import br.uff.ic.labgc.utils.URL;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cristiano
 */
public class RMIConnectorTest {
    
    public RMIConnectorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of commit method, of class RMIConnector.
     */
   //@Test
    public void testCommit() {
        System.out.println("commit");
        List<File> file = null;
        String message = "";
        RMIConnector instance = null;
        boolean expResult = false;
        boolean result = instance.commit(file, message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class RMIConnector.
     */
   // @Test
    public void testUpdate() {
        System.out.println("update");
        RMIConnector instance = null;
        List expResult = null;
        List result = instance.update();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkout method, of class RMIConnector.
     */
    //@Test
    public void testCheckout() {
        try {
            System.out.println("checkout");
            String revision = null;
            String token = null;
            RMIConnector instance = null;
            List expResult = null;
            List result = instance.checkout(revision, token);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (ServerException ex) {
            Logger.getLogger(RMIConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of diff method, of class RMIConnector.
     */
    //@Test
    public void testDiff() {
        System.out.println("diff");
        File file = null;
        String version = "";
        RMIConnector instance = null;
        String expResult = "";
        String result = instance.diff(file, version);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of log method, of class RMIConnector.
     */
    //@Test
    public void testLog() {
        System.out.println("log");
        RMIConnector instance = null;
        String expResult = "";
        String result = instance.log();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerRepository method, of class RMIConnector.
     */
    //@Test
    public void testRegisterRepository() {
        System.out.println("registerRepository");
        String repHost = "";
        String repName = "";
        RMIConnector instance = null;
        instance.registerRepository(repHost, repName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRepPath method, of class RMIConnector.
     */
    //@Test
    public void testGetRepPath() {
        System.out.println("getRepPath");
        RMIConnector instance = null;
        String expResult = "";
        String result = instance.getRepPath();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRepHost method, of class RMIConnector.
     */
    //@Test
    public void testGetRepHost() {
        System.out.println("getRepHost");
        RMIConnector instance = null;
        String expResult = "";
        String result = instance.getRepHost();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hello method, of class RMIConnector.
     */
    @Test
    public void testHello() throws Exception {
        System.out.println("hello");
        String name = "Joao da Silva";
        RMIConnector instance = new RMIConnector(new URL("http://localhost:1099/repository"));
        String expResult = "Hello, Joao da Silva!";
        String result = instance.hello(name);
        assertEquals(expResult, result);
    }
}
