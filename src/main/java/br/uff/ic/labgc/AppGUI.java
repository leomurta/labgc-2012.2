package br.uff.ic.labgc;

import br.uff.ic.labgc.comm.server.CommunicationServer;
import br.uff.ic.labgc.userInterface.gui.PrincipalJFrame;
import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * Hello world!
 *
 */
public class AppGUI {

    public static void main(String[] args) throws IOException 
    {
        PrincipalJFrame princ = new PrincipalJFrame();
        //new CommunicationServer();
        SetParameters();
        princ.setVisible(true);
    }

    private static void SetParameters() throws IOException 
    {
         ClassLoader cl = AppCLI.class.getClassLoader();
        URL policyURL = cl.getResource("evcs.policy");
        
        String jar = policyURL.toString().split("!/")[0]+"!/";
        File p = new File(policyURL.toString().split("file:/")[1].split("!")[0]);
        File f = new File(p.getAbsolutePath());
        String url = f.getParent();
        
        //System.setProperty("java.security.policy",policyURL.toString());
        //System.setProperty("java.rmi.server.codebase",jar);
        
        System.setProperty("user.dir",url);
        
       
        
        f = new File("..\\repositorio");//pegar de um xml de configuração
        
        String strRepo = f.getCanonicalPath();
        strRepo =strRepo.replace("\\target", "");
        strRepo +="\\";
        System.setProperty("br.uff.ic.labgc.repository",strRepo);
    }
}
