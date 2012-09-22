/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;


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
    
public static boolean copy(File origem,File destino,boolean overwrite)
throws FileNotFoundException, IOException {  
      if (destino.exists() && !overwrite)
         return false;  
     
      FileInputStream   fisOrigem = new FileInputStream(origem);  
      FileOutputStream fisDestino = new FileOutputStream(destino);  
      FileChannel fcOrigem = fisOrigem.getChannel();    
      FileChannel fcDestino = fisDestino.getChannel();    
      fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);    
      fisOrigem.close();    
      fisDestino.close();  
      return true;
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

public boolean revert(String diretorio)
throws IOException, WorkspaceException {
    
    File diretorio1 = new File(diretorio); 
    if (!diretorio1.exists()) {
        throw new WorkspaceDirNaoExisteException ("ERRO: Diretório inexistente.");

    }
        diretorio1 = new File(diretorio+File.separator + "vcs" ); 
    // testa se existe o diretorio de versionamento
    if (!diretorio1.exists()) {
        throw new WorkspaceRepNaoExisteException ("ERRO: Não existe repositório.");
       
    }
    // ler o diretório de controle vcs e cria um array com o conteúdo do diretório
    File[] stDir = diretorio1.listFiles();
        
    // copia os arquivos
    for (File file : stDir) { 
        String name = file.getName();
        String extensao = name.substring(name.lastIndexOf("."), name.length());
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        if (extensao == ".v"){
                copy(file, new File(diretorio+"\\"+name),true);  
         } 
    }
        
    // se tudo deu certo    
        return true;
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
public void createWorkspace(String Raiz, String Repositorio)
throws IOException, WorkspaceException {
    /*if (!diretorio.exists()) {
        diretorio.mkdirs();
    }
    else{
        throw new WorkspaceDirExisteException ("ERRO: Diretório existente.");
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
	out.close ();*/
}

//implementar
    public boolean canCreate(){
        return false;
    }
    public void storeLocalData(List<VersionedItem> items){
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setParam(String key, String value) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}