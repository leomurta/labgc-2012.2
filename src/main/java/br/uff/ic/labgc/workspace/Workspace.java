/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.exception.WorkspaceDirExisteException;
import br.uff.ic.labgc.exception.WorkspaceDirNaoExisteException;
import br.uff.ic.labgc.exception.WorkspaceEpelhoNaoExisteException;
import br.uff.ic.labgc.exception.WorkspaceException;
import br.uff.ic.labgc.exception.WorkspaceNaoDirException;
import br.uff.ic.labgc.exception.WorkspaceRepNaoExisteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;


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

        throw new UnsupportedOperationException("Not supported yet.");
   
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

        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
boolean copy(File origem,File destino,boolean overwrite)
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
       
        throw new UnsupportedOperationException("Not supported yet.");

 
    }
    
    //comandos de diretorio
    
    boolean add(File file)
    throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
 
    }

public boolean revert(String diretorio)
throws IOException, WorkspaceException {
    
    File diretorio1 = new File(diretorio); 
    if (!diretorio1.exists()) {
        throw new WorkspaceDirNaoExisteException ("ERRO: Diretório inexistente.");

    }
    diretorio1 = new File(diretorio+File.separator+".labgc" );
    
    // testa se existe o diretorio de versionamento
    if (!diretorio1.exists()) {
        throw new WorkspaceRepNaoExisteException ("ERRO: Não existe repositório.");
    }
    // procura pelo espelho - não existe um dir padrão pq extensão é a versão
    File[] stDir = diretorio1.listFiles();
    boolean achou = false;
    for (File file : stDir) { 
        String name = file.getName();
        String extensao = name.substring(name.lastIndexOf("."), name.length());
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        if (name == "espelho"){
            diretorio1=new File(diretorio+File.separator+name+extensao);
            achou = true;
        }
    }
    if (!achou) {
        throw new WorkspaceEpelhoNaoExisteException ("ERRO: Não existe espelho.");
    }
    stDir = diretorio1.listFiles();
    // copia os arquivos
    for (File file : stDir) { 
        String name = file.getName();
        copy(file, new File(diretorio+"\\"+name),true);  
         }     
    // se tudo deu certo    
        return true;
}

String status() {
        throw new UnsupportedOperationException("Not supported yet.");
  
    }

boolean release() {
        throw new UnsupportedOperationException("Not supported yet.");

    }

boolean resolve(File file) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

// Cria esqueleto da WorkSpace
// diretorio = diretorio completo do projeto, versao=versao do projeto
// repositorio=caminho do repositorio, login=usuario

public static void criaWorkSpace(File diretorio, String versao, String repositorio, String login)
throws IOException, WorkspaceException {
    if (!diretorio.exists()) {
        diretorio.mkdirs();
    }
    else{
        throw new WorkspaceDirExisteException ("ERRO: Diretório existente.");
    }
    
    // cria diretorio de controle
    File vcs = new File (diretorio, ".labgc");
    vcs.mkdir();
    
    // cria diretorio espelho da versao atual
    File espelho = new File (vcs, "espelho.r"+versao);
    espelho.mkdir();
    
    // guardando este lixo pq eu entendi melhor assim. Depois eu tiro.
        //File raiz = new File (vcs, "repositorio");
        //raiz.createNewFile();
        //FileWriter raizarq = new FileWriter(raiz, false);
        //PrintWriter out = new PrintWriter(raizarq);
        //out.println(repositorio);
        //out.close ();
    
    // cria arquivo repositorio com o caminho do repositorio remoto
    PrintWriter out = new PrintWriter(new FileWriter(new File (vcs, "repositorio")));
    out.println(repositorio);
    out.close ();
}

}