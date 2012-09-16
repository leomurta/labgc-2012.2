/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.utils;

import br.uff.ic.labgc.exception.ApplicationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cristiano
 */
public class URLTest {
    
    public URLTest() {
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
     * Test of getDefaultPortNumber method, of class URL.
     */
    @Test
    public void testGetDefaultPortNumberFile() {
        String protocol = "file";
        int expResult = 0;
        int result = URL.getDefaultPortNumber(protocol);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDefaultPortNumber method, of class URL.
     */
    @Test
    public void testGetDefaultPortNumberRmi() {
        String protocol = "rmi";
        int expResult = 1099;
        int result = URL.getDefaultPortNumber(protocol);
        assertEquals(expResult, result);
    }

    /**
     * Test of getProtocol method, of class URL.
     */
    @Test
    public void testGetProtocolFile() throws ApplicationException {
        String s1 = "file:///";
        String s2 = "file://D:/";
        String s3 = "file://usuario@D:/";
        URL instance1 = new URL(s1);
        String expResult = "file";
        String result1 = instance1.getProtocol();
        assertEquals(s1, expResult, result1);
        URL instance2 = new URL(s2);
        String result2 = instance2.getProtocol();
        assertEquals(s2, expResult, result2);
    }

    /**
     * Test of getProtocol method, of class URL.
     */
    @Test
    public void testGetProtocolRmi() throws ApplicationException {
        String s2 = "rmi://localhost";
        String s3 = "rmi://localhost/";
        String s4 = "rmi://localhost:9090";
        String s5 = "rmi://usuario@localhost";
        String s6 = "rmi://usuario@localhost/";
        String s7 = "rmi://usuario@localhost:9090";
        String expResult = "rmi";
        URL instance2 = new URL(s2);
        String result2 = instance2.getProtocol();
        assertEquals(expResult, result2);
        URL instance3 = new URL(s3);
        String result3 = instance3.getProtocol();
        assertEquals(expResult, result3);
        URL instance4 = new URL(s4);
        String result4 = instance4.getProtocol();
        assertEquals(expResult, result4);
        URL instance5 = new URL(s5);
        String result5 = instance5.getProtocol();
        assertEquals(expResult, result5);
        URL instance6 = new URL(s6);
        String result6 = instance6.getProtocol();
        assertEquals(expResult, result6);
        URL instance7 = new URL(s7);
        String result7 = instance7.getProtocol();
        assertEquals(expResult, result7);
    }

    /**
     * Test of getHost method, of class URL.
     */
    @Test
    public void testGetHost() throws ApplicationException{
        String s2 = "rmi://localhost";
        String s3 = "rmi://localhost/";
        String s4 = "rmi://localhost:9090";
        String s5 = "rmi://usuario@localhost";
        String s6 = "rmi://usuario@localhost/";
        String s7 = "rmi://usuario@localhost:9090";
        String expResult = "localhost";
        URL instance2 = new URL (s2);
        String result2 = instance2.getHost();
        assertEquals(expResult, result2);
        URL instance3 = new URL (s3);
        String result3 = instance3.getHost();
        assertEquals(expResult, result3);
        URL instance4 = new URL (s4);
        String result4 = instance4.getHost();
        assertEquals(expResult, result4);
        URL instance5 = new URL (s5);
        String result5 = instance5.getHost();
        assertEquals(expResult, result5);
        URL instance6 = new URL (s6);
        String result6 = instance6.getHost();
        assertEquals(expResult, result6);
        URL instance7 = new URL (s7);
        String result7 = instance7.getHost();
        assertEquals(expResult, result7);
    }

    /**
     * Test of getPort method, of class URL.
     */
    //@Test
    public void testGetPort() {
        System.out.println("getPort");
        URL instance = null;
        int expResult = 0;
        int result = instance.getPort();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserInfo method, of class URL.
     */
    //@Test
    public void testGetUserInfo() {
        URL instance = null;
        String expResult = "";
        String result = instance.getUserInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class URL.
     */
    //@Test
    public void testToString() {
        System.out.println("toString");
        URL instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasPort method, of class URL.
     */
    //@Test
    public void testHasPort() {
        System.out.println("hasPort");
        URL instance = null;
        boolean expResult = false;
        boolean result = instance.hasPort();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class URL.
     */
    //@Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        URL instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
