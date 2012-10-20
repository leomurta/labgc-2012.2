/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc;

import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.exception.ApplicationException;
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
            String text = "Texto para verificar compactação de arquivo.";
            file.setContent(text.getBytes());
            file.deflate();
            file.inflate();
            System.out.println(new String(file.getContent()));
        } catch (ApplicationException ex) {
            Logger.getLogger(TestZip.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
}
