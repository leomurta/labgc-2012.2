/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.exception.IncorrectPasswordException;
import br.uff.ic.labgc.storage.ConfigurationItem;
import br.uff.ic.labgc.storage.Project;
import br.uff.ic.labgc.storage.ProjectDAO;
import br.uff.ic.labgc.storage.Revision;
import br.uff.ic.labgc.storage.User;
import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class VersioningTest {
    private static Versioning versioning = new Versioning();
    private static ProjectDAO projectDAO = new ProjectDAO();
    
    public VersioningTest() {
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
     * Test of getDirPath method, of class Versioning.
     */
    //@Test
    public void testGetDirPath() {
        System.out.println("getDirPath");
        Versioning instance = new Versioning();
        String expResult = "";
        String result = instance.getDirPath();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDirPath method, of class Versioning.
     */
    //@Test
    public void testSetDirPath() {
        System.out.println("setDirPath");
        String dirPath = "";
        Versioning instance = new Versioning();
        instance.setDirPath(dirPath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRevisionUserName method, of class Versioning.
     */
    ////@Test
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
    public void testGetRevision() {
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
    //@Test
    public void testAddRevision() throws Exception {
        System.out.println("addRevision");
        VersionedDir vd = null;
        String token = "";
        Versioning instance = new Versioning();
        String expResult = "";
        String result = instance.addRevision(vd, token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFirstRevision method, of class Versioning.
     */
    //@Test
    public void testAddFirstRevision() throws Exception {
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
        versioning.addFirstRevision(vd, "username1");

        Project project = projectDAO.getByName("projeto10");
        assertNotNull(project);

    }

    /**
     * Test of updateRevision method, of class Versioning.
     */
    //@Test
    public void testUpdateRevision() {
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
    //@Test
    public void testGetLastLogs_String() {
        System.out.println("getLastLogs");
        String token = "";
        Versioning instance = new Versioning();
        List expResult = null;
        List result = instance.getLastLogs(token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastLogs method, of class Versioning.
     */
    //@Test
    public void testGetLastLogs_int_String() {
        System.out.println("getLastLogs");
        int num = 0;
        String token = "";
        Versioning instance = new Versioning();
        List expResult = null;
        List result = instance.getLastLogs(num, token);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        ConfigurationItem ci = new ConfigurationItem(1, "pastaraiz", null, 'A', true, 0, null, null, revision);
        ConfigurationItem child1 = new ConfigurationItem(1, "teste1", "hash1", 'A', false, 10, null, null, revision);
        ci.addChild(child1);
        ConfigurationItem child2 = new ConfigurationItem(1, "teste2", "hash2", 'A', false, 5, null, null, revision);
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
    public void testHashToPath() throws Exception{
        System.out.println("hashToPath");
        Class[] param = new Class[1];	
	param[0] = String.class;
        Method method;    
        method = Versioning.class.getDeclaredMethod("hashToPath", param);
        method.setAccessible(true);
        
        String hash = "aaabbbbbbbbb";
        String path = (String)method.invoke(versioning, hash);
        assertEquals("aaa/bbbbbbbbb", path);
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
        vd.addItem(vf2);;
        
        //criar revisão 0
        Date date = new Date();
        Project project = new Project(vd.getName());
        Revision revision = new Revision(date, "revision 1.0", "1.0", user, project);
        ConfigurationItem ci = new ConfigurationItem(1,project.getName(),"",'A',
                true,vd.getSize(),null,null,revision);
        
        method.invoke(versioning, vd,ci,true);
        assertEquals(true, ci.isDir());
        assertEquals(vf1.getSize()+vf2.getSize(), ci.getSize());
        assertEquals(vd.getName(), ci.getName());
        assertEquals(1, ci.getNumber());
    }
    
}