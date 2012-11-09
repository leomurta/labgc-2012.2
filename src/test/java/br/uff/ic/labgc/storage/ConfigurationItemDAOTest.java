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
    //@Test
    public void testGet() {
        System.out.println("get");
        int id = 0;
        ConfigurationItemDAO instance = new ConfigurationItemDAO();
        ConfigurationItem expResult = null;
        ConfigurationItem result = instance.get(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getByHash method, of class ConfigurationItemDAO.
     */
    //@Test
    public void testGetByHash() {
        System.out.println("getByHash");
        String hash = "";
        ConfigurationItemDAO instance = new ConfigurationItemDAO();
        ConfigurationItem expResult = null;
        ConfigurationItem result = instance.getByHash(hash);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Override
    //@Test
    public void testAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    //@Test
    public void testRemove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    //@Test
    public void testUpdate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
