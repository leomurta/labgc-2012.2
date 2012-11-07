/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.core.IObserver;
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
 * @author victor
 */
public class WorkspaceTest {
    
    public WorkspaceTest() {
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
     * Test of remove method, of class Workspace.
     */
    @Test
    public void testRemove() throws Exception {
        System.out.println("remove");
        File file = null;
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.remove(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of move method, of class Workspace.
     */
    @Test
    public void testMove() throws Exception {
        System.out.println("move");
        File file = null;
        String dest = "";
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.move(file, dest);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copyDir method, of class Workspace.
     */
    @Test
    public void testCopyDir() throws Exception {
        System.out.println("copyDir");
        File src = null;
        File dest = null;
        Workspace instance = null;
        instance.copyDir(src, dest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copy method, of class Workspace.
     */
    @Test
    public void testCopy() throws Exception {
        System.out.println("copy");
        File origem = null;
        File destino = null;
        boolean overwrite = false;
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.copy(origem, destino, overwrite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mkdir method, of class Workspace.
     */
    @Test
    public void testMkdir() throws Exception {
        System.out.println("mkdir");
        String name = "";
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.mkdir(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDir method, of class Workspace.
     */
    @Test
    public void testDeleteDir() {
        System.out.println("deleteDir");
        File dir = null;
        boolean expResult = false;
        boolean result = Workspace.deleteDir(dir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Workspace.
     */
    @Test
    public void testAdd() throws Exception {
        System.out.println("add");
        File file = null;
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.add(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of revert method, of class Workspace.
     */
    @Test
    public void testRevert_String() throws Exception {
        System.out.println("revert");
        String file = "";
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.revert(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of revert method, of class Workspace.
     */
    @Test
    public void testRevert_0args() throws Exception {
        System.out.println("revert");
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.revert();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of status method, of class Workspace.
     */
    /*@Test
    public void testStatus() {
        System.out.println("status");
        Workspace instance = null;
        String expResult = "";
        String result = instance.status();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of release method, of class Workspace.
     */
    @Test
    public void testRelease() {
        System.out.println("release");
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.release();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resolve method, of class Workspace.
     */
    @Test
    public void testResolve() {
        System.out.println("resolve");
        File file = null;
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.resolve(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createWorkspace method, of class Workspace.
     */
    @Test
    public void testCreateWorkspace() throws Exception {
        System.out.println("createWorkspace");
        String hostname = "";
        String repository = "";
        VersionedItem items = null;
        Workspace instance = null;
        instance.createWorkspace(hostname, repository, items);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of canCreate method, of class Workspace.
     */
    @Test
    public void testCanCreate() {
        System.out.println("canCreate");
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.canCreate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storeLocalData method, of class Workspace.
     */
    @Test
    public void testStoreLocalData() {
        System.out.println("storeLocalData");
        VersionedItem items = null;
        Workspace instance = null;
        instance.storeLocalData(items);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setParam method, of class Workspace.
     */
    @Test
    public void testSetParam() throws Exception {
        System.out.println("setParam");
        String key = "";
        String value = "";
        Workspace instance = null;
        instance.setParam(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParam method, of class Workspace.
     */
    @Test
    public void testGetParam() throws Exception {
        System.out.println("getParam");
        String key = "";
        Workspace instance = null;
        String expResult = "";
        String result = instance.getParam(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHostname method, of class Workspace.
     */
    @Test
    public void testGetHostname() throws Exception {
        System.out.println("getHostname");
        Workspace instance = null;
        String expResult = "";
        String result = instance.getHostname();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRepository method, of class Workspace.
     */
    @Test
    public void testGetRepository() throws Exception {
        System.out.println("getRepository");
        Workspace instance = null;
        String expResult = "";
        String result = instance.getRepository();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRevision method, of class Workspace.
     */
    @Test
    public void testGetRevision() throws Exception {
        System.out.println("getRevision");
        Workspace instance = null;
        String expResult = "";
        String result = instance.getRevision();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isWorkspace method, of class Workspace.
     */
    @Test
    public void testIsWorkspace() {
        System.out.println("isWorkspace");
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.isWorkspace();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerInterest method, of class Workspace.
     */
    @Test
    public void testRegisterInterest() {
        System.out.println("registerInterest");
        IObserver obs = null;
        Workspace instance = null;
        instance.registerInterest(obs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
