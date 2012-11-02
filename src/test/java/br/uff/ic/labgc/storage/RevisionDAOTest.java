/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.internal.runners.statements.ExpectException;

/**
 *
 * @author jokerfvd
 */
public class RevisionDAOTest implements IDAOTest{
    private static RevisionDAO revisionDAO = new RevisionDAO();
    private static ProjectDAO projectDAO = new ProjectDAO();
    private static ProjectUserDAO projectUserDAO = new ProjectUserDAO();
    
    public RevisionDAOTest() {
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
     * Test of get method, of class RevisionDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id = 1;
        Revision result = revisionDAO.get(id);
        assertNotNull(result);
    }

    /**
     * Test of getByProjectAndNumber method, of class RevisionDAO.
     */
    @Test
    public void testGetByProjectAndNumber() {
        System.out.println("getByProjectAndNumber");
        int projId = 1;
        String number = "1.0";
        Revision result = revisionDAO.getByProjectAndNumber(projId, number);
        assertNotNull(result);
    }

    /**
     * Test of getHeadRevisionNumber method, of class RevisionDAO.
     */
    @Test
    public void testGetHeadRevisionNumber() {
        System.out.println("getHeadRevisionNumber");
        int projectId = 1;
        String expResult = "1.0";
        Project project = projectDAO.get(projectId);
        String result = revisionDAO.getHeadRevisionNumber(project);
        assertEquals(expResult, result);
    }

    @Test
    public void testAdd() {
        System.out.println("add");
        Date date = new Date();
        String description = "testAdd";
        String number = "1.1";
        ProjectUserId projectUserId = new ProjectUserId(1,1);
        ProjectUser pu = projectUserDAO.get(projectUserId);
        Revision revision = new Revision(date,description,number,pu.getUser(),pu.getProject());
        int id = revisionDAO.add(revision);
        assertTrue("id != 0:",id != 0);
    }

    @Test (expected=ObjectNotFoundException.class)
    public void testRemove() {
        System.out.println("remove");
        Date date = new Date();
        String description = "testAdd";
        String number = "1.1";
        ProjectUserId projectUserId = new ProjectUserId(1,1);
        ProjectUser pu = projectUserDAO.get(projectUserId);
        Revision revision = new Revision(date,description,number,pu.getUser(),pu.getProject());
        int id = revisionDAO.add(revision);
        Revision r1 = revisionDAO.get(id);
        assertNotNull(r1);
        revisionDAO.remove(revision);
        revisionDAO.get(id);
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Date date = new Date();
        String description = "testUpdate";
        String number = "1.2";
        ProjectUserId projectUserId = new ProjectUserId(1,1);
        ProjectUser pu = projectUserDAO.get(projectUserId);
        Revision revision = new Revision(date,description,number,pu.getUser(),pu.getProject());
        int id = revisionDAO.add(revision);
        String newDescription = "newDescription";
        revision.setDescription(newDescription);
        revisionDAO.update(revision);
        
        Revision r1 = revisionDAO.get(id);
        assertEquals(newDescription,r1.getDescription());
    }
}
