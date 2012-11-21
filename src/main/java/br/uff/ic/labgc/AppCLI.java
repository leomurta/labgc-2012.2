package br.uff.ic.labgc;

import br.uff.ic.labgc.comm.server.CommunicationServer;
import br.uff.ic.labgc.exception.ClientWorkspaceUnavailableException;
import br.uff.ic.labgc.userInterface.cli.Cli;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
 *
 */
public class AppCLI {

    public static void main(String[] args) {
        try {
            String mode = System.getProperty("br.uff.ic.labgc.mode");
            if ("server".equals(mode)) {
                new CommunicationServer();
            } else {
                String invocationPath = System.getProperty("br.uff.ic.labgc.invocation");
                new Cli(invocationPath).run(args);
            }
        } catch (ParseException ex) {
            Logger.getLogger(AppCLI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClientWorkspaceUnavailableException ex) {
            Logger.getLogger(AppCLI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * O método funciona verificando caminhos relativos ao classLoader e onde o comando foi invocado.
     * O objetivo é a ferramenta ser chamada da seguinte maneira:
     * java -Dbr.uff.ic.labgc.mode=server -> opcional para servidor
     * -jar ...\labgc-2012.2.jar -comando ...parametros
     * @throws IOException 
     */
    private static void relativePaths() throws IOException{
        ClassLoader cl = AppCLI.class.getClassLoader();
        URL policyURL = cl.getResource("evcs.policy");
        
        String jar = policyURL.toString().split("!/")[0]+"!/";
        File p = new File(policyURL.toString().split("file:/")[1].split("!")[0]);
        File f = new File(p.getAbsolutePath());
        String url = f.getParent();
        
        System.setProperty("java.security.policy",policyURL.toString());
        System.setProperty("java.rmi.server.codebase",jar);
        
        if(System.getProperty("br.uff.ic.labgc.mode") == null)
            System.setProperty("br.uff.ic.labgc.mode","client");
        
        if(System.getProperty("java.rmi.server.hostname") == null)
            System.setProperty("java.rmi.server.hostname","localhost");
        
        System.setProperty("br.uff.ic.labgc.invocation",System.getProperty("user.dir"));
        
        System.setProperty("user.dir",url);
        
        f = new File("..\\repositorio");//pegar de um xml de configuração
        
        System.setProperty("br.uff.ic.labgc.repository",f.getCanonicalPath());
        
    }
}
