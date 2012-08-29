/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.client;

import java.io.File;
import java.util.List;

/**
 *
 * @author proto
 */
public interface IClient {
    public abstract boolean commit(File file);
    public abstract List<File> update();
    public abstract List<File> checkout();
    public abstract boolean release();
    public abstract String diff(File file, String version);
    
}
