/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
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
public class ProjectUserDAOTest implements IDAOTest{
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    
    public ProjectUserDAOTest() {
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
    @Test
    public void testAdd() {
        System.out.println("add");
        ProjectUser projectUser = new ProjectUser(10,10);
        ProjectUserId result = projectUserDAO.add(projectUser);
        assertNotNull(result);
    }

    /**
     * Test of update method, of class ProjectUserDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        ProjectUserId id = new ProjectUserId(1,1);
        ProjectUser projectUser = projectUserDAO.get(id);
        assertEquals("nvfdovhfdoivbiofdvf", projectUser.getToken());
        assertEquals(11111, projectUser.getPermission());
        projectUser.setPermission(10000);
        projectUserDAO.update(projectUser);
        
        ProjectUser pu = projectUserDAO.get(id);
        assertEquals(10000, pu.getPermission());
    }

    /**
     * Test of remove method, of class ProjectUserDAO.
     */
    @Test (expected=ObjectNotFoundException.class)
    public void testRemove_ProjectUserId() {
        System.out.println("remove");
        ProjectUser projectUser = new ProjectUser(11,11);
        ProjectUserId result = projectUserDAO.add(projectUser);
        projectUserDAO.remove(result);
        projectUserDAO.get(11,11);
    }

    /**
     * Test of remove method, of class ProjectUserDAO.
     */
    @Test (expected=ObjectNotFoundException.class)
    public void testRemove_ProjectUser() {
        System.out.println("remove");
        ProjectUser projectUser = new ProjectUser(12,12);
        ProjectUserId id = projectUserDAO.add(projectUser);
        projectUserDAO.remove(projectUser);
        projectUserDAO.get(id);
    }

    /**
     * Test of get method, of class ProjectUserDAO.
     */
    @Test
    public void testGet_ProjectUserId() {
        System.out.println("get");
        ProjectUserId projectUserId = new ProjectUserId(1,1);
        ProjectUser result = projectUserDAO.get(projectUserId);
        assertNotNull(result);
    }

    /**
     * Test of get method, of class ProjectUserDAO.
     */
    @Test
    public void testGet_int_int() {
        System.out.println("get");
        int userId = 1;
        int projId = 1;
        ProjectUser result = projectUserDAO.get(userId, projId);
        assertNotNull(result);
    }

    /**
     * Test of getByToken method, of class ProjectUserDAO.
     */
    @Test
    public void testGetByToken() {
        System.out.println("getByToken");
        String token = "nvfdovhfdoivbiofdvf";
        ProjectUser result = projectUserDAO.getByToken(token);
        assertNotNull(result);
    }

    //@Test
    public void testRemove() {
        //ja tem 2 testes removes
    }
}
