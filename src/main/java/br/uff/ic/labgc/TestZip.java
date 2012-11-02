/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc;

import br.uff.ic.labgc.comm.client.RMIConnector;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.CommunicationException;
import br.uff.ic.labgc.exception.CompressionException;
import br.uff.ic.labgc.exception.ContentNotAvailableException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristiano
 */
public class TestZip {
    public static void main(String args[]) {
        try {
            VersionedFile file = new VersionedFile();
            file.setContent("meu conteúdo".getBytes());
            System.out.println(new String(file.getContent()));
            file.deflate();
            System.out.println(new String(file.getContent()));
            file.inflate();
            System.out.println(new String(file.getContent()));
            
        } catch (ApplicationException ex) {
            Logger.getLogger(TestZip.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
}
