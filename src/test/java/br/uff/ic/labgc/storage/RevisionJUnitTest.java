/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
public class RevisionJUnitTest {
    
    private static RevisionDAO revisionDAO = new RevisionDAO();
    private static ProjectDAO projectDAO = new ProjectDAO();
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    private static UserDAO userDAO = new UserDAO();
    
    public RevisionJUnitTest() {
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
    public void testGetConfigurationItensFromRevision() {       
        Project project = new Project("projeto");
        projectDAO.add(project);
        
        User user = new User("user", "user", "user");
        userDAO.add(user);
        
        ProjectUser pu = new ProjectUser(project.getId(), user.getId());
        projectUserDAO.add(pu);
        
        Date date = new Date();
        Revision revision = new Revision(date,"descricao" , "1.0", user, project);
        Set configItens = new HashSet();
        ConfigurationItem ci1 = new ConfigurationItem(1, "arquivo1.txt", "FHUIRFGUY", 'A', null, null);
        ConfigurationItem ci2 = new ConfigurationItem(1, "arquivo2.txt", "vdfjkfkf", 'U', null, null);
        configItens.add(ci1);
        configItens.add(ci2);
        revision.setConfigItens(configItens);
        assertTrue("2 configItens:",2 == configItens.size());
        revisionDAO.add(revision);
        
        Revision revision1 = revisionDAO.getRevision(revision.getId());
        assertTrue("Usuário criado:",revision1.getConfigItens().size() == configItens.size());
    
         
    }
    
    
    
    
}
