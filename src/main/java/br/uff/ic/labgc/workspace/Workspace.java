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
import br.uff.ic.labgc.util.VersionedItems;
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
public class Workspace implements IObservable {

    private String LocalRepo;
    private IObserver observer;
    private String PROPERTIES_FILE = "labgc.properties";
    private String PROPERTY_REVISION = "revision";
    private String PROPERTY_HOST = "hostname";
    private String PROPERTY_PROJECT = "repository";
    private String WS_FOLDER = ".labgc";
    private String ESPELHO = "espelho.r";

    public Workspace(String LocalRepo) {
        this.LocalRepo = LocalRepo; //caminho do projeto gravado na WS
    }

    //Métodos de propriedades
    public void setParam(String key, String value)throws WorkspaceException {
        if(key == null)
            throw new WorkspaceException("Valor de chave nula");
        if(value == null)
            throw new WorkspaceException("Valor de valor nulo");
        setProperty(key, value);
    }

    /**
     * metodo para pegar o valor de um parametro salvo
     *
     * @param key, chave do parametro salvo
     * @return
     */
    public String getParam(String key) throws WorkspaceException {
        if(key == null)
            throw new WorkspaceException("Valor de chave nula");
        return getProperty(key);

    }

    /**
     * retorna valor do hostname guardado na criacao do workspace
     *
     * @return
     */
    public String getHost() throws WorkspaceException {
        return getProperty(PROPERTY_HOST);
    }

    /**
     * retorna o valor do relacionado ao repositorio do projeto no servidor.
     * Valor adicionado na criacao do workspace
     *
     * @return
     */
    public String getProject() throws WorkspaceException {
        return getProperty(PROPERTY_PROJECT);
    }

    /**
     * retorna o valor do relacionado ao repositorio do projeto no servidor.
     * Valor adicionado na criacao do workspace
     *
     * @return
     */
    public String getRevision() throws WorkspaceException {
        return getProperty(PROPERTY_REVISION);
    }

    public void setRevision(String revision) throws WorkspaceException {
        if(revision == null) 
            throw new WorkspaceException("Valor de revisao nula");
        setProperty(PROPERTY_REVISION, revision);
    }

    private void setProperty(String chave, String valor) throws WorkspaceException {
        File vcs = new File(this.LocalRepo, WS_FOLDER);
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
        File vcs = new File(this.LocalRepo, WS_FOLDER);
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

    // primitiva de listar diretório e colocar em array de File
    private List<File> listingDir(File startDir)
            throws FileNotFoundException {
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
            throws FileNotFoundException {
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
    public void compareDir(String dir1, String dir2, List<File> lDirM, List<File> lDirD, List<File> lDirA) throws FileNotFoundException {
        List<File> strDir1 = new ArrayList<File>();
        List<File> strDir2 = new ArrayList<File>();

        String repo = LocalRepo;
        Path baseDir = Paths.get(repo + File.separator + ".labgc" + File.separator + "espelho.r");
        int countdir = baseDir.getNameCount();
        int max;
        File dir = new File(dir1);
        strDir1 = listingDir(dir);

        // compara se teve modificação e "deleção"
        for (File f : strDir1) {
            baseDir = Paths.get(f.getPath());
            max = baseDir.getNameCount();
            Path relativeDir = baseDir.subpath(countdir, max);
            File d = relativeDir.toFile();
            File file2 = new File(LocalRepo + File.separator, d.toString());
            if (f.lastModified() < file2.lastModified()) { //se foi modificado
                lDirM.add(file2);
            }
            if (!file2.exists()) { //se foi deletado
                lDirD.add(file2);
            }
        }
        baseDir = Paths.get(dir2);
        countdir = baseDir.getNameCount();
        File dir02 = new File(dir2);
        strDir2 = listingDirNotEspelho(dir02);
        for (File f : strDir2) {
            baseDir = Paths.get(f.getPath());
            max = baseDir.getNameCount();
            Path relativeDir = baseDir.subpath(countdir, max);
            File d = relativeDir.toFile();
            File file2 = new File(LocalRepo + File.separator, d.toString());
            if (!file2.exists()) {  //se foi adicionado
                lDirA.add(f);
            }
        }
    }

    public List<VersionedItem> compareDir(String dir1, String dir2) throws FileNotFoundException {
        List<File> strDir1 = new ArrayList<File>();
        List<File> strDir2 = new ArrayList<File>();

        List<VersionedItem> lItem = new ArrayList<VersionedItem>();
        String repo = LocalRepo;
        Path baseDir = Paths.get(repo + File.separator + ".labgc" + File.separator + "espelho.r");
        int countdir = baseDir.getNameCount();
        int max;
        File dir = new File(dir1);
        strDir1 = listingDir(dir);

        // compara se teve modificação e "deleção"
        for (File f : strDir1) {
            VersionedItem item;
            baseDir = Paths.get(f.getPath());
            max = baseDir.getNameCount();
            Path relativeDir = baseDir.subpath(countdir, max);
            File d = relativeDir.toFile();
            File file2 = new File(LocalRepo + File.separator, d.toString());
            if (f.lastModified() < file2.lastModified()) { //se foi modificado

                if (file2.isDirectory()) {
                    item = new VersionedDir();
                } else {
                    item = new VersionedFile();
                }

                item.setName(file2.getName());
                item.setStatus(EVCSConstants.MODIFIED);
                lItem.add(item);
            }
            if (!file2.exists()) { //se foi deletado

                if (file2.isDirectory()) {
                    item = new VersionedDir();
                } else {
                    item = new VersionedFile();
                }

                item.setName(file2.getName());
                item.setStatus(EVCSConstants.DELETED);
                lItem.add(item);
            }
        }
        baseDir = Paths.get(dir2);
        countdir = baseDir.getNameCount();
        File dir02 = new File(dir2);
        strDir2 = listingDirNotEspelho(dir02);
        for (File f : strDir2) {
            VersionedItem item;
            baseDir = Paths.get(f.getPath());
            max = baseDir.getNameCount();
            Path relativeDir = baseDir.subpath(countdir, max);
            File d = relativeDir.toFile();
            File file2 = new File(LocalRepo + File.separator, d.toString());
            if (!file2.exists()) {
                if (f.isDirectory()) {
                    item = new VersionedDir();
                } else {
                    item = new VersionedFile();
                }
                //se foi adicionado
                item.setName(f.getName());
                lItem.add(item);
            }
        }


        return lItem;
    }

    // Primitiva de cópia de diretórios
    void copyDir(File src, File dest)
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
            // Fecha
            fisOrigem.close();
            fisDestino.close();

        }
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
    // Apagar diretório e subdiretórios
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    boolean add(File file)
            throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    /*
     * Testa se é um subDir de um Dir
     * base tem que ser um diretorio
     * child pode ser um arquivo com caminho completo
     */
    public boolean isSubDir(File baseDir, File child)
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

    public boolean revert(String fileOrDir)
            throws WorkspaceException {

        File local = new File(LocalRepo);
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
        if (!parent.exists()) {
            throw new WorkspaceDirNaoExisteException("ERRO: Diretório inexistente.");

        }
        File diretorio1 = new File(local + File.separator + ".labgc");

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
                diretorio1 = new File(diretorio1 + File.separator + name + extensao);
                achou = true;
            }
        }
        if (!achou) {
            throw new WorkspaceEpelhoNaoExisteException("ERRO: Não existe espelho.");
        }
        // testa se for arquivo copia por cima

        //extrair caminho relativo à baseDir (parent)
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
            if (target.isFile()) {
                try {
                    copy(file2, target, true);
                } catch (FileNotFoundException ex) {
                    throw new WorkspaceException("File not found.");
                } catch (IOException ex) {
                    throw new WorkspaceException("IO Exception.");
                }
            } else {// diretório
                if (!deleteDir(target)) {
                    throw new WorkspaceException("ERRO: Não foi possível limpar WorkSpace.");
                }
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
            throws WorkspaceException {
        File local = new File(LocalRepo);
        File parent = new File(local.getParent());

        if (!parent.exists()) {
            throw new WorkspaceDirNaoExisteException("ERRO: Diretório inexistente.");
        }
        File diretorio1 = new File(local + File.separator + ".labgc");

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
                diretorio1 = new File(diretorio1 + File.separator + name + extensao);
                achou = true;
            }
        }
        if (!achou) {
            throw new WorkspaceEpelhoNaoExisteException("ERRO: Não existe espelho.");
        }
        if (!deleteDir(parent)) {
            throw new WorkspaceException("ERRO: Não foi possível limpar WorkSpace.");
        }
        try {
            copyDir(diretorio1, parent);
            /*        stDir = diretorio1.listFiles();
             // copia os arquivos
             for (File file : stDir) {
             String name = file.getName();
             copy(file, new File(diretorio + "\\" + name), true);
             }*/
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            throw new WorkspaceException("Não foi possivel gravar arquivos no disco");
        }
        // se tudo deu certo   
        return true;
    }
    /**
     *
     * @return
     * @throws IOException
     * @throws WorkspaceException
     */
    public List<VersionedItem> status()
            throws IOException, WorkspaceException {

        File local = new File(LocalRepo);
        File parent = new File(local.getParent());

        if (!parent.exists()) {
            throw new WorkspaceDirNaoExisteException("ERRO: Diretório inexistente.");

        }
        File diretorio1 = new File(local + File.separator + ".labgc");

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
//                diretorio1 = new File(diretorio + File.separator + name + extensao);
                achou = true;
            }
        }
        if (!achou) {
            throw new WorkspaceEpelhoNaoExisteException("ERRO: Não existe espelho.");
        }
        String dir01 = LocalRepo + File.separator + ".labgc" + File.separator + "espelho.r";
        String dir02 = LocalRepo;

        List<VersionedItem> lItem;
        lItem = compareDir(dir01, dir02);

        return lItem;
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


        VersionedItems.write(local, ((VersionedDir) items).getContainedItens(), new IObserver[]{observer});
        VersionedItems.write(espelho, ((VersionedDir) items).getContainedItens());

        
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

    //implementar para o cliente

    public VersionedItem diff(String file, String version) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void update(VersionedItem files) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public VersionedItem commit() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
} //End
