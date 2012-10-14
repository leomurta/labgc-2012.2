package br.uff.ic.labgc;

import br.uff.ic.labgc.comm.server.CommunicationServer;
import br.uff.ic.labgc.userInterface.gui.PrincipalJFrame;


/**
 * Hello world!
 *
 */
public class AppGUI {

    public static void main(String[] args) 
    {
        PrincipalJFrame princ = new PrincipalJFrame();
        new CommunicationServer();
        princ.setVisible(true);
    }
}
