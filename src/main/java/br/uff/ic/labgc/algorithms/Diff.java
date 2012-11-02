package br.uff.ic.labgc.algorithms;

// Teste !

import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.IncompatibleItensException;

/**
 *
 * @author Evangelista
 */
public class Diff {
    
    // OUTPUT DELTA
    // <- Não definido ainda ! ->
    //
    
    private final static byte EOL = (new String("\n")).getBytes()[0];
    private final static byte EOF = (new String("\n")).getBytes()[0];
    
    public static void main( String args[] ){
        byte[] testeA;
        byte[] testeB;
        String teste2 = "A" + '\n' + "B" + '\n' + "C" + '\n' + "D" + '\n';
        String teste3 = "A" + '\n' + "F" + '\n' + "C" + '\n';
        
        testeA = teste2.getBytes();
        testeB = teste3.getBytes();
        
        //teste = getFline(teste);
        
        System.out.println("-->\n" + new String(lcs(testeA, testeB)));
    }
    
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
    
    public static byte[] patch( VersionedItem file1, byte[] delta ){
        
        return null;
        
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
            
            for( int i = 0; i < num_of_blocks + 1; i++ ){
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
        
        if( !isNull( seq1 ) && !isNull( seq2 ) ){
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

        }else{
            return new byte[0];
        }

    }

    // Finalizado
    private static boolean hasDiff(byte[] fline_seq1, byte[] fline_seq2) {
        if( !(isNull(fline_seq1) && isNull(fline_seq2)) && fline_seq1.length == fline_seq2.length ){
        for( int i = 0; i < fline_seq1.length; i++ ){
            if( Byte.compare(fline_seq1[i], fline_seq2[i]) != 0 ){
                return true;
            }
        }
        }
        return false;
    }

    private static byte[] delFline( byte[] fline_seq1, byte[] seq ) {
       
        byte[] retorno = new byte[seq.length - fline_seq1.length];
        
        for( int j = 0; j < retorno.length; j++ ){
            retorno[j] = seq[j + fline_seq1.length];
        }
        
        return retorno;
        
    }

    private static byte[] getFline( byte[] seq ) {
        
        if( seq.length != 0){
        int i = 0;
        while( Byte.compare(seq[i++], EOL) != 0 );
        byte[] retorno = new byte[i];
        
        for( int j = 0; j < i; j++ ){
            retorno[j] = seq[j];
        }
        
        return retorno;
        } else {
            return new byte[0];
        }
        
    }

    private static byte[] addFline( byte[] fline_seq1, byte[] seq ) {
        byte[] retorno = new byte[fline_seq1.length + seq.length];
        
        for(int i = 0; i < fline_seq1.length; i++ ){
            retorno[i] = fline_seq1[i];
        }
        for(int i = 0; i < seq.length; i++){
            retorno[i + fline_seq1.length] = seq[i];
        }
        
        return retorno;
    }

    private static int countLines(byte[] lcs_file) {
        int tam = 0;
        for( int i = 0; i < lcs_file.length; i++ ){
            if( Byte.compare(lcs_file[i], EOL) == 0 ){ tam++; }
        }
        return tam;
    }
    
    private static boolean isNull( byte[] seq ){
        return seq.length == 0;
    }
    
}
