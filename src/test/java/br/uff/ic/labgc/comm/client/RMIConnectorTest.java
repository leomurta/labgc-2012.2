/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.ServerException;
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
        try {
            System.out.println("commit");
            VersionedItem item = null;
            String token = "";
            String message = "";
            RMIConnector instance = null;
            String expResult = null;
            String result = instance.commit(item, message,token);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (ApplicationException ex) {
            Logger.getLogger(RMIConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of update method, of class RMIConnector.
     */
    // @Test
    public void testUpdate() {
        try {
            System.out.println("update");
            String revision = null;
            String token = null;
            RMIConnector instance = null;
            VersionedItem expResult = null;
            VersionedItem result = instance.update(revision, token);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (ApplicationException ex) {
            Logger.getLogger(RMIConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            VersionedItem expResult = null;
            VersionedItem result = instance.checkout(revision, token);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (ApplicationException ex) {
            Logger.getLogger(RMIConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of diff method, of class RMIConnector.
     */
    //@Test
//    public void testDiff() {
//        try {
//            System.out.println("diff");
//            VersionedItem item = null;
//            String version = "";
//            RMIConnector instance = null;
//            String expResult = "";
//            String result = instance.diff(item, version);
//            assertEquals(expResult, result);
//            // TODO review the generated test code and remove the default call to fail.
//            fail("The test case is a prototype.");
//        } catch (ApplicationException ex) {
//            Logger.getLogger(RMIConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    /**
     * Test of log method, of class RMIConnector.
     */
    //@Test
    public void testLog() {
        try {
            System.out.println("log");
            RMIConnector instance = null;
            String token = "";
            String expResult = "";
            List<VersionedItem> result = instance.log( token);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (ApplicationException ex) {
            Logger.getLogger(RMIConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        RMIConnector instance = new RMIConnector("localhost");
        String expResult = "Hello, Joao da Silva!";
        String result = instance.hello(name);
        assertEquals(expResult, result);
    }
}
