/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.util;

import br.uff.ic.labgc.core.IObserver;
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Felipe
 */
public class VersionedItems {

    IObserver[] observers;

    private VersionedItems() {
    }

    private VersionedItems(IObserver[] observers) {
        this.observers = observers;
    }
    
    /**
     * Método para a leitura de arquivos do disco transformando para VersionedItem
     * @param dir diretório root onde os dados iniciarão a leitura
     * @param exclusions lista pastas ou arquivos a serem ignorados
     * @return Retorna uma lista de VersionedItem
     * @throws ApplicationException 
     */
    public static List<VersionedItem> read(final File dir, String[] exclusions) throws ApplicationException {
        return VersionedItems.read(dir, exclusions, true);
    }
    
    /**
     * Método para a leitura de arquivos do disco transformando para VersionedItem
     * @param dir diretório root onde os dados iniciarão a leitura
     * @param exclusions lista pastas ou arquivos a serem ignorados
     * @param content valor booleano que define se o conteúdo será coletado ou não - false para coletar apenas metadados
     * @return Retorna uma lista de VersionedItem
     * @throws ApplicationException 
     */
    public static List<VersionedItem> read(final File dir, String[] exclusions, boolean content) throws ApplicationException {
        try {
            VersionedItems vi = new VersionedItems();
            vi.checkDir(dir);
            vi.canRead(dir);
            return vi.readFiles(dir, exclusions, content);
        } catch (IOException e) {
            throw new ApplicationException("Ocorreu um erro na leitura dos arquivos");
        }
    }
    
    /**
     * Método para a gravação VersionedItem para arquivos do disco
     * @param dir diretório root onde os dados iniciarão a leitura
     * @param items lista de VersionedItem a serem gravados
     * @throws ApplicationException 
     */
    public static void write(final File dir, List<VersionedItem> items) throws ApplicationException {
        VersionedItems.write(dir, items, null);
    }

    /**
     * Método para a gravação VersionedItem para arquivos do disco
     * @param dir diretório root onde os dados iniciarão a leitura
     * @param items lista de VersionedItem a serem gravados
     * @param observers array dos observadores do processo de gravação
     * @throws ApplicationException 
     */
    public static void write(final File dir, List<VersionedItem> items, IObserver[] observers) throws ApplicationException {
        VersionedItems vi = new VersionedItems(observers);
        vi.checkDir(dir);
        vi.canWrite(dir);
        vi.writeFiles(dir, items);
    }
    
    /**
     * Método para verificar se o diretório passado é realmente diretório
     * @param dir diretório
     * @throws ApplicationException 
     */
    private void checkDir(File dir) throws ApplicationException {
        if (!dir.isDirectory()) {
            throw new ApplicationException("A referencia não é um diretório válido");
        }
    }
    
    /**
     * Método para verificar se o diretório tem permissão de leitura
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
     * @param dir diretório
     * @throws ApplicationException 
     */
    private void canWrite(File dir) throws ApplicationException {
        if (!dir.canWrite()) {
            throw new ApplicationException("O diretório não pode ser escrito");
        }
    }

    //ferramentas de leitura
    /**
     * Método para a leitura de arquivos do disco
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
     * @param file arquivo no disco
     * @param content booleano, false para coletar apenas metadados
     * @return retorna um VersionedFile
     * @throws IOException 
     */
    private VersionedFile readFileToVersionedFile(File file, boolean content) throws IOException {
        VersionedFile newfile = new VersionedFile();
        newfile.setName(file.getName());
        newfile.setLastChangedTime(new Date(file.lastModified()));

        if (content) {
            newfile.setContent(FileUtils.readFileToByteArray(file));
        }

        return newfile;
    }

    //ferramentas de escrita
    /**
     * Método para a gravação de arquivos do disco
     * @param dir diretório root onde a gravação inicia
     * @param items lista de VersionedItem para a gravação em disco
     * @throws ApplicationException 
     */
    private void writeFiles(File dir, List<VersionedItem> items) throws ApplicationException {

        for (VersionedItem item : items) {
            if (item.isDir()) {
                try {
                    this.writeVersionedDir((VersionedDir) item, dir);
                } catch (IOException e) {
                    throw new ApplicationException("Não foi possivel criar o diretório "+item.getName());
                }
            } else {
                try {
                    this.writeVersionedFile((VersionedFile) item, dir);
                } catch (IOException e) {
                    throw new ApplicationException("Não foi possivel gravar o arquivo "+item.getName());
                }
            }
        }
    }
    
    /**
     * Método para a criação de diretórios
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
        this.notifyObservers(newfile.getPath());
    }

    //observers
    /**
     * Método para avisar os interessados em monitorar o processo dentro do VersionedItems
     * @param msg mensagem a ser enviada
     */
    private void notifyObservers(String msg) {
        if (observers != null) {
            for (IObserver observer : observers) {
                observer.sendNotify(msg);
            }
        }
    }
}
