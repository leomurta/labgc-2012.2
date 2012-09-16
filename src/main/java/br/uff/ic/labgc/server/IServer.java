/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import java.io.*;
import java.util.*;

/**
 *
 * @author Felipe R
 */
public interface IServer {
    public void registerRepository(String repHost, String repName);
    public boolean commit(List<File> file, String message);
    public List<File> update();
    public List<File> checkout();
    public String diff(File file, String version);
    public String log();
    public String getRepPath();
    public String getRepHost();
    
}
