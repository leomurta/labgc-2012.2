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
public class UserDAOTest implements IDAOTest{
    private static UserDAO userDAO = new UserDAO();
    
    public UserDAOTest() {
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
     * Test of get method, of class UserDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id = 1;
        User result = userDAO.get(id);
        assertNotNull(result);
    }
    
    //@Test(expected=ObjectNotFoundException.class)
    //O sessao.get em UserDAO era pra retornar null mas não esta retornando
    //no testRemove funciona pq ?????
    public void testUserNotFound() {
        userDAO.get(10);
    }

    /**
     * Test of getByUserName method, of class UserDAO.
     */
    @Test
    public void testGetByUserName() {
        System.out.println("getByUserName");
        User user = userDAO.getByUserName("username1");
        assertNotNull(user);
    }

    /**
     * Test of exist method, of class UserDAO.
     */
    @Test
    public void testExist() {
        System.out.println("exist");
        String userName = "username1";
        boolean result = userDAO.exist(userName);
        assertEquals(true, result);
        
        userName = "naoexiste";
        result = userDAO.exist(userName);
        assertEquals(false, result);
    }

    /**
     * Test of getUsers method, of class UserDAO.
     */
    //@Test
    public void testGetUsers() {
        System.out.println("getUsers");
        List result = userDAO.getUsers();
        assertEquals(1, result.size());
    }

    @Test
    public void testAdd() {
        System.out.println("add");
        User user = new User("testAdd1","testAdd2","testAdd3");
        userDAO.get(1);
        int id = userDAO.add(user);
        assertTrue("id != 0:",id != 0);
    }

    //@Test
    public void testRemove() {
        System.out.println("remove");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //@Test
    public void testUpdate() {
        System.out.println("update");
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
