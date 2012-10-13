/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

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
public class ProjectUserTest {
    
    public ProjectUserTest() {
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
     * Test of add method, of class ProjectUserDAO.
     */
    //@Test
    public void testAdd() {
        System.out.println("add");
        ProjectUser projectUser = null;
        ProjectUserDAO instance = new ProjectUserDAO();
        ProjectUserId expResult = null;
        ProjectUserId result = instance.add(projectUser);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class ProjectUserDAO.
     */
    //@Test
    public void testUpdate() {
        System.out.println("update");
        ProjectUser projectUser = null;
        ProjectUserDAO instance = new ProjectUserDAO();
        instance.update(projectUser);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class ProjectUserDAO.
     */
    //@Test
    public void testRemove_ProjectUserId() {
        System.out.println("remove");
        ProjectUserId projectUserId = null;
        ProjectUserDAO instance = new ProjectUserDAO();
        instance.remove(projectUserId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class ProjectUserDAO.
     */
    //@Test
    public void testRemove_ProjectUser() {
        System.out.println("remove");
        ProjectUser pu = null;
        ProjectUserDAO instance = new ProjectUserDAO();
        instance.remove(pu);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class ProjectUserDAO.
     */
    //@Test
    public void testGet_ProjectUserId() {
        System.out.println("get");
        ProjectUserId projectUserId = null;
        ProjectUserDAO instance = new ProjectUserDAO();
        ProjectUser expResult = null;
        ProjectUser result = instance.get(projectUserId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class ProjectUserDAO.
     */
    //@Test
    public void testGet_int_int() {
        System.out.println("get");
        int userId = 0;
        int projId = 0;
        ProjectUserDAO instance = new ProjectUserDAO();
        ProjectUser expResult = null;
        ProjectUser result = instance.get(userId, projId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getByToken method, of class ProjectUserDAO.
     */
    //@Test
    public void testGetByToken() {
        System.out.println("getByToken");
        String token = "";
        ProjectUserDAO instance = new ProjectUserDAO();
        ProjectUser expResult = null;
        ProjectUser result = instance.getByToken(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProjectUsers method, of class ProjectUserDAO.
     */
    //@Test
    public void testGetProjectUsers() {
        System.out.println("getProjectUsers");
        ProjectUserDAO instance = new ProjectUserDAO();
        List expResult = null;
        List result = instance.getProjectUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
