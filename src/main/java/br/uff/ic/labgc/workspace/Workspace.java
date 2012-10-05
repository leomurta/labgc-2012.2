/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.WorkspaceDirExisteException;
import br.uff.ic.labgc.exception.WorkspaceDirNaoExisteException;
import br.uff.ic.labgc.exception.WorkspaceEpelhoNaoExisteException;
import br.uff.ic.labgc.exception.WorkspaceException;
import br.uff.ic.labgc.exception.WorkspaceRepNaoExisteException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
*
* @author vyctorh
*/

public class Workspace implements IObservable{
    private String LocalRepo;
    
    
    
    public Workspace (String LocalRepo){
        this.LocalRepo = LocalRepo;
    }
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
      if (destino.exists() && !overwrite) {
        return false;
    }  
      
      // FileChannel realiza as operações de leitura e gravação 
      // com o máximo de eficiência otimizando operação de transferência de dados.
      // Foi o que eu li pelo menos =]
      
      // Cria a stream para ler o arquivo original
      FileInputStream   fisOrigem = new FileInputStream(origem); 
      // Cria a stream para gravar o arquivo de destino
      FileOutputStream fisDestino = new FileOutputStream(destino);
      // Usa as streams para criar os canais correspondentes
      FileChannel fcOrigem = fisOrigem.getChannel();    
      FileChannel fcDestino = fisDestino.getChannel();
      // Transfere todo o volume para o arquivo de cópia com o número de bytes do arquivo original
      fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
      // Fecha
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

public boolean revert()
throws IOException, WorkspaceException {
    String diretorio = getParam("Diretorio");
    
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

public String status() {
        throw new UnsupportedOperationException("Not supported yet.");
  
    }

public boolean release() {
        throw new UnsupportedOperationException("Not supported yet.");

    }

public boolean resolve(File file) {
        throw new UnsupportedOperationException("Not supported yet.");

    }


// Cria esqueleto da WorkSpace
// diretorio = diretorio completo do projeto, versao=versao do projeto
// repositorio=caminho do repositorio, login=usuario

public void createWorkspace(String hostname, String repository)
throws WorkspaceException {
    File diretorio1 = new File (LocalRepo);
    if (!diretorio1.exists()) {
        diretorio1.mkdirs();
    }
    else{
        throw new WorkspaceDirExisteException ("ERRO: Diretório existente.");
    }
    
    // cria diretorio de controle
    File vcs = new File (LocalRepo, ".labgc");
    vcs.mkdir();
    
    // cria diretorio espelho da versao atual
   /*  File espelho = new File (vcs, "espelho.r"+versao);
    espelho.mkdir();*/
    
    // guardando este lixo pq eu entendi melhor assim. Depois eu tiro.
        //File raiz = new File (vcs, "repositorio");
        //raiz.createNewFile();
        //FileWriter raizarq = new FileWriter(raiz, false);
        //PrintWriter out = new PrintWriter(raizarq);
        //out.println(repositorio);
        //out.close ();
    
    // cria arquivo repositorio com o caminho do repositorio remoto
    PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(new File (vcs, "repositorio")));
            out.println(hostname);
            out.close ();
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            throw new WorkspaceException ("ERRO: gravando arquivo repositorio.", ex);
        }
    
}
/**
     * verifica a possibilidade de criar um  workspace, retorna true ou false
     * @return 
     */
// pode criar diretório - true: pode criar e false: existe diretório

    public boolean canCreate()
    throws WorkspaceException, IOException {
        
        File dirtemp = new File (LocalRepo);
        if (! dirtemp.exists()) {
            return true;
        }
        else {
            return false;
        }
    }
    /*
     * setParam - coloca em um arquivo um par chave/valor
     */
public void setParam(String key, String value) 
throws WorkspaceException,IOException {
    File vcs = new File (LocalRepo, ".labgc");
    File file = new File(vcs,"labgc.properties");
    if (!file.exists()) {
        file.createNewFile();
    }
    Properties properties = new Properties();
    properties.setProperty(key, value);
    properties.store(new FileOutputStream(file), null);
    }


//implementar
    /**
     * salva arquivos versionados, do servidor, para o workspace(disco local)
     * @param items, arquivo de itens versionados, sendo que o conjunto contem arquivos e pastas
     */
    public void storeLocalData(VersionedItem items){
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
     /**
     * metodo para pegar o valor de um parametro salvo
     * @param key, chave do parametro salvo
     * @return 
     */
    public String getParam(String key) 
    throws WorkspaceException, IOException {
      
        File vcs = new File (LocalRepo, ".labgc");
        File file = new File(vcs,"labgc.properties");
    
        Properties properties = new Properties();
        FileInputStream fi = new FileInputStream( file );
        properties.load( fi );
        fi.close();
        return properties.getProperty(key);
    }
    /**
     * retorna valor do hostname guardado na criacao do workspace
     * @return 
     */
    public String getHostname() throws WorkspaceException {
        String chave="";
        try {
            chave = getParam( "Hostname" );
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chave;
    }
    
    /**
     * retorna o valor do relacionado ao repositorio do projeto no servidor. Valor adicionado na criacao do workspace
     * @return 
     */
    public String getRepository() throws WorkspaceException {
        String chave="";
        
      
        try {
            chave = getParam( "Repository" );
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chave;
    }
    /**
     * verifica se o localRepo e um workspace valido e inicializado
     * @return 
     */
    public boolean isWorkspace() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    


    /**
     * metodo da interface IObservable para adicionar observadores
     * @param obs 
     */
    @Override
    public void registerInterest(IObserver obs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
   
}