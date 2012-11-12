/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.core.EVCSConstants;
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.exception.IncorrectPasswordException;
import br.uff.ic.labgc.exception.VersioningException;
import br.uff.ic.labgc.storage.ConfigurationItem;
import br.uff.ic.labgc.storage.ConfigurationItemDAO;
import br.uff.ic.labgc.storage.Project;
import br.uff.ic.labgc.storage.ProjectDAO;
import br.uff.ic.labgc.storage.ProjectUser;
import br.uff.ic.labgc.storage.ProjectUserDAO;
import br.uff.ic.labgc.storage.Revision;
import br.uff.ic.labgc.storage.RevisionDAO;
import br.uff.ic.labgc.storage.Storage;
import br.uff.ic.labgc.storage.User;
import br.uff.ic.labgc.storage.UserDAO;
import br.uff.ic.labgc.storage.util.HibernateUtil;
import br.uff.ic.labgc.storage.util.InfrastructureException;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.plexus.util.FileUtils;
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
public class VersioningTest {
    private static Versioning versioning = new Versioning();
    private static ProjectDAO projectDAO = new ProjectDAO();
    private static RevisionDAO revisionDAO = new RevisionDAO();
    private static ConfigurationItemDAO configItemDAO = new ConfigurationItemDAO();
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    private static UserDAO userDAO = new UserDAO();
    
    public VersioningTest() {
        Storage.setDirPath("repositorio/");
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
     * Test of getRevisionUserName method, of class Versioning.
     */
    //@Test
    public void testGetRevisionUserName() {
        System.out.println("getRevisionUserName");
        String number = "";
        Versioning instance = new Versioning();
        String expResult = "";
        String result = instance.getRevisionUserName(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRevision method, of class Versioning.
     */
    @Test
    public void testGetRevision() throws VersioningException {
        System.out.println("getRevision");
        String token = "nvfdovhfdoivbiofdvf";
        VersionedDir vd = versioning.getRevision("1.0", token);
        assertTrue("Revision criada:",vd.getSize() != 0);     
    }

    /**
     * Test of login method, of class Versioning.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String token = versioning.login("projeto1", "username1", "password1");
        assertTrue("Token OK:", token.equals("nvfdovhfdoivbiofdvf"));
        
        try{
            versioning.login("projeto1", "naoexiste", "password1");
            fail("ObjectNotFoundException expected");
        }catch (ObjectNotFoundException ex) {}
        
        try{
            versioning.login("projeto1", "username1", "senhaerrada");
            fail("IncorrectPasswordException expected");
        }catch (IncorrectPasswordException ex) {}
    }

    /**
     * Test of getVersionedFileContent method, of class Versioning.
     */
    @Test
    public void testGetVersionedFileContent() throws Exception {
        System.out.println("getVersionedFileContent");       
        String token = "nvfdovhfdoivbiofdvf";
        byte content[];
        content = versioning.getVersionedFileContent("vnfdovh9e0h0", token);
        assertTrue(content.length == 10);
    }

    /**
     * Test of addRevision method, of class Versioning.
     */
    @Test
    public void testAddRevision() throws Exception {
        try{
        System.out.println("addRevision");
        VersionedDir vd = new VersionedDir();
        vd.setAuthor("Autor10");
        vd.setCommitMessage("msg2012");
        vd.setName("projeto1");
        vd.setStatus(EVCSConstants.MODIFIED);
        vd.setLastChangedRevision("1.0");
        
        VersionedFile vf1 = new VersionedFile();
        vf1.setAuthor(vd.getAuthor());
        vf1.setCommitMessage(vd.getCommitMessage());
        vf1.setName("arquivo10.txt");
        vf1.setContent("lambdalambda".getBytes());
        vf1.setStatus(EVCSConstants.ADDED);
        vd.addItem(vf1);
        
        VersionedFile vf2 = new VersionedFile("vnfdovh9e0h0", 10);
        vf2.setAuthor(vd.getAuthor());
        vf2.setCommitMessage(vd.getCommitMessage());
        vf2.setName("arquivo1.txt");
        vf2.setStatus(EVCSConstants.UNMODIFIED);
        vd.addItem(vf2);
        
        VersionedFile vf3 = new VersionedFile("vcdfsniovfbiov", 5);
        vf3.setAuthor(vd.getAuthor());
        vf3.setCommitMessage(vd.getCommitMessage());
        vf3.setName("arquivo2.txt");
        vf3.setStatus(EVCSConstants.DELETED);
        vd.addItem(vf3);
        
        VersionedFile vf4 = new VersionedFile();
        vf4.setAuthor(vd.getAuthor());
        vf4.setCommitMessage(vd.getCommitMessage());
        vf4.setName("arquivo11.txt");
        vf4.setContent("lambdalambda".getBytes());
        vf4.setStatus(EVCSConstants.ADDED);
        vd.addItem(vf4);
        
        VersionedFile vf5 = new VersionedFile();
        vf5.setAuthor(vd.getAuthor());
        vf5.setCommitMessage(vd.getCommitMessage());
        vf5.setName("arquivo12.txt");
        vf5.setContent("lambdalambda".getBytes());
        vf5.setStatus(EVCSConstants.ADDED);
        vd.addItem(vf5);
        
        String token = "nvfdovhfdoivbiofdvf";
        String expResult = "1.1";
        String result = versioning.addRevision(vd, token);
        assertEquals(expResult, result);
        
        //deleting
        Project project = projectDAO.get(1);
        String revNum = revisionDAO.getHeadRevisionNumber(project);
        Revision revision = revisionDAO.getByProjectAndNumber(project.getId(), revNum);
        revisionDAO.removeRemovingFKs(revision);
        
        }
        catch (Exception e){
            HibernateUtil.rollbackTransaction();
            HibernateUtil.closeSession();
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, e);
            fail();
        }
    }

    /**
     * Test of addFirstRevision method, of class Versioning.
     * ta dando erro na hora dos removes mais ta insereindo OK
     */
    //@Test
    public void testAddFirstRevision() throws Exception {
        try{
        System.out.println("addFirstRevision"); 
        VersionedDir vd = new VersionedDir();
        vd.setAuthor("Autor10");
        vd.setCommitMessage("msg10");
        vd.setName("projeto10");
        
        VersionedFile vf = new VersionedFile();
        vf.setAuthor(vd.getAuthor());
        vf.setCommitMessage(vd.getCommitMessage());
        vf.setName("arquivo10.txt");
        vf.setContent("iabadabadu".getBytes());
        vd.addItem(vf);
        
        //removendo diretorio se existente
        FileUtils.deleteDirectory(new File(Storage.getDirPath() +vd.getName()));
        
        User user = userDAO.getByUserName("username1");
        versioning.addFirstRevision(vd, user.getUsername());
        String revNum = EVCSConstants.FIRST_REVISION;
        //HibernateUtil.commitTransaction();
        Project project = projectDAO.getByName("projeto10");
        assertNotNull(project);
        
        //removendo diretorio criado
        FileUtils.deleteDirectory(new File(Storage.getDirPath() +vd.getName()));
        
        Revision rev = revisionDAO.getByProjectAndNumber(project.getId(), revNum);
        ConfigurationItem ci = rev.getConfigItem();
        ci.setRevision(null);
        rev.setConfigItem(null);
        configItemDAO.remove(ci);
        revisionDAO.remove(rev);
        ProjectUser pu = new ProjectUser(project.getId(),user.getId());
        projectUserDAO.remove(pu);
        projectDAO.remove(project);
        
//*        
        }
        catch(Exception e){	
            HibernateUtil.rollbackTransaction();
            HibernateUtil.closeSession();
            throw e;
        }
 //*/       
    }

    /**
     * Test of updateRevision method, of class Versioning.
     */
    //@Test
    public void testUpdateRevision() throws VersioningException {
        System.out.println("updateRevision");
        String revNum = "";
        String revTo = "";
        String token = "";
        Versioning instance = new Versioning();
        VersionedDir expResult = null;
        VersionedDir result = instance.updateRevision(revNum, revTo, token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastLogs method, of class Versioning.
     */
    @Test
    public void testGetLastLogs() throws Exception {
        try{
        System.out.println("getLastLogs");
        String token = "nvfdovhfdoivbiofdvf";

        VersionedDir vd = new VersionedDir();
        vd.setAuthor("Autor10");
        vd.setCommitMessage("msg10");
        vd.setName("projeto10");
        vd.setStatus(EVCSConstants.MODIFIED);
        vd.setLastChangedRevision("1.0");
        versioning.addRevision(vd, token);
        
        VersionedDir vd1 = new VersionedDir();
        vd1.setAuthor("Autor11");
        vd1.setCommitMessage("msg11");
        vd1.setName("projeto11");
        vd1.setStatus(EVCSConstants.MODIFIED);
        vd1.setLastChangedRevision("1.1");
        versioning.addRevision(vd1, token);
        
        VersionedDir vd2 = new VersionedDir();
        vd2.setAuthor("Autor12");
        vd2.setCommitMessage("msg12");
        vd2.setName("projeto12");
        vd2.setStatus(EVCSConstants.MODIFIED);
        vd2.setLastChangedRevision("1.2");
        versioning.addRevision(vd2, token);
        
        List result = versioning.getLastLogs(token);
        assertEquals(4, result.size());
        
 //*       
        //deleting
        Project project = projectDAO.get(1);
        Revision revision = revisionDAO.getByProjectAndNumber(project.getId(), "1.3");
        revisionDAO.removeRemovingFKs(revision);
        revision = revisionDAO.getByProjectAndNumber(project.getId(), "1.2");
        revisionDAO.removeRemovingFKs(revision);
        revision = revisionDAO.getByProjectAndNumber(project.getId(), "1.1");
        revisionDAO.removeRemovingFKs(revision);
   
//*/ 
        }
        catch(Exception e){	
            HibernateUtil.rollbackTransaction();
            HibernateUtil.closeSession();
            throw e;
        }
    }
    
    @Test
    public void testIncrementRevision() throws Exception{
        System.out.println("incrementRevision");
        Class[] param = new Class[1];	
	param[0] = String.class;
        Method method;
        method = Versioning.class.getDeclaredMethod("incrementRevision", param);
        method.setAccessible(true);
        String revision = "1.0";
        String newRev = (String)method.invoke(versioning, revision);
        assertEquals(newRev, "1.1");

        revision = "1.13";
        newRev = (String)method.invoke(versioning, revision);
        assertEquals(newRev, "1.14");

        revision = "1.20.30.41";
        newRev = (String)method.invoke(versioning, revision);
        assertEquals(newRev, "1.20.30.42");
    }
    
    @Test 
    public void testConfigItemToVersionedDir() throws Exception{
        System.out.println("configItemToVersionedDir");
        Class[] param = new Class[1];	
	param[0] = ConfigurationItem.class;
        Method method;    
        method = Versioning.class.getDeclaredMethod("configItemToVersionedDir", param);
        method.setAccessible(true);
        
        Date date = new Date();
        User user = new User("name", "username", "password");
        Revision revision = new Revision(date, "revisao 1", "1.0", user, null);
        ConfigurationItem ci = new ConfigurationItem(1, "pastaraiz", null, 'A', 1, 0, null, null, revision);
        ConfigurationItem child1 = new ConfigurationItem(1, "teste1", "hash1", 'A', 0, 10, null, null, revision);
        ci.addChild(child1);
        ConfigurationItem child2 = new ConfigurationItem(1, "teste2", "hash2", 'A', 0, 5, null, null, revision);
        ci.addChild(child2);
        
        VersionedDir vd = (VersionedDir)method.invoke(versioning, ci);
        assertEquals(vd.getAuthor(), user.getName());
        assertEquals(vd.getCommitMessage(), revision.getDescription());
        assertEquals(vd.getLastChangedRevision(), revision.getNumber());
        assertEquals(vd.getLastChangedTime(), revision.getDate());
        assertEquals(vd.getName(), ci.getName());
        assertEquals(vd.getContainedItens().size(), ci.getChildren().size());
        
        VersionedFile vf = (VersionedFile)vd.getContainedItens().get(0);
        assertEquals(vf.getAuthor(), user.getName());
        assertEquals(vf.getCommitMessage(), revision.getDescription());
        assertEquals(vf.getLastChangedRevision(), revision.getNumber());
        assertEquals(vf.getLastChangedTime(), revision.getDate());
        
        //o get(0) cada hora retorna um diferente, entao eh melhor nao testa
        //assertEquals(vf.getName(), child1.getName());
        //assertEquals(vf.getHash(), child1.getHash());
    }
    
    @Test 
    public void testVersionedDirToConfigItem() throws Exception{
        System.out.println("versionedDirToConfigItem");
        Class[] param = new Class[3];	
	param[0] = VersionedDir.class;
        param[1] = ConfigurationItem.class;
        param[2] = boolean.class;
        Method method;    
        method = Versioning.class.getDeclaredMethod("versionedDirToConfigItem", param);
        method.setAccessible(true);
        
        User user = new User("name", "username", "password");
        VersionedDir vd = new VersionedDir(null, null, "raiz", user.getName(), "msg");
        
        VersionedFile vf1 = new VersionedFile(null, null, "arq1", user.getName(), "msg1");
        vf1.setContent("conteudo1".getBytes());
        VersionedDir vd1 = new VersionedDir(null, null, "dir1", user.getName(), "msgmsg");
        vd1.addItem(vf1);
        vd.addItem(vd1);
        
        VersionedFile vf2 = new VersionedFile(null, null, "arq2", user.getName(), "msg2");
        vf2.setContent("conteudo2".getBytes());
        vd.addItem(vf2);
        
        //criar revisão 0
        Date date = new Date();
        Project project = new Project(vd.getName());
        Revision revision = new Revision(date, "revision 1.0", "1.0", user, project);
        ConfigurationItem ci = new ConfigurationItem(1,project.getName(),"",'A',
                1,vd.getSize(),null,null,revision);
        
        method.invoke(versioning, vd,ci,true);
        assertEquals(1, ci.getDir());
        assertEquals(vf1.getSize()+vf2.getSize(), ci.getSize());
        assertEquals(vd.getName(), ci.getName());
        assertEquals(1, ci.getNumber());
    }
    
    //@Test 
    public void testAdd1ItemToRevision(){
        System.out.println("add1ItemToRevision");
    }
    
}
