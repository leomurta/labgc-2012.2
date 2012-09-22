/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.*;
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
    public List<VersionedItem> checkout(int revision, String token)throws ServerException;
    public String login(String user, String pwd)throws ServerException;
    public String diff(File file, String version);
    public String log();
    public String getRepPath();
    public String getRepHost();
    
}
