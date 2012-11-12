/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.HibernateUtil;
import br.uff.ic.labgc.storage.util.InfrastructureException;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
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
public class ProjectDAOTest implements IDAOTest{
    private static ProjectDAO projectDAO = new ProjectDAO();
    private static UserDAO userDAO = new UserDAO();
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    
    public ProjectDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        HibernateUtil.beginTransaction();
    }
    
    @After
    public void tearDown() {
        try{
            HibernateUtil.commitTransaction();
        }
        catch(Exception e){	
            HibernateUtil.rollbackTransaction();
            HibernateUtil.closeSession();
        }
    }

    /**
     * Test of getByName method, of class ProjectDAO.
     */
    @Test
    public void testGetByName() {
        System.out.println("getByName");
        Project project = projectDAO.getByName("projeto1");
        assertNotNull(project);
    }

    /**
     * Test of exist method, of class ProjectDAO.
     */
    @Test
    public void testExist() {
        System.out.println("exist - teste 1");
        String name = "projeto1";
        boolean expResult = true;
        boolean result = projectDAO.exist(name);
        assertEquals(expResult, result);
        
        System.out.println("exist - teste 2");
        name = "naoexisto";
        expResult = false;
        result = projectDAO.exist(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class ProjectDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id=1;
        Project project = projectDAO.get(id);
        assertNotNull(project);
        assertTrue("Ids iguais:",id == project.getId() );
    }

    @Test
    public void testAdd() {
        System.out.println("add");
        Project project = new Project("Projeto 1");
        int id = projectDAO.add(project);
        assertTrue("id != 0:",id != 0);
        projectDAO.remove(project);
    }

    @Test (expected=ObjectNotFoundException.class)
    public void testRemove() {
        System.out.println("remove");
        Project project = new Project("Projeto 1");
        int id = projectDAO.add(project);
        projectDAO.remove(project);
        projectDAO.get(id);
        
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Project project = new Project("testUpdate");
        String old = project.getName();
        projectDAO.add(project);
        boolean result = projectDAO.exist(old);
        assertEquals(true, result);
        
        String newName = "newName";
        project.setName(newName);
        projectDAO.update(project);
        result = projectDAO.exist(newName);
        assertEquals(true, result);
        
        result = projectDAO.exist(old);
        assertEquals(false, result);
        projectDAO.remove(project);
    }
    
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        Project project = new Project("Projeto 2");
        projectDAO.add(project);
        User user = new User("name1", "username2", "pass2");
        userDAO.add(user);
        ProjectUser pu = new ProjectUser(project.getId(),user.getId());
        projectUserDAO.add(pu);
        
        projectUserDAO.remove(pu);
        projectDAO.remove(project);
        userDAO.remove(user);
        
    }
}
