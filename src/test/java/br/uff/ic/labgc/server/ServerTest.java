/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import br.uff.ic.labgc.core.VersionedItem;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jokerfvd
 */
public class ServerTest {
    
    public ServerTest() {
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
     * Test of commit method, of class Server.
     */
    //@Test
    public void testCommit() throws Exception {
        System.out.println("commit");
        VersionedItem file = null;
        String token = "";
        Server instance = null;
        String expResult = "";
        //String result = instance.commit(file, token);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Server.
     */
    //@Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        String clientRevision = "";
        String revision = "";
        String token = "";
        Server instance = null;
        VersionedItem expResult = null;
        VersionedItem result = instance.update(clientRevision, revision, token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkout method, of class Server.
     */
    //@Test
    public void testCheckout() throws Exception {
        System.out.println("checkout");
        String revision = "";
        String token = "";
        Server instance = null;
        VersionedItem expResult = null;
        VersionedItem result = instance.checkout(revision, token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of diff method, of class Server.
     */
    //@Test
    public void testDiff() throws Exception {
        System.out.println("diff");
        VersionedItem file = null;
        String version = "";
        Server instance = null;
        String expResult = "";
        //String result = instance.diff(file, version);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of log method, of class Server.
     */
    //@Test
    public void testLog() throws Exception {
        System.out.println("log");
        Server instance = null;
        String expResult = "";
        //String result = instance.log();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class Server.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String user = "username1";
        String pwd = "password1";
        String repository = "projeto1";
        Server instance = new Server("localhost");
        String expResult = "nvfdovhfdoivbiofdvf";
        String result = instance.login(user, pwd, repository);
        assertEquals(expResult, result);
    }

    /**
     * Test of getItemContent method, of class Server.
     */
    //@Test
    public void testGetItemContent() throws Exception {
        System.out.println("getItemContent");
        String hash = "";
        Server instance = null;
        byte[] expResult = null;
        byte[] result = instance.getItemContent(hash);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBytesFromFile method, of class Server.
     */
    //@Test
    public void testGetBytesFromFile() throws Exception {
        System.out.println("getBytesFromFile");
        File file = null;
        byte[] expResult = null;
        byte[] result = Server.getBytesFromFile(file);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
