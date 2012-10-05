package br.uff.ic.labgc;

import br.uff.ic.labgc.comm.server.CommunicationServer;
import br.uff.ic.labgc.exception.ClientWorkspaceUnavailableException;
import br.uff.ic.labgc.userInterface.cli.Cli;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        try {
            String mode = System.getProperty("br.uff.ic.labgc.mode");
            if ("server".equals(mode)) {
                new CommunicationServer();
            } else {
                new Cli().run(args);
            }
        } catch (ParseException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClientWorkspaceUnavailableException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
