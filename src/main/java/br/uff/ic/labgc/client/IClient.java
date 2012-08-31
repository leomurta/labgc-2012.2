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
    
    //comandos basicos
    
    boolean commit(File file, String message);
    boolean release();
    List<File> update();
    List<File> checkout();
    String diff(File file, String version);
    
    //comandos similares ao OS
    
    boolean add(File file);
    boolean remove(File file);
    boolean move(File file, String dest);
    boolean copy(File file, String dest);
    boolean mkdir(String name);
    
    //comandos do diretorio
    
    boolean revert(File file);
    String status();
    String log();
    boolean resolve(File file);
    
}
