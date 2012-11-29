/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.userInterface.cli;

import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ClientWorkspaceUnavailableException;
import java.io.File;
import java.lang.reflect.Method;
import org.apache.commons.cli.ParseException;
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
public class CliTest {
    
    public static class MyCli extends Cli { 
        public MyCli(String invocationPath) {
            super(invocationPath);
        }
        
        @Override
        public void run(String[] logArg) throws ParseException, ClientWorkspaceUnavailableException{
            super.run(logArg);
        }
    }

    
    public CliTest() {
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
     * Test of Cli method, of class Cli.
     */
    //@Test
    public void testCli() {
        System.out.println("Cli");
        Cli instance = null;
        instance.Cli();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compare method, of class Cli.
     */
    //@Test
    public void testCompare() {
        System.out.println("compare");
        VersionedItem object1 = null;
        VersionedItem object2 = null;
        Cli instance = null;
        boolean expResult = false;
        boolean result = instance.compare(object1, object2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class Cli.
     */
    //@Test
    public void testRun() throws Exception {
        System.out.println("run");
        String[] args = null;
        Cli instance = null;
        instance.run(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendNotify method, of class Cli.
     */
    //@Test
    public void testSendNotify() {
        System.out.println("sendNotify");
        String strNotify = "";
        Cli instance = null;
        instance.sendNotify(strNotify);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testRunLog() throws Exception {
        System.out.println("runLog");      
        String invocationPath = System.getProperty("user.dir")+File.separator+"launchers"
                +File.separator+"windows"+File.separator;
        MyCli cli = new MyCli(invocationPath);
        String[] args = "cli -log projeto1".split(" ");
        cli.run(args);
    }
}
