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
    private static UserDAO userDAO = new UserDAO();
    private static ProjectDAO projectDAO = new ProjectDAO();
    
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
        Project project = new Project("kjhkj");
        projectDAO.add(project);
        User user = new User("a1", "b1", "c1");
        userDAO.add(user);
        ProjectUser projectUser = new ProjectUser(project.getId(),user.getId());
        ProjectUserId result = projectUserDAO.add(projectUser);
        assertNotNull(result);
        projectUserDAO.remove(result);
        projectDAO.remove(project);
        userDAO.remove(user);
    }

    /**
     * Test of update method, of class ProjectUserDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        ProjectUserId id = new ProjectUserId(1,1);
        ProjectUser projectUser = projectUserDAO.get(id);
        int old = projectUser.getPermission();
        int newp = 10000;
        projectUser.setPermission(newp);
        projectUserDAO.update(projectUser);
        
        ProjectUser pu = projectUserDAO.get(id);
        assertEquals(newp, pu.getPermission());
        
        projectUser.setPermission(old);
        projectUserDAO.update(projectUser);
    }

    /**
     * Test of remove method, of class ProjectUserDAO.
     */
    @Test
    public void testRemove_ProjectUserId() {
        System.out.println("remove");
        Project project = new Project("fivdf");
        projectDAO.add(project);
        User user = new User("a", "b", "c");
        userDAO.add(user);
        ProjectUser projectUser = new ProjectUser(project.getId(),user.getId());
        ProjectUserId result = projectUserDAO.add(projectUser);
        projectUserDAO.remove(result);
        
         try{
            projectUserDAO.get(project.getId(),user.getId());
            fail("ObjectNotFoundException expected");
        }catch (ObjectNotFoundException ex) {}
         projectDAO.remove(project);
         userDAO.remove(user);
        
    }

    /**
     * Test of remove method, of class ProjectUserDAO.
     */
    @Test 
    public void testRemove_ProjectUser() {
        System.out.println("remove");
        Project project = new Project("vfs");
        projectDAO.add(project);
        User user = new User("a2", "b2", "c2");
        userDAO.add(user);
        ProjectUser projectUser = new ProjectUser(project.getId(),user.getId());
        ProjectUserId id = projectUserDAO.add(projectUser);
        projectUserDAO.remove(projectUser);
        
         try{
            projectUserDAO.get(id);
            fail("ObjectNotFoundException expected");
        }catch (ObjectNotFoundException ex) {}
         projectDAO.remove(project);
         userDAO.remove(user);
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
