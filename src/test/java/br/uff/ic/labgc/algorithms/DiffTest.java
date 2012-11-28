/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.algorithms;

import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import java.lang.reflect.Method;
import java.util.List;
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
public class DiffTest {
    
    public DiffTest() {
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
     * Test of main method, of class Diff.
     */
    //@Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        Diff.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of show method, of class Diff.
     */
    //@Test
    public void testShow() throws Exception {
        System.out.println("show");
        List<VersionedItem> lista = null;
        int ident = 0;
        Diff.show(lista, ident);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class Diff.
     */
    //@Test
    public void testRun() throws Exception {
        System.out.println("run");
        VersionedItem file1 = null;
        VersionedItem file2 = null;
        byte[] expResult = null;
        byte[] result = Diff.run(file1, file2);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of apply method, of class Diff.
     */
    //@Test
    public void testApply_VersionedFile_byteArr() throws Exception {
        System.out.println("apply");
        VersionedFile file1 = null;
        byte[] delta = null;
        byte[] expResult = null;
        byte[] result = Diff.apply(file1, delta);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of apply method, of class Diff.
     */
    //@Test
    public void testApply_VersionedDir_byteArr() throws Exception {
        System.out.println("apply");
        VersionedDir dir = null;
        byte[] delta = null;
        VersionedDir expResult = null;
        VersionedDir result = Diff.apply(dir, delta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lcs method, of class Diff.
     */
    //@Test
    public void testLcs() {
        System.out.println("lcs");
        byte[] seq1 = null;
        byte[] seq2 = null;
        byte[] expResult = null;
        byte[] result = Diff.lcs(seq1, seq2);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of has_diff method, of class Diff.
     */
    //@Test
    public void testHas_diff_byteArr_byteArr() {
        System.out.println("has_diff");
        byte[] fline_seq1 = null;
        byte[] fline_seq2 = null;
        boolean expResult = false;
        boolean result = Diff.has_diff(fline_seq1, fline_seq2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of has_diff method, of class Diff.
     */
    //@Test
    public void testHas_diff_List_List() throws Exception {
        System.out.println("has_diff");
        List<VersionedItem> set1 = null;
        List<VersionedItem> set2 = null;
        boolean expResult = false;
        boolean result = Diff.has_diff(set1, set2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testDiffAndApply() throws Exception {
        System.out.println("Diff");
        Class[] param = new Class[2];	
	param[0] = VersionedFile.class;
        param[1] = VersionedFile.class;
        Method method;    
        method = Diff.class.getDeclaredMethod("diff", param);
        method.setAccessible(true);
        
        VersionedFile vf1 = new VersionedFile();
        vf1.setContent("A\nB\nC\nD\nE\n".getBytes());
        
        VersionedFile vf2 = new VersionedFile();
        vf2.setContent("A\n2\nB\n4\nE\n".getBytes());
        
        Diff instance = new Diff();
        byte[] ret = (byte[])method.invoke(instance, vf1,vf2);
        
        byte[] retapply = Diff.apply(vf1, ret);
        assertArrayEquals(retapply, vf2.getContent());
    }
}
