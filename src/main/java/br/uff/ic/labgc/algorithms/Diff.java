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
    
    // OUTPUT DELTA
    // <- Não definido ainda ! ->
    //
    
    public static byte[] run( VersionedItem file1, VersionedItem file2 ) throws ApplicationException, IncompatibleItensException{
        
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
        
        byte[] final_file = null;
        
        // Registra METADADOS dos arquivos que estão sendo diferenciados
        
        if( file1.getHash() != file2.getHash() ){
            
            byte[] lcs_file = Diff.lcs( file1.getContent(), file2.getContent() );
            int num_of_blocks = countLines(lcs_file);
            
            addBlock( lcs_file, final_file );
            
            for( int i = 0; i < num_of_blocks + 1; i++ ){
                byte[] block = identifyBlock( file1, lcs_file, i );
                addBlock( block, final_file );
            }
            
        }
        return null;
    }

    private static byte[] diff_directories( VersionedDir file1, VersionedDir file2 ) {
        return null;
    }
    
    // Finalizado
    private static boolean isFile( VersionedItem file1 ){
        try{
            VersionedFile aux = (VersionedFile) file1;
        } catch( Exception e ){
            return false;
        }
        return true;
    }
    
    // Finalizado
    private static byte[] lcs( byte[] seq1, byte[] seq2 ){
        
        byte[] fline_seq1 = getFline(seq1); // Identifica primeira linha seq1
        byte[] fline_seq2 = getFline(seq2); // Identifica segunda  linha seq2
        
        byte[] new_seq1 = delFline( fline_seq1, seq1 ); // Remove primeiras linhas das sequências
        byte[] new_seq2 = delFline( fline_seq2, seq2 );
        
        if( hasDiff( fline_seq1, fline_seq2 ) ){            
            // Chamar duas frentes de LCS
            byte[] lcs1 = lcs( new_seq1, seq2 );
            byte[] lcs2 = lcs( seq1, new_seq2 );
            
            // Verifica qual das duas frentes possui maior sequência comum
            // Retorna
            if( countLines(lcs1) >= countLines(lcs2) ){
                return lcs1;
            } else {
                return lcs2;
            }
        } else {
            return addFline( fline_seq1, lcs( new_seq1, new_seq2 ) );
        }

    }

    // Finalizado
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

    private static int countLines(byte[] lcs_file) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static void addBlock(byte[] lcs_file, byte[] final_file) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static byte[] identifyBlock(VersionedFile file1, byte[] lcs_file, int i) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
