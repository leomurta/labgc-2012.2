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
    boolean commit(List<File> file, String message);
    List<File> update();
    List<File> checkout();
    String diff(File file, String version);
    String log();
    
}
