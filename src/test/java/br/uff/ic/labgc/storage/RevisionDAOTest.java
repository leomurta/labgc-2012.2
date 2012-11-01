/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

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
public class RevisionDAOTest {
    
    public RevisionDAOTest() {
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
     * Test of get method, of class RevisionDAO.
     */
    //@Test
    public void testGet() {
        System.out.println("get");
        int id = 0;
        RevisionDAO instance = new RevisionDAO();
        Revision expResult = null;
        Revision result = instance.get(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getByProjectAndNumber method, of class RevisionDAO.
     */
    //@Test
    public void testGetByProjectAndNumber() {
        System.out.println("getByProjectAndNumber");
        int projId = 0;
        String number = "";
        RevisionDAO instance = new RevisionDAO();
        Revision expResult = null;
        Revision result = instance.getByProjectAndNumber(projId, number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeadRevisionNumber method, of class RevisionDAO.
     */
    @Test
    public void testGetHeadRevisionNumber() {
        System.out.println("getHeadRevisionNumber");
        int projectId = 1;
        RevisionDAO instance = new RevisionDAO();
        String expResult = "1.0";
        ProjectDAO pd = new ProjectDAO();
        Project project = pd.get(projectId);
        String result = instance.getHeadRevisionNumber(project);
        assertEquals(expResult, result);
    }
}
