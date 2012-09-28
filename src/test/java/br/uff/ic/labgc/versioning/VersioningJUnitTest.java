/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.storage.Project;
import br.uff.ic.labgc.storage.ProjectDAO;
import br.uff.ic.labgc.storage.ProjectUser;
import br.uff.ic.labgc.storage.ProjectUserDAO;
import br.uff.ic.labgc.storage.ProjectUserId;
import br.uff.ic.labgc.storage.User;
import br.uff.ic.labgc.storage.UserDAO;
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
public class VersioningJUnitTest {
    private static UserDAO userDAO = new UserDAO();
    private static ProjectDAO projectDAO = new ProjectDAO();
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    
    public VersioningJUnitTest() {
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
    // public void hello() {}
    
    @Test
    public void testLogin() {
        User user = new User("nome","usuario","senha");
        userDAO.add(user);
        Project project = new Project("projeto");
        projectDAO.add(project);
        ProjectUser pu = new ProjectUser(user.getId(), project.getId());
        projectUserDAO.add(pu);
        
        Versioning versioning = new Versioning();
        String token = versioning.login(project.getName(), user.getUsername(), user.getPassword());
        assertTrue("Token OK:", token.equals(user.getPassword()));
    
    }
}
