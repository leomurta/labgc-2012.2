/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.IncorrectPasswordException;
import br.uff.ic.labgc.storage.ConfigurationItem;
import br.uff.ic.labgc.storage.Project;
import br.uff.ic.labgc.storage.ProjectDAO;
import br.uff.ic.labgc.storage.ProjectUser;
import br.uff.ic.labgc.storage.ProjectUserDAO;
import br.uff.ic.labgc.storage.ProjectUserId;
import br.uff.ic.labgc.storage.Revision;
import br.uff.ic.labgc.storage.RevisionDAO;
import br.uff.ic.labgc.storage.User;
import br.uff.ic.labgc.storage.UserDAO;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static RevisionDAO revisionDAO = new RevisionDAO();
    private static Versioning versioning = new Versioning();
    
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
        try {
            String token = versioning.login("projeto1", "username1", "password1");
            assertTrue("Token OK:", token.equals("nvfdovhfdoivbiofdvf"));
        } catch (ObjectNotFoundException ex) {
            Logger.getLogger(VersioningJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Erro 1",true);
        } catch (IncorrectPasswordException ex) {
            Logger.getLogger(VersioningJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Erro 2",true);
        }
    
    }
    
    @Test
    public void testGetRevision() { 
        String token = "nvfdovhfdoivbiofdvf";
        VersionedDir vd = versioning.getRevision("1.0", token);
        assertTrue("Revision criado:",vd.getSize() != 0);
        
    }
    
    @Test
    public void testgetVersionedFile() {
        try {
            String token = "nvfdovhfdoivbiofdvf";
            VersionedFile vf = versioning.getVersionedFile("vnfdovh9e0h0", token);
            assertTrue(vf.getSize() == 10);
        } catch (IOException ex) {
            assertFalse("Erro 1",true);
            Logger.getLogger(VersioningJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
