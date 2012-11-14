/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.util;

import br.uff.ic.labgc.algorithms.Diff;
import br.uff.ic.labgc.core.EVCSConstants;
import br.uff.ic.labgc.core.IObserver;
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.IncompatibleItensException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Felipe
 */
public class VersionedItemUtils {

    IObserver[] observers;

    private VersionedItemUtils() {
    }

    private VersionedItemUtils(IObserver[] observers) {
        this.observers = observers;
    }

    /**
     * Método para a leitura de um arquivo, junto com seu conteudo, do disco
     * transformando para VersionedFile
     *
     * @param file arquivo para a leitura e conversao
     * @return Retorna uma VersionedFile
     * @throws ApplicationException
     */
    public static VersionedFile reaFile(final File file) throws ApplicationException {
        return readFile(file, true);
    }

    /**
     * Método para a leitura de um arquivo do disco transformando para
     * VersionedFile
     *
     * @param file arquivo para a leitura e conversao
     * @param content valor booleano que define se o conteúdo será coletado ou
     * não - false para coletar apenas metadados
     * @return Retorna uma VersionedFile
     * @throws ApplicationException
     */
    public static VersionedFile readFile(final File file, boolean content) throws ApplicationException {
        try {
            VersionedItemUtils vi = new VersionedItemUtils();
            vi.checkFile(file);
            vi.canRead(file);
            return vi.readFileToVersionedFile(file, content);
        } catch (IOException ex) {
            Logger.getLogger(VersionedItemUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new ApplicationException("Ocorreu um erro na leitura do arquivo");
        }
    }
    /**
     * Método para a leitura de arquivos do disco transformando para
     * VersionedItem
     *
     * @param dir diretório root onde os dados iniciarão a leitura
     * @return Retorna uma lista de VersionedItem
     * @throws ApplicationException
     */
    public static List<VersionedItem> read(final File dir) throws ApplicationException {
        return VersionedItemUtils.read(dir, null, true);
    }
    /**
     * Método para a leitura de arquivos do disco transformando para
     * VersionedItem
     *
     * @param dir diretório root onde os dados iniciarão a leitura
     * @param content valor booleano que define se o conteúdo será coletado ou
     * não - false para coletar apenas metadados
     * @return Retorna uma lista de VersionedItem
     * @throws ApplicationException
     */
    public static List<VersionedItem> read(final File dir, boolean content) throws ApplicationException {
        return VersionedItemUtils.read(dir, null, content);
    }
    /**
     * Método para a leitura de arquivos do disco transformando para
     * VersionedItem
     *
     * @param dir diretório root onde os dados iniciarão a leitura
     * @param exclusions lista pastas ou arquivos a serem ignorados
     * @return Retorna uma lista de VersionedItem
     * @throws ApplicationException
     */
    public static List<VersionedItem> read(final File dir, String[] exclusions) throws ApplicationException {
        return VersionedItemUtils.read(dir, exclusions, true);
    }

    /**
     * Método para a leitura de arquivos do disco transformando para
     * VersionedItem
     *
     * @param dir diretório root onde os dados iniciarão a leitura
     * @param exclusions lista pastas ou arquivos a serem ignorados
     * @param content valor booleano que define se o conteúdo será coletado ou
     * não - false para coletar apenas metadados
     * @return Retorna uma lista de VersionedItem
     * @throws ApplicationException
     */
    public static List<VersionedItem> read(final File dir, String[] exclusions, boolean content) throws ApplicationException {
        try {
            VersionedItemUtils vi = new VersionedItemUtils();
            vi.checkDir(dir);
            vi.canRead(dir);
            return vi.readFiles(dir, exclusions, content);
        } catch (IOException ex) {
            Logger.getLogger(VersionedItemUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new ApplicationException("Ocorreu um erro na leitura dos arquivos");
        }
    }

    /**
     * Método para a gravação um VersionedFile para arquivo do disco
     *
     * @param dir diretório root onde os dados iniciarão a escrita
     * @param item VersionedFile a ser gravado no disco
     * @throws ApplicationException
     */
    public static void writeFile(final File dir, VersionedFile item) throws ApplicationException {
        VersionedItemUtils vi = new VersionedItemUtils();
        try {
            vi.writeVersionedFile(item, dir);
        } catch (IOException ex) {
            Logger.getLogger(VersionedItemUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new ApplicationException("Nao foi possivel gravar o arquivo");
        }
    }

    /**
     * Método para a gravação VersionedItem para arquivos do disco
     *
     * @param dir diretório root onde os dados iniciarão a escrita
     * @param items lista de VersionedItem a serem gravados
     * @throws ApplicationException
     */
    public static void write(final File dir, List<VersionedItem> items) throws ApplicationException {
        VersionedItemUtils.write(dir, items, null);
    }

    /**
     * Método para a gravação VersionedItem para arquivos do disco
     *
     * @param dir diretório root onde os dados iniciarão a escrita
     * @param items lista de VersionedItem a serem gravados
     * @param observers array dos observadores do processo de gravação
     * @throws ApplicationException
     */
    public static void write(final File dir, List<VersionedItem> items, IObserver[] observers) throws ApplicationException {
        VersionedItemUtils vi = new VersionedItemUtils(observers);
        vi.checkDir(dir);
        vi.canWrite(dir);
        vi.writeFiles(dir, items);
    }
    
    /**
     * Metodo para diff de arquivo
     * @param pristine copia base
     * @param working copia a ser checada
     * @return VersionedFile
     */
    public static VersionedFile diff(VersionedFile pristine, VersionedFile working) throws ApplicationException {
        VersionedItemUtils vi = new VersionedItemUtils();
        return vi.compareVersionedFile(pristine, working);
    }
    
    /**
     * Metodo para diff de VersionedItem
     * @param pristine copia base
     * @param working copia a ser checada
     * @return lista de VersionedItem
     */
    public static List<VersionedItem> diff(List<VersionedItem> pristine, List<VersionedItem> working) throws ApplicationException {
        VersionedItemUtils vi = new VersionedItemUtils();
        return vi.compareVersionedItems(pristine, working);
    }

    // <editor-fold defaultstate="collapsed" desc="Ferramentas de verificacao"> 
    /**
     * Método para verificar se o diretório passado é realmente diretório
     *
     * @param dir diretório
     * @throws ApplicationException
     */
    private void checkDir(File dir) throws ApplicationException {
        if (!dir.isDirectory()) {
            throw new ApplicationException("A referencia não é um diretório válido");
        }
    }

    private void checkFile(File file) throws ApplicationException {
        try {
            this.checkDir(file);
        } catch (ApplicationException ex) {
            return;
        }
        throw new ApplicationException("A referencia não é um arquivo válido");
    }

    /**
     * Método para verificar se o diretório tem permissão de leitura
     *
     * @param dir diretório
     * @throws ApplicationException
     */
    private void canRead(File dir) throws ApplicationException {
        if (!dir.canRead()) {
            throw new ApplicationException("O diretório não pode ser lido");
        }
    }

    /**
     * Método para verificar se o diretório tem permissão de escrita
     *
     * @param dir diretório
     * @throws ApplicationException
     */
    private void canWrite(File dir) throws ApplicationException {
        if (!dir.canWrite()) {
            throw new ApplicationException("O diretório não pode ser escrito");
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Ferramentas de leitura">    
    /**
     * Método para a leitura de arquivos do disco
     *
     * @param dir diretório root onde a leitura começa
     * @param exclusions arquivos ou diretórios a serem ignorados pela leitura
     * @param content booleano, false para coletar apenas metadados
     * @return Retorna uma lista de VersionedItem
     * @throws IOException
     */
    private List<VersionedItem> readFiles(File dir, String[] exclusions, boolean content) throws IOException {
        VersionedDir root = new VersionedDir();

        File[] files;

        if (exclusions != null && exclusions.length > 0) {
            files = dir.listFiles(makeFilter(exclusions));
        } else {
            files = dir.listFiles();
        }

        for (File file : files) {
            if (file.isDirectory()) {
                VersionedDir newdir = new VersionedDir();
                newdir.setName(file.getName());
                newdir.addItem(this.readFiles(file, exclusions, content));
                root.addItem(newdir);
            } else {
                root.addItem(this.readFileToVersionedFile(file, content));
            }

        }
        return root.getContainedItens();
    }

    /**
     * Método que gera filtros de itens ignorados na leitura
     *
     * @param exclusions array com o nome dos arquivos ou diretórios a ignorar
     * @return retorna um FileFilter
     */
    private FileFilter makeFilter(final String[] exclusions) {
        FileFilter filter = new FileFilter() {
            private String[] denied = exclusions;

            @Override
            public boolean accept(File file) {
                for (String name : denied) {
                    if (file.getName().equals(name)) {
                        return false;
                    }
                }
                return true;
            }
        };
        return filter;
    }

    /**
     * Método que gera um VersionedFile de um arquivo no disco
     *
     * @param file arquivo no disco
     * @param content booleano, false para coletar apenas metadados
     * @return retorna um VersionedFile
     * @throws IOException
     */
    private VersionedFile readFileToVersionedFile(File file, boolean content) throws IOException {
        VersionedFile newfile = new VersionedFile();
        newfile.setName(file.getName());
        newfile.setLastChangedTime(new Date(file.lastModified()));
        newfile.setContent(FileUtils.readFileToByteArray(file));
        
        if (!content) {
            newfile.releaseContent();
        }

        return newfile;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Ferramentas de escrita"> 
    /**
     * Método para a gravação de arquivos do disco
     *
     * @param dir diretório root onde a gravação inicia
     * @param items lista de VersionedItem para a gravação em disco
     * @throws ApplicationException
     */
    private void writeFiles(File dir, List<VersionedItem> items) throws ApplicationException {
        for (VersionedItem item : items) {
            if (item.isDir()) {
                try {
                    this.writeVersionedDir((VersionedDir) item, dir);
                } catch (IOException ex) {
                    Logger.getLogger(VersionedItemUtils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ApplicationException("Não foi possivel criar o diretório " + item.getName());
                }
            } else {
                try {
                    this.writeVersionedFile((VersionedFile) item, dir);
                } catch (IOException ex) {
                    Logger.getLogger(VersionedItemUtils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ApplicationException("Não foi possivel gravar o arquivo " + item.getName());
                }
            }
        }
    }

    /**
     * Método para a criação de diretórios
     *
     * @param vdir objeto de diretório VersionedDir
     * @param root diretório root onde o novo diretório será criado
     * @throws IOException
     * @throws ApplicationException
     */
    private void writeVersionedDir(VersionedDir vdir, File root) throws IOException, ApplicationException {
        File newdir = new File(root, vdir.getName());
        newdir.mkdir();
        this.writeFiles(newdir, vdir.getContainedItens());
        newdir.setLastModified(vdir.getLastChangedTime().getTime());
    }

    /**
     * Método para a gravação de arquivos
     *
     * @param vfile objeto de arquivo VersionedFile
     * @param root diretório root onde o arquivos será criado
     * @throws IOException
     * @throws ApplicationException
     */
    private void writeVersionedFile(VersionedFile vfile, File root) throws IOException, ApplicationException {
        File newfile = new File(root, vfile.getName());
        newfile.createNewFile();

        byte[] content = vfile.getContent();

        FileOutputStream fileWriter = new FileOutputStream(newfile);
        fileWriter.write(content);
        fileWriter.close();
        newfile.setLastModified(vfile.getLastChangedTime().getTime());
        this.notifyObservers(newfile.getAbsolutePath());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Ferramentas de diff">
    /**
     * Metodo para a comparacao de VersionedItem
     *
     * @param pristine copia do espelho
     * @param working copia do usuario
     * @return uma lista de VersionedItem
     */
    private List<VersionedItem> compareVersionedItems(List<VersionedItem> pristine, List<VersionedItem> working) throws ApplicationException {
        VersionedDir root = new VersionedDir();

        List<VersionedItem> modified = this.checkModified(pristine, working);
        List<VersionedItem> deleted = this.<VersionedItem>difference(pristine, working);
        List<VersionedItem> added = this.<VersionedItem>difference(working, pristine);

        root.addItem(this.releaseContent(deleted, EVCSConstants.DELETED));
        root.addItem(this.setAdded(added));
        root.addItem(modified);

        return root.getContainedItens();
    }

    /**
     * Metedo para remover o conteudo de um Versionded
     *
     * @param items
     * @param status
     * @return
     */
    private List<VersionedItem> releaseContent(List<VersionedItem> items, int status) {
        VersionedDir root = new VersionedDir();
        for (VersionedItem item : items) {
            item.setStatus(status);
            if (item.isDir()) {
                VersionedDir dir = (VersionedDir) item;
                root.addItem(this.releaseContent(dir.getContainedItens(), status));
            } else {
                VersionedFile file = (VersionedFile) item;
                if (file.hasContent()) {
                    file.releaseContent();
                }
                root.addItem(file);
            }
        }
        return root.getContainedItens();
    }

    /**
     * Metodo para marcar o status de todos os arquivos adicionados
     *
     * @param items Lista de VersionedItem novos
     * @return A mesma lista marcada;
     */
    private List<VersionedItem> setAdded(List<VersionedItem> items) {
        VersionedDir root = new VersionedDir();
        for (VersionedItem item : items) {
            item.setStatus(EVCSConstants.ADDED);
            if (item.isDir()) {
                VersionedDir dir = (VersionedDir) item;
                root.addItem(this.setAdded(dir.getContainedItens()));
            } else {
                root.addItem(item);
            }

        }
        return root.getContainedItens();
    }

    /**
     * Metodo verifica a existencia de VersionedItem modificados em um conjunto
     * de VersionedItem comuns a copia de trabalho e ao espelho
     *
     * @param pristine Lista de VersionedItem do espelho
     * @param working Lista de VersionedItem da copia de trabalho do usuario
     * @return Lista de VersionedItem
     */
    private List<VersionedItem> checkModified(List<VersionedItem> pristine, List<VersionedItem> working) throws ApplicationException {
        List<VersionedItem> pris = this.<VersionedItem>intersection(pristine, working);
        List<VersionedItem> work = this.<VersionedItem>intersection(working, pristine);

        Collections.sort(pris);
        Collections.sort(work);

        VersionedDir root = new VersionedDir();

        for (VersionedItem itemp : pris) {
            for (VersionedItem itemw : work) {
                if (itemp.equals(itemw)) {

                    if (itemp.isDir()) {
                        root.addItem(this.compareVersionedDir((VersionedDir) itemp, (VersionedDir) itemw));
                    } else {
                        root.addItem(this.compareVersionedFile((VersionedFile) itemp, (VersionedFile) itemw));
                    }

                    break;
                }
            }
        }

        return root.getContainedItens();
    }

    /**
     * Metodo para comparar VersionedDir
     *
     * @param pristine VersionedDir do espelho
     * @param working VersionedDir modificado pelo usuario
     * @return Retorna um VersionedDir
     */
    private VersionedDir compareVersionedDir(VersionedDir pristine, VersionedDir working) throws ApplicationException {
        VersionedDir root = new VersionedDir();

        if (!pristine.getLastChangedTime().equals(working.getLastChangedTime())
                || pristine.getSize() != working.getSize()) {
            root.addItem(this.compareVersionedItems(pristine.getContainedItens(), working.getContainedItens()));
            root.setStatus(EVCSConstants.MODIFIED);
        } else {
            root.addItem(this.releaseContent(working.getContainedItens(), EVCSConstants.UNMODIFIED));
            root.setStatus(EVCSConstants.UNMODIFIED);
        }
        return root;
    }

    /**
     * Metodo para comparar VersionedFile se e diferente ou nao
     *
     * @param pristine VersionedFile do espelho
     * @param working VerisonedFile modificado pelo usuario
     * @return retorna o VersionedFile com o conteudo definido pelo usuario ou
     * diff dele com a versao do espelho
     */
    private VersionedFile compareVersionedFile(VersionedFile pristine, VersionedFile working) throws ApplicationException {

        VersionedFile file = working;
          
        if (pristine.getHash().equals(working.getHash())){
            VersionedDir root = new VersionedDir();
            root.addItem(file);
            releaseContent(root.getContainedItens(), EVCSConstants.UNMODIFIED);
        } else {
            if(pristine.hasContent() && working.hasContent()) {
                file = this.applyDiff(pristine, working);
            }
            file.setStatus(EVCSConstants.MODIFIED);
        }

        return file;

    }

    /**
     * Metodo para aplicar diff de arquivos
     *
     * @param pristine VersionedFile do espelho
     * @param working VerisonedFile modificado pelo usuario
     * @return retorna o VersionedFile com o diff no conteudo
     */
    private VersionedFile applyDiff(VersionedFile pristine, VersionedFile working) throws ApplicationException {//chamar algoritmo de diff aqui
        if (pristine.hasContent() && working.hasContent()) {
            working.setDiffContent(Diff.run(pristine, working));
            working.setDiff(true);
            return working;
        } else {
            return working;
        }
    }

    /**
     * Metodo para verificar arquivos com iguais em duas listas. Funciona com o
     * equals
     *
     * @param <T>
     * @param base referencia base para a comparacao e retorno
     * @param other item para comparar
     * @return retorna uma lista de itens <T>
     */
    private <T> List<T> intersection(List<T> base, List<T> other) {
        Set<T> others = new HashSet<T>(other);
        List<T> temp = new ArrayList<T>();
        for (T item : base) {
            if (others.contains(item));
            temp.add(item);
        }
        return temp;
    }

    /**
     * Metodo para verificar arquivos com diferentes em duas listas. Funciona
     * com o equals
     *
     * @param <T>
     * @param base referencia base para a comparacao e retorno
     * @param other item para comparar
     * @return retorna uma lista de itens <T>
     */
    private <T> List<T> difference(List<T> base, List<T> other) {
        Set<T> basis = new HashSet<T>(base);
        Set<T> others = new HashSet<T>(other);

        basis.removeAll(others);

        return new ArrayList<T>(basis);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Observer"> 
    /**
     * Método para avisar os interessados em monitorar o processo dentro do
     * VersionedItems
     *
     * @param msg mensagem a ser enviada
     */
    private void notifyObservers(String msg) {
        if (observers != null) {
            for (IObserver observer : observers) {
                observer.sendNotify(msg);
            }
        }
    }
    // </editor-fold>
}
