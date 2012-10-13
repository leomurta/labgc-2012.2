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
            String token = versioning.login("projeto10", "usuario10", "senha10");
            assertTrue("Token OK:", token.equals("senha10"));
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
/*
        User user = userDAO.getByUserName("usuario10");
        Project project = projectDAO.getName("usuario10");
        Date date = new Date();
        Revision revision = new Revision(date,"descricao" , "1.0", user, project);
        ArrayList<ConfigurationItem> configItens = new ArrayList();
        ConfigurationItem ci1 = new ConfigurationItem(1, "arquivo1.txt", "FHUIRFGUY", 'A', null, null);
        ConfigurationItem ci2 = new ConfigurationItem(1, "arquivo2.txt", "vdfjkfkf", 'U', null, null);
        configItens.add(ci1);
        configItens.add(ci2);
        revision.setConfigItens(configItens);
        assertTrue("2 configItens:",2 == configItens.size());
        revisionDAO.add(revision);
*/ 

        String token = "senha10";
        VersionedDir vd = versioning.getRevision("1.0", token);
        assertTrue("Revision criado:",vd.getSize() != 0);
        
    }
    
    @Test
    public void testgetVersionedFile() {
        try {
            String token = "senha10";
            VersionedFile vf = versioning.getVersionedFile("FHUIRFGUY", token);
            assertTrue(vf.getSize() != 0x0);
        } catch (IOException ex) {
            assertFalse("Erro 1",true);
            Logger.getLogger(VersioningJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
