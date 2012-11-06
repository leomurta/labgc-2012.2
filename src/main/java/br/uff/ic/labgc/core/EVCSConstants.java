/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.core;

/**
 *
 * @author Cristiano
 */
public interface EVCSConstants {

    /**
     * Identifica a revisão HEAD
     */
    public static final String REVISION_HEAD = "-1";
    
    /*
     * Identifica a quantidade de msgs default que o log retornará
     */
    public static final int DEFAULT_LOG_MSG = 10;
    
    /**
     * Identifica que o item nao foi modificado
     */
    public static final int UNMODIFIED = 0;
    
    /**
     * Identifica que o item foi modificado
     */
    public static final int MODIFIED = 1;
    
    /**
     * Identifica que o item nao foi adicionado
     */
    public static final int ADDED = 2;
    
    /**
     * Identifica que o item nao foi deletado
     */
    public static final int DELETED = 3;
    
}
