package br.uff.ic.labgc.algorithms;

import java.io.File;

/**
 *
 * @author Evangelista
 */
public class Diff {
    
    public final static int ARCHIVES    = 0;
    public final static int DIRECTORIES = 1;
    
    public static File run( File file1, File file2, int spec ){
        
        switch( spec ){
            
            case 0:
                // DIFF DE ARQUIVOS
                return diff_archives   ( file1, file2 );
            case 1:
                // DIFF DE DIRETÓRIOS
                return diff_directories( file1, file2 );
            
        }
        return null;
    }
    
    private static File diff_archives   ( File file1, File file2 ){
        return null;
    }

    private static File diff_directories( File file1, File file2 ) {
        return null;
    }
    
}
