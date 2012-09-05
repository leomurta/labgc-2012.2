/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.workspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 *
 * @author vyctorh
 */
public class IWorkspace {
    
    /**
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean remove(File file) 
    throws FileNotFoundException, IOException {

        return false;
   
    }


    /**
     *
     * @param file
     * @param dest
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean move(File file, String dest) 
    throws FileNotFoundException, IOException  
{

        return false;
        
    }


    boolean copy(File file, String dest)
    throws FileNotFoundException, IOException  {

        return false;
 
    }


    boolean mkdir(String name)
    throws IOException  {
        return false;

 
    }
    
    //comandos de diretorio
    
    boolean add(File file)
    throws IOException  {
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
