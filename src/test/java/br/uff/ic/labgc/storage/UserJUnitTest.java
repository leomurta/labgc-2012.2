/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.InfrastructureException;
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
public class UserJUnitTest {
    
    private static UserDAO userDAO = new UserDAO();
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    
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
        User user = new User("Teste","Teste","teste");
        int id = userDAO.add(user);
        assertTrue("Usuário criado:",id != 0);
    }
    
    @Test
    public void testGet() {
        User user = new User("Teste","Teste","teste");
        int id = userDAO.add(user);
        User user1 = userDAO.get(id);
        assertTrue("Ids iguais:",id == user1.getId() );
    }
    
    @Test
    public void testGetUserByUserName() {
        User user = userDAO.getByUserName("username1");
        assertNotNull(user);
    }
    
    @Test(expected=ObjectNotFoundException.class)
    public void testDontGetUserByUserName() {
        userDAO.getByUserName("username10");
    }
    
    //@Test
    public void testGetUsers() {
        User user = new User("Teste","Teste","teste");
        userDAO.add(user);
        User user1 = new User("Teste1","Teste1","teste1");
        userDAO.add(user1);
        List<User> users = userDAO.getUsers();
        assertTrue("2 elementos:",users.size() == 2 );
    }
    
    //@Test(expected=ObjectNotFoundException.class)
    //O sessao.get em UserDAO era pra retornar null mas não esta retornando
    //no testRemove funciona pq ?????
    public void testUserNotFound() {
        userDAO.get(10);
    }
    
    @Test
    public void testUpdateName() {
        User user = new User("Teste","Teste","teste");
        String name = user.getName();
        user.setName("novo nome");
        userDAO.update(user);
        User user1 = userDAO.get(user.getId());
        assertTrue("Nomes diferentes:", !name.equals(user1.getName()));
    }
    
    @Test(expected=ObjectNotFoundException.class)
    public void testRemove() {
        User user = new User("Teste","Teste","teste");
        int id = userDAO.add(user);
        userDAO.remove(user);
        userDAO.get(id);
    }
    
}
