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
    
    /**
     * Identifica a primeira revisão criada de um projeto
     */
    public static final String FIRST_REVISION = "1.0";
    
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
    
    //tipos de delta
    public static final int FORWARD = 0;
    public static final int REVERSE = 1;
    public static final int DELTATYPE = REVERSE;
    
    //diz se irá utilizar deltas ou fazer versionamento completo
    public static final boolean DELTAENABLED = false;
    
    public static final int POSTGRES = 0;
    public static final int DERBY = 1;
    public static final int HSQLDB = 2;
    
    public static final int BDTYPE = POSTGRES;
}
