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
    public boolean commit(VersionedItem file, String message);
    public VersionedItem update();
    public VersionedItem checkout(String revision, String token)throws ServerException;
    public String login(String user, String pwd)throws ServerException;
    public String diff(VersionedItem file, String version);
    public String log();
    public String getRepPath();
    public String getRepHost();
    public byte[] getItemContent(String hash) throws ServerException;
}
