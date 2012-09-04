/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.workspace;

import java.io.File;

/**
 *
 * @author vyctorh
 */
public class IWorkspace {
    
boolean remove(File file) {
        return false;
   
    }

boolean move(File file, String dest) {
        return false;
        
    }

boolean copy(File file, String dest) {
        return false;
 
    }

boolean mkdir(String name) {
        return false;
 
    }
    
    //comandos de diretorio
    
boolean add(File file) {
        return false;
 
    }

boolean revert(File file) {
        return false;
 
    }

String status() {
        return null;
  
    }

boolean release() {
        return false;

    }

boolean resolve(File file) {
        return false;

    }
    
}
