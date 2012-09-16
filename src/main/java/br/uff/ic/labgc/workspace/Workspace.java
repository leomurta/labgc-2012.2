/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.exception.WorkspaceException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
*
* @author vyctorh
*/

public class Workspace {
    
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
    throws FileNotFoundException, IOException {

        return false;
 
    }


    boolean mkdir(String name)
    throws IOException {
        return false;

 
    }
    
    //comandos de diretorio
    
    boolean add(File file)
    throws IOException {
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
// diretorio = diretorio raiz do projeto e raiz=caminho da raiz do repositorio e repositorio=caminho dentro da raiz
public static void criaWorkSpace(File diretorio, String Raiz, String Repositorio)
throws IOException, WorkspaceException {
    if (!diretorio.exists()) {
        diretorio.mkdirs();
    }
    else{
        throw new WorkspaceException ("ERRO: Diretório existente.");
    }
       	File vcs = new File (diretorio, "vcs");
	if (!vcs.exists()) {
        vcs.mkdir();
    }
                
        File entrada = new File (vcs, "Entrada");
	entrada.createNewFile();
                        
	File raiz = new File (vcs, "raiz");
	raiz.createNewFile();
        FileWriter raizarq = new FileWriter(raiz, false);
	PrintWriter out = new PrintWriter(raizarq);
	out.println(Raiz);
	out.close ();
                
	out = new PrintWriter(new FileWriter(new File (vcs, "repositorio")));
	out.println(Repositorio);
	out.close ();
}

}