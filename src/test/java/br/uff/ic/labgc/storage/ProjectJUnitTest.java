/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

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
public class ProjectJUnitTest {
    private static ProjectDAO projectDAO = new ProjectDAO();
    
    public ProjectJUnitTest() {
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
    public void testAdd() {
        Project project = new Project("Projeto 1");
        int id = projectDAO.add(project);
        assertTrue("Projeto criado:",id != 0);
    }
    
    @Test
    public void testGet() {
        Project project = new Project("Projeto 1");
        int id = projectDAO.add(project);
        Project project1 = projectDAO.get(id);
        assertTrue("Ids iguais:",id == project1.getId() );
    }
    
    
    
}
