/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.client;

import java.io.*;
import java.util.*;

/**
 *
 * @author Felipe R
 */
public interface IClient {
    
    //comandos server
    
    boolean commit(List<File> files, String message);
    List<File> update();
    List<File> checkout();
    String diff(File file, String version);
    String log();
    
    //comandos similares ao OS
    
    boolean remove(File file);
    boolean move(File file, String dest);
    boolean copy(File file, String dest);
    boolean mkdir(String name);
    
    //comandos do diretorio
    
    boolean add(File file);
    boolean revert(File file);
    String status();
    boolean release();
    boolean resolve(File file);
    
}
