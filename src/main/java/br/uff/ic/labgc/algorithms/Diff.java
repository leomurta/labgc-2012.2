package br.uff.ic.labgc.algorithms;

import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.IncompatibleItensException;
import java.io.File;

/**
 *
 * @author Evangelista
 */
public class Diff {
    
    public static byte[] run( VersionedItem file1, VersionedItem file2 ){
        
        if( isFile(file1) && isFile(file2) ){
            return diff_archives( (VersionedFile)file1, (VersionedFile)file2 );
        } else {
            if( !isFile(file1) && !isFile(file2) ){
                return diff_directories( (VersionedDir)file1, (VersionedDir)file2 );
            } else {
                throw new IncompatibleItensException();
            }
        }
    }
    
    private static byte[] diff_archives   ( VersionedFile file1, VersionedFile file2 ) throws ApplicationException{
        // Verifica se os Hashes de arquivo são diferentes
        // Chama LCS
        // 
        
        byte[] final_file;
        
        // Registra informações dos arquivos que estão sendo diferenciados
        
        if( file1.getHash() != file2.getHash() ){
            byte[] lcs_file = Diff.lcs( file1.getContent(), file2.getContent() );
        }
        return null;
    }

    private static byte[] diff_directories( VersionedDir file1, VersionedDir file2 ) {
        return null;
    }
    
    private static boolean isFile( VersionedItem file1 ){
        try{
            VersionedFile aux = (VersionedFile) file1;
        } catch( Exception e ){
            return false;
        }
        return true;
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
