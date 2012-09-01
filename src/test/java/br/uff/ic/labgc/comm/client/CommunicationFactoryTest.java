/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.properties.IPropertiesConstants;
import br.uff.ic.labgc.server.IServer;
import java.util.Locale;
import java.util.ResourceBundle;
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
public class CommunicationFactoryTest {

    private ResourceBundle bundle;

    public CommunicationFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        bundle = ResourceBundle.getBundle(IPropertiesConstants.PROPERTIES_FILE_NAME,
                Locale.getDefault(), ClassLoader.getSystemClassLoader());

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getServer method, of class CommunicationFactory.
     */
    @Test
    public void testGetServer() {
        System.out.println("getServer");
        CommunicationFactory instance = CommunicationFactory.getFactory();
        String expResult = bundle.getString(IPropertiesConstants.SERVER_CLASS);
        String result = instance.getServer().getClass().getName();
        assertEquals(expResult, result);
    }
}
