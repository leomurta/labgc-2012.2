/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.core.IObserver;
import br.uff.ic.labgc.core.VersionedItem;
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
     * Test of setParam method, of class Workspace.
     */
    //@Test
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
    //@Test
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
     * Test of getHost method, of class Workspace.
     */
    //@Test
    public void testGetHost() throws Exception {
        System.out.println("getHost");
        Workspace instance = null;
        String expResult = "";
        String result = instance.getHost();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProject method, of class Workspace.
     */
    //@Test
    public void testGetProject() throws Exception {
        System.out.println("getProject");
        Workspace instance = null;
        String expResult = "";
        String result = instance.getProject();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRevision method, of class Workspace.
     */
    //@Test
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
     * Test of setRevision method, of class Workspace.
     */
    //@Test
    public void testSetRevision() throws Exception {
        System.out.println("setRevision");
        String revision = "";
        Workspace instance = null;
        instance.setRevision(revision);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of canCreate method, of class Workspace.
     */
    //@Test
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
     * Test of isWorkspace method, of class Workspace.
     */
    //@Test
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
     * Test of createWorkspace method, of class Workspace.
     */
    //@Test
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
     * Test of revert method, of class Workspace.
     */
    //@Test
    public void testRevert_String() throws Exception {
        System.out.println("revert");
        String fileOrDir = "";
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.revert(fileOrDir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of revert method, of class Workspace.
     */
    //@Test
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
     * Test of release method, of class Workspace.
     */
    //@Test
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
    //@Test
    public void testResolve() {
        System.out.println("resolve");
        String file = "";
        Workspace instance = null;
        boolean expResult = false;
        boolean result = instance.resolve(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of status method, of class Workspace.
     */
    //@Test
    public void testStatus() throws Exception {
        System.out.println("status");
        Workspace instance = new Workspace("F:/mybackups/Educacao/Mestrado-UFF/Git/labgc-2012.2/launchers/windows/projeto1");
        VersionedItem expResult = null;
        VersionedItem result = instance.status();
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class Workspace.
     */
    //@Test
    public void testUpdate() {
        System.out.println("update");
        VersionedItem files = null;
        Workspace instance = null;
        instance.update(files);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of commit method, of class Workspace.
     */
    //@Test
    public void testCommit() {
        System.out.println("commit");
        Workspace instance = null;
        VersionedItem expResult = null;
        VersionedItem result = instance.commit();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of diff method, of class Workspace.
     */
    //@Test
    public void testDiff() {
        System.out.println("diff");
        String file = "";
        String version = "";
        Workspace instance = null;
        VersionedItem expResult = null;
        VersionedItem result = instance.diff(file, version);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerInterest method, of class Workspace.
     */
    //@Test
    public void testRegisterInterest() {
        System.out.println("registerInterest");
        IObserver obs = null;
        Workspace instance = null;
        instance.registerInterest(obs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
