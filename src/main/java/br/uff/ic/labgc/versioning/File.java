/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.versioning;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author jokerfvd
 */
public class File {
    private String path;
    private FileInputStream stream;

    public String getPath() {
        return path;
    }
    
    public FileInputStream getStream() throws FileNotFoundException{
        if (stream == null)
            stream = new FileInputStream(path);
        return stream;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
