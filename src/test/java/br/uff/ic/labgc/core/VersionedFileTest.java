/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class VersionedFileTest {
    
    public VersionedFileTest() {
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
     * Test of getContent method, of class VersionedFile.
     */
    //@Test
    public void testGetContent() throws Exception {
        System.out.println("getContent");
        VersionedFile instance = new VersionedFile();
        byte[] expResult = null;
        byte[] result = instance.getContent();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setContent method, of class VersionedFile.
     */
    //@Test
    public void testSetContent() {
        System.out.println("setContent");
        byte[] content = null;
        VersionedFile instance = new VersionedFile();
        instance.setContent(content);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDiffContent method, of class VersionedFile.
     */
    //@Test
    public void testSetDiffContent() {
        System.out.println("setDiffContent");
        byte[] content = null;
        VersionedFile instance = new VersionedFile();
        instance.setDiffContent(content);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHash method, of class VersionedFile.
     */
    //@Test
    public void testGetHash() {
        System.out.println("getHash");
        VersionedFile instance = new VersionedFile();
        String expResult = "";
        String result = instance.getHash();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inflate method, of class VersionedFile.
     */
    //@Test
    public void testInflate() throws Exception {
        System.out.println("inflate");
        VersionedFile instance = new VersionedFile();
        instance.inflate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deflate method, of class VersionedFile.
     */
    //@Test
    public void testDeflate() throws Exception {
        System.out.println("deflate");
        VersionedFile instance = new VersionedFile();
        instance.deflate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isDir method, of class VersionedFile.
     */
    //@Test
    public void testIsDir() {
        System.out.println("isDir");
        VersionedFile instance = new VersionedFile();
        boolean expResult = false;
        boolean result = instance.isDir();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /*
     * o setContent chama o generete hash
     */
    @Test 
    public void testGenerateHash(){
        System.out.println("generateHash");
        VersionedFile vf = new VersionedFile();
        
        String plaintext = "your text here";
        vf.setContent(plaintext.getBytes());
        String hash = vf.getHash() ;
        assertEquals(32, hash.length());

        
        plaintext = "ABIUBIUDBCIBDIUBIUBIUCDIUCVIUGCIUVCIVSCIVCUVCUICVSIUCVSIUC"
                + "CVUYfh489fy45hf858g9489fhriefeiovb4398fh89hf589fh45f89r5hf8f"
                + "h498g45f9gr4f8r0fvhrfvj9rtgjrt09gj9045gg09fu509fy4509fj45f09"
                + "vj094jvdfiohv984y4f845h5frekojhoifhrtiotgiotrhgiorthgf094f59"
                + "h4f5h8045y845fh45hf5h89yf94hff94hf48fhg89f5h9g5094hf4gf4h0h0"
                + "hv45f8480fg8945gf8945gf8945gf894g5f89h45f8945hfhf9h4f94fhf9h";
        vf.setContent(plaintext.getBytes());
        assertEquals("7d61195bbf636134f074399f5982727a", vf.getHash());
    }
}
