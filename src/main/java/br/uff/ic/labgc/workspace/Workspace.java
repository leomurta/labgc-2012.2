/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.workspace;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.WorkspaceDirNaoExisteException;
import br.uff.ic.labgc.exception.WorkspaceEpelhoNaoExisteException;
import br.uff.ic.labgc.exception.WorkspaceException;
import br.uff.ic.labgc.exception.WorkspaceRepNaoExisteException;
import br.uff.ic.labgc.util.VersionedItemUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vyctorh
 */
public class Workspace implements IWorkspace {

    private String workspaceDir;
    private IObserver observer;
    private String PROPERTIES_FILE = "labgc.properties";
    private String PROPERTY_REVISION = "revision";
    private String PROPERTY_HOST = "hostname";
    private String PROPERTY_PROJECT = "repository";
    private String WS_FOLDER = ".labgc";
    private String ESPELHO = "espelho.r";

    public Workspace(String LocalRepo) {
        this.workspaceDir = LocalRepo; //caminho do projeto gravado na WS
    }
    
    // <editor-fold defaultstate="collapsed" desc="Propriedades do Workspace">
    public void setParam(String key, String value)throws WorkspaceException {
        if(key == null)
            throw new WorkspaceException("Valor de chave nula");
        if(value == null)
            throw new WorkspaceException("Valor de valor nulo");
        setProperty(key, value);
    }

    public String getParam(String key) throws WorkspaceException {
        if(key == null)
            throw new WorkspaceException("Valor de chave nula");
        return getProperty(key);

    }

    public String getHost() throws WorkspaceException {
        return getProperty(PROPERTY_HOST);
    }

    public String getProject() throws WorkspaceException {
        return getProperty(PROPERTY_PROJECT);
    }

    public String getRevision() throws WorkspaceException {
        return getProperty(PROPERTY_REVISION);
    }

    public void setRevision(String revision) throws WorkspaceException {
        if(revision == null) 
            throw new WorkspaceException("Valor de revisao nula");
        setProperty(PROPERTY_REVISION, revision);
    }
    
    //primitivas locais para os Métodos de propriedades
    private void setProperty(String chave, String valor) throws WorkspaceException {
        File vcs = new File(this.workspaceDir, WS_FOLDER);
        File file = new File(vcs, PROPERTIES_FILE);
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

        properties.setProperty(chave,valor);
        try {
            properties.store(new FileOutputStream(file), null);
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            throw new WorkspaceException("nao foi possivel gravar");
        }
    }

    private String getProperty(String chave) throws WorkspaceException {
        File vcs = new File(this.workspaceDir, WS_FOLDER);
        File file = new File(vcs, PROPERTIES_FILE);

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

        return properties.getProperty(chave);
    }
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Métodos de verificação do WS">
    public boolean canCreate() {
        try {
            File file = new File(this.workspaceDir);
            this.checkLabgcFolder(file.getAbsolutePath());
        } catch (WorkspaceException ex) {
            return true;
        }
        return false;
    }
    
    public boolean isWorkspace() {
        try {
            this.checkLabgcFolder(this.workspaceDir);
            this.checkLabgcFile(this.workspaceDir);
        } catch (WorkspaceException ex) {
            return false;
        }
        return true;
    }

    public void createWorkspace(String hostname, String repository, VersionedItem items)
            throws ApplicationException {

        //pega os items, grava os arquivos no disco e 
        //grava a pasta de controle dentro da pasta do projeto

        File local = new File(workspaceDir);
        File controle = new File(local, WS_FOLDER);
        File espelho = new File(controle, ESPELHO);

        local.mkdir();
        controle.mkdir();
        espelho.mkdir();
        
        setProperty(PROPERTY_PROJECT, repository);
        setProperty(PROPERTY_HOST, hostname);
        setRevision(items.getLastChangedRevision());
        
        IObserver observer = new IObserver() {
            @Override
            public void sendNotify(String path) {
                notifyObservers(path);
            }
        };
        
        VersionedItemUtils.write(local, ((VersionedDir) items).getContainedItens(), new IObserver[]{observer});
        VersionedItemUtils.write(espelho, ((VersionedDir) items).getContainedItens());
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funcionalidades da WS">
    public boolean revert(String fileOrDir)
            throws WorkspaceException {

        File local = new File(workspaceDir);
        File parent = new File(local.getParent());
        File target = new File(fileOrDir);
        try {
            // testa se o caminho está dentro do repositório
            if (!isSubDir(parent, target)) {
                throw new WorkspaceRepNaoExisteException("ERRO: Alvo não está no repositório.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        // testa se é versionado
        File diretorio1 = new File(local + File.separator + WS_FOLDER );
        if (!diretorio1.exists()) {
            throw new WorkspaceRepNaoExisteException("ERRO: Não existe repositório.");
        }
        // procura pelo espelho
        diretorio1 = new File(local + File.separator + WS_FOLDER +File.separator + ESPELHO);
        if (new File(diretorio1.getAbsolutePath()+ESPELHO).exists()) {
            throw new WorkspaceEpelhoNaoExisteException("ERRO1: Não existe espelho.");
        }
        
        // testa se for arquivo copia por cima
        //extrair caminho relativo à baseDir    ---> (parent)
        Path baseDir = Paths.get(parent.toString());
        Path targetDir = Paths.get(target.toString());
        int countdir = baseDir.getNameCount();
        int max = targetDir.getNameCount();
        Path relativeDir = targetDir.subpath(countdir, max);
        File d = relativeDir.toFile();
        //adicionar caminho relativo ao espelho
        File file2 = new File(diretorio1 + File.separator, d.toString());
        //buscar o arquivo origem no espelho - testar se existe.
        if (!file2.exists()) {
            throw new WorkspaceException("ERRO: Não existe arquivo no espelho.");
        } else {
            if (target.isFile()) { //grava direto por cima
                try {
                    copy(file2, target, true);
                } catch (FileNotFoundException ex) {
                     Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
                    throw new WorkspaceException("Erro gravando cópia do arquivo.");
                } catch (IOException ex) {
                    Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
                    throw new WorkspaceException("IO Exception.");
                }
            } else {// grava o diretório
                deleteDir(target);
                try {
                    copyDir(file2, target);
                } catch (IOException ex) {
                    Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
                    throw new WorkspaceException("Não foi possivel gravar arquivos no disco");
                }
            }
        }
        return true;
    }

    public boolean revert()
            throws ApplicationException {
        File local = new File(workspaceDir);
        File diretorio1 = new File(local + File.separator + WS_FOLDER );

        // testa se existe o diretorio de versionamento
        if (!diretorio1.exists()) {
            throw new WorkspaceRepNaoExisteException("ERRO: Não existe repositório.");
        }
        // procura pelo espelho 
       
        diretorio1 = new File(local + File.separator + WS_FOLDER +File.separator + ESPELHO);
        if (new File(diretorio1.getAbsolutePath()+ESPELHO).exists()) {
            throw new WorkspaceEpelhoNaoExisteException("ERRO1: Não existe espelho.");
        }
        deleteDir(local);
        
        VersionedDir pristine = new VersionedDir();       
        String exclusions[] = {WS_FOLDER};
        pristine.addItem(VersionedItemUtils.read(diretorio1));       
        VersionedItemUtils.write(local,pristine.getContainedItens());
        
        // se tudo deu certo   
        return true;
    }
    
    public boolean release() {
        throw new UnsupportedOperationException("Not supported yet.");
        //VRF se tem commit pendente para poder deletar WS
    }
    
    public boolean resolve(String file) {
        throw new UnsupportedOperationException("Not supported yet.");

    }
    
    public VersionedItem status()
            throws ApplicationException {
        File local = new File(workspaceDir);
        File mirror = new File(local, WS_FOLDER + File.separator + ESPELHO);
        
        VersionedDir root = new VersionedDir(); 
        VersionedDir working = new VersionedDir();
        VersionedDir pristine = new VersionedDir();
                
        String exclusions[] = {WS_FOLDER};
        working.addItem(VersionedItemUtils.read(local, exclusions, false));//false nao le o conteudo
        pristine.addItem(VersionedItemUtils.read(mirror, false));
        
        root.addItem(VersionedItemUtils.diff(pristine.getContainedItens(), working.getContainedItens()));
        
        return root;
        
    }

    public void update(VersionedItem files) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public VersionedItem commit() 
        throws ApplicationException {
        File local = new File(workspaceDir);
        File mirror = new File(local, WS_FOLDER + File.separator + ESPELHO);
        
        VersionedDir root = new VersionedDir(); 
        VersionedDir working = new VersionedDir();
        VersionedDir pristine = new VersionedDir();
                
        String exclusions[] = {WS_FOLDER};
        working.addItem(VersionedItemUtils.read(local, exclusions));//false nao le o conteudo
        pristine.addItem(VersionedItemUtils.read(mirror));
        
        root.addItem(VersionedItemUtils.diff(pristine.getContainedItens(), working.getContainedItens()));
        root.setLastChangedRevision(this.getRevision());
        root.setName(WS_FOLDER);
        root.setStatus(EVCSConstants.MODIFIED);
        
        return root;
        
    }
    
    //implementar para o cliente

    public VersionedItem diff(String file, String version) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
     // </editor-fold>
    
 
    // primitivas locais de manipulação de diretório e arquivos
    private List<File> listingDir(File startDir)
             {
        List<File> result = new ArrayList<File>(); //cria coleção
        File[] strDir = startDir.listFiles();
        List<File> str = Arrays.asList(strDir); //transforma em coleção
        for (File file : str) {
            if (!file.isFile()) {
                List<File> deeperList = listingDir(file);
                result.addAll(deeperList);
            } else {
                result.add(file);
            }
        }
        return result;
    }

    private List<File> listingDirNotEspelho(File startDir)
            {
        List<File> result = new ArrayList<File>(); //cria coleção
        // filtro para não entrar no diretorio de controle
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (!name.endsWith(".labgc")) {
                    return true;
                }
                return false;
            }
        }; // fim filtro

        File[] strDir = startDir.listFiles(filter);
        List<File> str = Arrays.asList(strDir); //transforma em coleção
        for (File file : str) {
            if (!file.isFile()) {
                List<File> deeperList = listingDir(file);
                result.addAll(deeperList);
            } else {
                result.add(file);
            }
        }
        return result;
    }

    // Primitiva de comparação de diretórios espelho x projeto

    private VersionedItem compareDir(String espelhoDir, String workspaceDir) {
        List<File> listEspelhoFiles = new ArrayList<File>();
        List<File> listWorkspaceFiles = new ArrayList<File>();

        List<VersionedItem> listVersionedItems = new ArrayList<VersionedItem>();
        Path baseDir = Paths.get(espelhoDir);
        int countdir = baseDir.getNameCount();
        int max;
        File espelhoFile = new File(espelhoDir);
        listEspelhoFiles = listingDir(espelhoFile);

        // compara se teve modificação e "deleção"
        for (File f : listEspelhoFiles) {
            VersionedItem item;
            baseDir = Paths.get(f.getPath());
            max = baseDir.getNameCount();
            Path relativeDir = baseDir.subpath(countdir, max);
            File d = relativeDir.toFile();
            File file2 = new File(workspaceDir + File.separator, d.toString());
            if (f.lastModified() < file2.lastModified()) { //se foi modificado

                if (file2.isDirectory()) {
                    item = new VersionedDir();
                } else {
                    item = new VersionedFile();
                }

                item.setName(file2.getName());
                item.setStatus(EVCSConstants.MODIFIED);
                listVersionedItems.add(item);
            }
            if (!file2.exists()) { //se foi deletado

                if (file2.isDirectory()) {
                    item = new VersionedDir();
                } else {
                    item = new VersionedFile();
                }

                item.setName(file2.getName());
                item.setStatus(EVCSConstants.DELETED);
                listVersionedItems.add(item);
            }
        }
        baseDir = Paths.get(workspaceDir);
        countdir = baseDir.getNameCount();
        File dir02 = new File(workspaceDir);
        listWorkspaceFiles = listingDirNotEspelho(dir02);
        for (File f : listWorkspaceFiles) {
            VersionedItem item;
            baseDir = Paths.get(f.getPath());
            max = baseDir.getNameCount();
            Path relativeDir = baseDir.subpath(countdir, max);
            File d = relativeDir.toFile();
            File file2 = new File(workspaceDir + File.separator, d.toString());
            if (!file2.exists()) {
                if (f.isDirectory()) {
                    item = new VersionedDir();
                } else {
                    item = new VersionedFile();
                }
                //se foi adicionado
                item.setName(f.getName());
                listVersionedItems.add(item);
            }
        }
        VersionedDir result=new VersionedDir();
        result.addItem(listVersionedItems);
        return result;
    }

    // Primitiva de cópia de diretórios
    private void copyDir(File src, File dest)
            throws IOException {
        if (src.isDirectory()) {
            //cria se diretório não existe
            if (!dest.exists()) {
                dest.mkdir();
            }
            /* Filtro de diretório
             * 
             * FilenameFilter filter = new FilenameFilter() {
             public boolean accept(File dir, String name) {
             if (!name.endsWith("CVS")) {
             // if (name.endsWith(".txt")) {
             return true;
             }       
             return false;
             }
             };
             * 
             */

            //pega conteúdo do diretório
            String files[] = src.list();
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //cópia recursiva
//    		copydir(srcFile,destFile);
            }

        } else {
            //copia arquivos
            FileInputStream fisOrigem = new FileInputStream(src);
            FileOutputStream fisDestino = new FileOutputStream(dest);
            FileChannel fcOrigem = fisOrigem.getChannel();
            FileChannel fcDestino = fisDestino.getChannel();
            // Transfere todo o volume para o arquivo de cópia com o número de bytes do arquivo original
            fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
            dest.setLastModified(src.lastModified());
            // Fecha
            fisOrigem.close();
            fisDestino.close();

        }
    }

    private boolean copy(File origem, File destino, boolean overwrite)
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
        destino.setLastModified(origem.lastModified());
        // Fecha
        fisOrigem.close();
        fisDestino.close();
        return true;
    }

    private static boolean deleteDir(File dir) {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (!name.endsWith(".labgc")) {
                    return true;
                }
                return false;
            }
        }; 
        if (dir.isDirectory()) {
            String[] children = dir.list(filter);
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    private boolean isSubDir(File baseDir, File child)
            throws IOException {
        baseDir = baseDir.getCanonicalFile();
        child = child.getCanonicalFile();

        File parentFile = child;
        while (parentFile != null) {
            if (baseDir.equals(parentFile)) {
                return true;
            }
            parentFile = parentFile.getParentFile();
        }
        return false;
    }

    private void checkLabgcFolder(String path) throws WorkspaceException {

        final File diretorio1 = new File(path, ".labgc");
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

    private VersionedItem fileToVersionedItem(File file) {
        VersionedItem result = null;

        if (file.isDirectory()) {
            File[] stDir = file.listFiles();
            result = new VersionedDir();

            for (File files : stDir) { //for (int i = 0; i < stdir.length; i++){File files = stdir[i];

                ((VersionedDir) result).addItem(fileToVersionedItem(files));
            }
        } else {
            result = new VersionedFile();
            result.setLastChangedTime(new Date(file.lastModified()));
            result.setName(file.getName());
            try {
                ((VersionedFile) result).setContent(Files.readAllBytes(file.toPath()));
            } catch (IOException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }


} //End
