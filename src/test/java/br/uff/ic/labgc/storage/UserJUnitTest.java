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
public class UserJUnitTest {
    
    private static UserDAO userDAO = new UserDAO();
    
    public UserJUnitTest() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    //public void hello() {}
    
    @Test
    public void testAdd() {
        User user1 = new User(1,"Teste","teste");
        int id = userDAO.add(user1);
        assertTrue("Usuário criado:",id == 1);
        User user2 = new User(2,"Teste1","teste1");
        id = userDAO.add(user2);
        assertTrue("Usuário criado:",id == 2);
    }
}
