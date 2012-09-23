package br.uff.ic.labgc.algorithms;

import java.io.File;

/**
 *
 * @author Evangelista
 */
public class Diff {
    
    public final static int ARCHIVES    = 0;
    public final static int DIRECTORIES = 1;
    
    public static byte[] run( File file1, File file2, int spec ){
        
        switch( spec ){
            
            case 0:
                // DIFF DE ARQUIVOS
                // converter array de bytes
                return diff_archives   ( file1, file2 );
            case 1:
                // DIFF DE DIRETÓRIOS
                // converter array de bytes
                return diff_directories( file1, file2 );
            
        }
        return null;
    }
    
    private static byte[] diff_archives   ( File file1, File file2 ){
        // Verifica se os Hashes de arquivo são diferentes
        // Chama LCS
        // 
        return null;
    }

    private static byte[] diff_directories( File file1, File file2 ) {
        return null;
    }
    
    private static byte[] lcs( byte[] seq1, byte[] seq2 ){
        // Definir tamanho de "seq1" como sendo o número de linhas (qtd. de \n)
        // Definir tamanho de "seq2" como sendo o número de linhas (qtd. de \n)
        
        byte[] fline_seq1 = getFline(seq1); // Identifica primeira linha seq1
        byte[] fline_seq2 = getFline(seq2); // Identifica segunda  linha seq2
        
        byte[] new_seq1 = delFline( fline_seq1, seq1 ); // Remove primeiras linhas das sequências
        byte[] new_seq2 = delFline( fline_seq2, seq2 );
        
        if( hasDiff( fline_seq1, fline_seq2 ) ){            
            // Chamar duas frentes de LCS
            lcs( new_seq1, seq2 );
            lcs( seq1, new_seq2 );
            
            // Verifica qual das duas frentes possui maior sequência comum
            // Retorna
        } else {
            return addFline( fline_seq1, lcs( new_seq1, new_seq2 ) );
        }
        
        return null;
    }

    private static boolean hasDiff(byte[] fline_seq1, byte[] fline_seq2) {
        for( int i = 0; i < fline_seq1.length; i++ ){
            if( fline_seq1[i] != fline_seq2[i] ){
                return true;
            }
        }
        return false;
    }

    private static byte[] delFline( byte[] fline_seq1, byte[] seq ) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static byte[] getFline( byte[] seq ) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static byte[] addFline( byte[] fline_seq1, byte[] seq ) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
