/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.exception.StorageException;
import br.uff.ic.labgc.exception.StorageObjectAlreadyExistException;
import br.uff.ic.labgc.storage.util.HibernateUtil;
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
public class StorageTest {
    private static Storage storage = new Storage("repositorio/");
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    private static UserDAO userDAO = new UserDAO();
    private static ProjectDAO projectDAO = new ProjectDAO();
    
    public StorageTest() {
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
     * Test of setDirPath method, of class Storage.
     */
    //@Test
    public void testSetDirPath() {
        System.out.println("setDirPath");
        String dirPath = "";
        Storage.setDirPath(dirPath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDirPath method, of class Storage.
     */
    //@Test
    public void testGetDirPath() {
        System.out.println("getDirPath");
        String expResult = "";
        String result = Storage.getDirPath();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addProject method, of class Storage.
     */
    //@Test
    public void testAddProject_String_String() throws Exception {
        System.out.println("addProject");
        String projName = "";
        String userName = "";
        Storage instance = new Storage();
        instance.addProject(projName, userName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addProject method, of class Storage.
     */
    //@Test
    public void testAddProject_3args() throws Exception {
        System.out.println("addProject");
        String projName = "";
        String userName = "";
        ConfigurationItem ci = null;
        Storage instance = new Storage();
        instance.addProject(projName, userName, ci);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashToPath method, of class Storage.
     */
    @Test 
    public void testHashToPath() throws Exception{
        System.out.println("hashToPath");
        String hash = "aaabbbbbbbbb";
        String path = storage.hashToPath(hash);
        assertEquals("aaa/bbbbbbbbb", path);
    }

    /**
     * Test of storeFiles method, of class Storage.
     */
    //@Test
    public void testPersistFile() throws Exception {
        System.out.println("storeFiles");
        String dirPath = "";
        String filePath = "";
        byte[] content = null;
        Storage instance = new Storage();
        instance.persistFile(dirPath, filePath, content);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testAddUserToProject() throws Exception {
        System.out.println("addUserToProject");
        
        User user = new User("novo", "novo", "novo");
        userDAO.add(user);
        Project project = projectDAO.get(1);
        storage.addUserToProject(project.getName(),user.getUsername());
        ProjectUser pu = projectUserDAO.get(project.getId(), user.getId());
        projectUserDAO.remove(pu);
        userDAO.remove(user);
   
        try{
            User user1 = userDAO.get(1);
            storage.addUserToProject(project.getName(), user1.getUsername());
            fail("StorageObjectAlreadyExistException expected");
        }catch (StorageObjectAlreadyExistException e){} 
    }
    
    @Test
    public void testAddUser() throws StorageException{
        System.out.println("addUser");
        String userName = "novo";
        storage.addUser("novo", userName, "novo");
        
        User user = userDAO.getByUserName(userName);
        userDAO.remove(user);
        
        userName = "username1";
        try{
            storage.addUser("novo", userName, "novo");
            fail("StorageObjectAlreadyExistException expected");
        }catch (StorageObjectAlreadyExistException e){}
    }
            
}
