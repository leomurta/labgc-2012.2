/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.ApplicationException;
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
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;

/**
 *
 * @author vyctorh
 */
public class Workspace implements IObservable {

    private String LocalRepo;
    private IObserver observer;

    public Workspace(String LocalRepo) {
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
            throws FileNotFoundException, IOException {

        throw new UnsupportedOperationException("Not supported yet.");

    }

    boolean copy(File origem, File destino, boolean overwrite)
            throws FileNotFoundException, IOException {
        if (destino.exists() && !overwrite) {
            return false;
        }

        // FileChannel realiza as operações de leitura e gravação 
        // com o máximo de eficiência otimizando operação de transferência de dados.
        // Foi o que eu li pelo menos =]

        // Cria a stream para ler o arquivo original
        FileInputStream fisOrigem = new FileInputStream(origem);
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
            throw new WorkspaceDirNaoExisteException("ERRO: Diretório inexistente.");

        }
        diretorio1 = new File(diretorio + File.separator + ".labgc");

        // testa se existe o diretorio de versionamento
        if (!diretorio1.exists()) {
            throw new WorkspaceRepNaoExisteException("ERRO: Não existe repositório.");

        }
        // procura pelo espelho 
        File[] stDir = diretorio1.listFiles();
        boolean achou = false;
        for (File file : stDir) {
            String name = file.getName();
            String extensao = name.substring(name.lastIndexOf("."), name.length());
            int pos = name.lastIndexOf(".");
            if (pos > 0) {
                name = name.substring(0, pos);
            }
            if (name == "espelho") {
                diretorio1 = new File(diretorio + File.separator + name + extensao);
                achou = true;
            }
        }
        if (!achou) {
            throw new WorkspaceEpelhoNaoExisteException("ERRO: Não existe espelho.");
        }
        stDir = diretorio1.listFiles();
        // copia os arquivos
        for (File file : stDir) {
            String name = file.getName();
            copy(file, new File(diretorio + "\\" + name), true);
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
    
    public void createWorkspace(String hostname, String repository, VersionedItem items)
            throws ApplicationException {

        //pega os items, grava os arquivos no disco e 
        //grava a pasta de controle dentro da pasta do projeto

        File local = new File(LocalRepo);
        File parent = new File(local.getParent());

        try {
            this.writeVersionedDir((VersionedDir) items, parent);
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            throw new WorkspaceException("Não foi possivel gravar arquivos no disco");
        }

        // cria diretorio de controle
        File vcs = new File(local, ".labgc");
        vcs.mkdir();

        // cria diretorio espelho da versao atual
        File espelho;
        espelho = new File(vcs, "espelho.r");
        espelho.mkdir();
        
        // cria filtro para excluir .labgc
        
        FilenameFilter excluiLabgc = new FilenameFilter() {
            public boolean accept(File parent, String name){
                   return !name.endsWith(".labgc");
            }
        };
        
        // copia WS para o espelho e exclui .labgc
        
        File[] stDir = parent.listFiles(excluiLabgc);
        
        // copia os arquivos
        for (File file : stDir) {
            String name = file.getName();
            // tratar .labgc - não pode copiar
        try {
             copy(file, new File(espelho + "\\" + name), true);
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            throw new WorkspaceException("Não foi possivel gravar arquivos no disco");
        }
           
        }
        
        setParam("repositorio", repository);
        setParam("hostname", hostname);

    }

    /**
     * verifica a possibilidade de criar um workspace, retorna true ou false
     *
     * @return
     */
// pode criar diretório - true: pode criar e false: existe diretório
    public boolean canCreate() {
        try {
            File file = new File(this.LocalRepo);
            this.checkLabgcFolder(file.getAbsolutePath());
        } catch (WorkspaceException ex) {
            return true;
        }
        return false;
    }
    /*
     * setParam - coloca em um arquivo um par chave/valor
     */

    public void setParam(String key, String value)
            throws WorkspaceException {
        File vcs = new File(this.LocalRepo, ".labgc");
        File file = new File(vcs, "labgc.properties");
        Properties properties = new Properties();
        if (file.exists()) {
            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                properties.load(fis);
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
                throw new WorkspaceException("nao foi possivel abrir arquivo de propriedades");
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
                throw new WorkspaceException("nao foi possivel criar");
            }
        }

        properties.setProperty(key, value);
        try {
            properties.store(new FileOutputStream(file), null);
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            throw new WorkspaceException("nao foi possivel gravar");
        }
    }

//implementar
    
    
    /**
     * salva arquivos versionados, do servidor, para o workspace(disco local)
     *
     * @param items, arquivo de itens versionados, sendo que o conjunto contem
     * arquivos e pastas
     */
    public void storeLocalData(VersionedItem items) {
    }

    private void writeVersionedDir(VersionedDir dir, File folder) throws IOException, ApplicationException {
        File directory = new File(folder, dir.getName());
        directory.mkdir();

        for (VersionedItem item : dir.getContainedItens()) {
            if (item instanceof VersionedDir) {
                writeVersionedDir((VersionedDir) item, directory);
            } else {
                writeVersionedFile((VersionedFile) item, directory);
            }
        }
    }

    private void writeVersionedFile(VersionedFile f, File folder) throws IOException, ApplicationException {

        File file = new File(folder, f.getName());
        file.createNewFile();

        byte[] content;
            
        content = f.getContent();
        FileOutputStream fileWriter = new FileOutputStream(file);
        fileWriter.write(content);
        fileWriter.close();
        this.notifyObservers(file.getPath());
            
        
    }

    /**
     * metodo para pegar o valor de um parametro salvo
     *
     * @param key, chave do parametro salvo
     * @return
     */
    public String getParam(String key)
            throws WorkspaceException {

        File vcs = new File(this.LocalRepo, ".labgc");
        File file = new File(vcs, "labgc.properties");

        Properties properties = new Properties();
        FileInputStream fi;
        try {
            fi = new FileInputStream(file);
            properties.load(fi);
            fi.close();
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            throw new WorkspaceException("Erro ao manipular o arquivo de parametro");
        }

        return properties.getProperty(key);
    }

    /**
     * retorna valor do hostname guardado na criacao do workspace
     *
     * @return
     */
    public String getHostname() throws WorkspaceException {
        String chave = "";

        chave = getParam("hostname");

        return chave;
    }

    /**
     * retorna o valor do relacionado ao repositorio do projeto no servidor.
     * Valor adicionado na criacao do workspace
     *
     * @return
     */
    public String getRepository() throws WorkspaceException {
        String chave = "";

        chave = getParam("repositorio");

        return chave;
    }

    /**
     * verifica se o localRepo e um workspace valido e inicializado
     *
     * @return
     */
    public boolean isWorkspace() {
        try {
            this.checkLabgcFolder(this.LocalRepo);
            this.checkLabgcFile(this.LocalRepo);
        } catch (WorkspaceException ex) {
            return false;
        }
        return true;
    }

    private void checkLabgcFolder(String path) throws WorkspaceException {

        File diretorio1 = new File(path, ".labgc");
        if (!diretorio1.exists()) {
            throw new WorkspaceEpelhoNaoExisteException(null);
        }

    }

    private void checkLabgcFile(String path) throws WorkspaceException {

        File arquivo = new File(path, ".labgc//labgc.properties");
        if (!arquivo.exists()) {
            throw new WorkspaceEpelhoNaoExisteException(null);
        }

    }

    private void checkPropertyFile(String path) throws WorkspaceException {

        File arquivo = new File(path, ".labgc//key.properties");
        if (!arquivo.exists()) {
            throw new WorkspaceEpelhoNaoExisteException(null);
        }

    }

    /**
     * metodo da interface IObservable para adicionar observadores
     *
     * @param obs
     */
    @Override
    public void registerInterest(IObserver obs) {

        this.observer = obs;

    }

    private void notifyObservers(String msg) {
        if (this.observer != null) {
            this.observer.sendNotify(msg);
        }
    }
    private VersionedItem criarItem(String dir1){
        File raiz = new File(dir1);
        File[] stDir = raiz.listFiles();
       
        // copia os arquivos
        for (File file : stDir) {
            String name = file.getName();
        }
        return null;
        }
    
}



