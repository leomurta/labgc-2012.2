/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

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
public class ConfigurationItemDAOTest implements IDAOTest{
    private static ConfigurationItemDAO configItemDAO = new ConfigurationItemDAO();
    private static RevisionDAO revisionDAO = new RevisionDAO();
    private static ProjectDAO projectDAO = new ProjectDAO();
    
    public ConfigurationItemDAOTest() {
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
     * Test of get method, of class ConfigurationItemDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id = 1;
        ConfigurationItem result = configItemDAO.get(id);
        assertNotNull(result);
    }

    /**
     * Test of getByHash method, of class ConfigurationItemDAO.
     */
/*
    @Test
    public void testGetByHash() {
        System.out.println("getByHash");
        String hash = "vnfdovh9e0h0";
        ConfigurationItem result = configItemDAO.getByHash(hash);
        assertNotNull(result);
        assertEquals(10, result.getSize());
    }
*/    

    @Override
    @Test
    public void testAdd() {
        System.out.println("add");
        Project project = projectDAO.get(1);
        String revNum = revisionDAO.getHeadRevisionNumber(project);
        Revision revision = revisionDAO.getByProjectAndNumber(project.getId(), revNum);
        ConfigurationItem ci = new ConfigurationItem(1, "nomepasta", null, 'A', 1, 0, null, null, revision);
        configItemDAO.add(ci);
        configItemDAO.remove(ci);      
    }
    
    @Test
    public void testAddWithChildren() {
        System.out.println("addWithChildren");
        Project project = projectDAO.get(1);
        String revNum = revisionDAO.getHeadRevisionNumber(project);
        Revision revision = revisionDAO.getByProjectAndNumber(project.getId(), revNum);
        ConfigurationItem ci = new ConfigurationItem(1, "nomepasta1", null, 'A', 1, 0, null, null, revision);
        ConfigurationItem c1 = new ConfigurationItem(1, "filho1", "filho1", 'A', 0, 0, null, null, revision);
        ci.addChild(c1);
        ConfigurationItem c2 = new ConfigurationItem(1, "filho2", "filho2", 'A', 0, 0, null, null, revision);
        ci.addChild(c2);
        configItemDAO.add(ci);
        configItemDAO.remove(ci);      
    }
    
    @Test
    public void testAddWithPreviousAndNext() {
        System.out.println("addWithPrevious");
        Project project = projectDAO.get(1);
        String revNum = revisionDAO.getHeadRevisionNumber(project);
        Revision revision = revisionDAO.getByProjectAndNumber(project.getId(), revNum);
        ConfigurationItem previous = configItemDAO.get(1);
        ConfigurationItem ci = new ConfigurationItem(2, "novo", null, 'A', 1, 0, null, previous, revision);
        previous.setNext(ci);//não precisei atualizar usando o update
        configItemDAO.add(ci);
        configItemDAO.remove(ci);
        previous.setNext(null);
    }

    @Override
    //@Test testado no add
    //já foi
    public void testRemove() {
        System.out.println("remover");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Test
    public void testUpdate() {
        System.out.println("update");
        ConfigurationItem ci = configItemDAO.get(1);
        long oldsize = ci.getSize();
        long newsize = 200;
        ci.setSize(newsize);
        ConfigurationItem ciagain = configItemDAO.get(1);
        assertEquals(newsize, ciagain.getSize());
        ci.setSize(oldsize);
    }
    
    @Test
    public void testGetByValuesAnParent(){
        System.out.println("getByValuesAnParent");
        String hash = "vnfdovh9e0h0";
        String name = "arquivo1.txt";
        int parentId = 1;
        int isDir = 0;
        ConfigurationItem result = configItemDAO.getByValuesAnParent(name,hash,parentId,isDir);
        assertNotNull(result);
    }
}
