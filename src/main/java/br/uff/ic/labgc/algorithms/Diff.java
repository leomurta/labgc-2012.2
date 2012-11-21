package br.uff.ic.labgc.algorithms;

// Teste !
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.IncompatibleItensException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Evangelista
 */
public class Diff {

    // OUTPUT DELTA
    // <- Não definido ainda ! ->
    //
    private final static byte[] EOL = (new String("\n")).getBytes();
    private final static byte[] EOF = (new String("\n")).getBytes();
    
    private final static byte[] ID_DIR  = (new String("/§D")).getBytes();
    private final static byte[] ID_FILE = (new String("/§F")).getBytes();

    public static void main(String args[]) throws ApplicationException {
        byte[] testeA;
        byte[] testeB;
        String teste2 = "D" + '\n' + "Caramba !" + '\n' + "G" + '\n' + "M";
        String teste3 = "Todos os papeis são amarelos" + '\n' + "Caramba !" + '\n' + "HTT" + '\n' + "I" + '\n' + "G" + '\n' + "TGG";

        testeA = teste2.getBytes();
        testeB = teste3.getBytes();
        
        VersionedFile testeC = new VersionedFile();
        VersionedFile testeD = new VersionedFile();
        
        testeC.setContent(testeA);
        testeD.setContent(testeB);

        //teste = getFline(teste);

        //System.out.println("-->\n" + new String(lcs(testeA, testeB)));
        System.out.println(new String(diff(testeC, testeD)));
    }

    public static byte[] run(VersionedItem file1, VersionedItem file2) throws ApplicationException, IncompatibleItensException {

        if ( !file1.isDir() && !file2.isDir() ) {
            return diff( ((VersionedFile)file1), ((VersionedFile)file2) );
        } else {
            if ( file1.isDir() && file2.isDir() ) {
                return null; //diff_directories((VersionedDir) file1, (VersionedDir) file2);
            } else {
                if ( file1.isDir() && !file2.isDir() ){
                    return null;
                }
            }
        }
        return null;
    }
    
    public static byte[] run_and_send_by_email(VersionedItem file1, VersionedItem file2, String target_email){
        return null;
    }

    public static byte[] apply(VersionedFile file1, byte[] delta) {

        byte[] delta_aux = delta;

        //while (getFline(delta_aux)[0] != EOF) {
            // Comparação de Bytes
            // Se R, Remove
            // Se A, Adiciona
        //}

        return null;

    }
    
    public static List<VersionedItem> apply(VersionedDir dir, byte[] delta){
        return null;
    }
    
    public static byte[] apply_email(VersionedFile file1, File email){
        return null;
    }
    
    public static List<VersionedItem> apply_email(VersionedDir dir, File email){
        return null;
    }

    private static byte[] diff(VersionedFile file1, VersionedFile file2) throws ApplicationException {
        // DIFFERENCE BETWEEN FILES
        String ret = "";

        byte[] seq1 = file1.getContent().clone();
        byte[] seq2 = file2.getContent().clone();
        byte[] lcs1 = lcs(seq1, seq2);
        
        int i = 0;
        int j = 0;
        int h = 0;
        int tam_seq1 = countLines(seq1);
        int tam_seq2 = countLines(seq2);
        
        ArrayList<String> lines_added = new ArrayList<>();
        
        // REGISTRA HASH SEGURO
        ret += file1.getHash();
        ret += '\n';
        
        while( !isNull(lcs1) ){
            int i_aux = i;
            if( has_diff( del_line_wraps( get_first_sequence(seq1) ), del_line_wraps( get_first_sequence(lcs1) ) ) ){
                while( has_diff( del_line_wraps( get_first_sequence(seq1) ), del_line_wraps( get_first_sequence(lcs1) ) ) ){
                    seq1 = del_first_sequence( get_first_sequence(seq1).length, seq1 );
                    i++;
                }
                ret += ("R " + h + " " + i_aux + " " + (i-1) ) + '\n';
            }
            if( has_diff( del_line_wraps( get_first_sequence(seq2) ), del_line_wraps( get_first_sequence(lcs1) ) ) ){
                lines_added.removeAll(lines_added);
                while( has_diff( del_line_wraps( get_first_sequence(seq2) ), del_line_wraps( get_first_sequence(lcs1) ) ) ){
                    lines_added.add( new String( del_line_wraps( get_first_sequence(seq2) ) ) );
                    seq2 = del_first_sequence( get_first_sequence(seq2).length, seq2 );
                    j++;
                }
                ret += ("A " + h + " " + lines_added.size() ) + '\n';
                for( int r = 0; r < lines_added.size(); r++ ){
                    ret += ( lines_added.get(r) ) + '\n';
                }
            }

            seq1 = del_first_sequence( get_first_sequence(seq1).length, seq1 );
            seq2 = del_first_sequence( get_first_sequence(seq2).length, seq2 );
            lcs1 = del_first_sequence( get_first_sequence(lcs1).length, lcs1 );
            i++;
            j++;
            h++;
        }
        
        // REMOVE TODAS AS LINHAS APÓS LCS DA SEQ1
        if( i <= tam_seq1 )
            ret += ("R " + h + " " + i + " " + (tam_seq1-1) ) + '\n';
        
        // ADICIONA TODAS AS LINHAS APÓS LCS DA SEQ2
        if( j <= tam_seq2 ){
            lines_added.removeAll(lines_added);
            while( !isNull(seq2) ){
                lines_added.add( new String( del_line_wraps( get_first_sequence(seq2) ) ) );
                seq2 = del_first_sequence( get_first_sequence(seq2).length, seq2 );
            }
            ret += ("A " + h + " " + lines_added.size() ) + '\n';
            for( int r = 0; r < lines_added.size(); r++ ){
                ret += ( lines_added.get(r) ) + '\n';
            }
        }

        return ret.getBytes();
    }

    private static byte[] diff(VersionedDir d1, VersionedDir d2) throws ApplicationException {
        // DIFFERENCE BETWEEN DIRECTORIES
        // TODO: Transformar o contador de Diff em uma variável global.

        byte[] ret = new byte[0];
        
        int count_diff = 0;
        byte[] arq_diff = new byte[0];
        
        List<VersionedItem> dir1 = d1.getContainedItens();
        List<VersionedItem> dir2 = d2.getContainedItens();
        
        int i = 0;
        int j = 0;
        
        List<VersionedItem> lcs = lcs(dir1, dir2);
        
        add_head( ret, d1 );
        
        while( dir1.size() > 0 && dir2.size() > 0 ){
            if( !Set.belongs_to(dir1.get(0), lcs) ){
                if( dir1.get(0).isDir() ){
                    System.out.println("R -D" + dir1.get(0).getName());
                } else {
                    System.out.println("R -F" + dir1.get(0).getName());
                }
            } else {
                if( Set.belongs_to(dir1.get(0), dir2) ){
                    // MODIFICAÇÃO
                    if( dir1.get(0).isDir() ){
                        // FALTA IDENTIFICADOR DO DIRETÓRIO RAIZ
                        mod_recursively(ret, dir1);
                    } else {
                        mod(ret, (VersionedFile)dir1.get(0));
                    }
                    dir2.remove(dir1.get(0));
                }
            }
            if( !Set.belongs_to(dir2.get(0), lcs) ){
                if( dir2.get(0).isDir() ){
                    // FALTA IDENTIFICADOR DO DIRETÓRIO RAIZ
                    add_recursively(ret, dir2);
                } else {
                    add(ret, (VersionedFile)dir2.get(0));
                }
            } else {
                if( Set.belongs_to(dir2.get(0), dir1) ){
                    // MODIFICAÇÃO
                    if( dir1.get(0).isDir() ){
                        // FALTA IDENTIFICADOR DO DIRETÓRIO RAIZ
                        mod_recursively(ret, dir1);
                    } else {
                        mod(ret, (VersionedFile)dir1.get(0));
                    }
                    dir1.remove(dir2.get(0));
                }
            }
            dir1.remove(dir1.get(0));
            dir2.remove(dir2.get(0));
        }
        
        add_tail( ret, d1 );
        
        return ret;
    }
    
    private static void add_recursively(byte[] main, List<VersionedItem> dir) throws ApplicationException{
        for(int i = 0; i < dir.size(); i++){
            if(dir.get(i).isDir()){
                System.out.println("A -D " + dir.get(i).getName());
                add_recursively(main, dir);
                System.out.println("END");
            }
            else{
                add(main, (VersionedFile)dir);
            }
        }
    }
    
    private static void mod_recursively(byte[] main, List<VersionedItem> dir) throws ApplicationException{
        for(int i = 0; i < dir.size(); i++){
            if(dir.get(i).isDir()){
                System.out.println("M -D " + dir.get(i).getName());
                mod_recursively(main, dir);
                System.out.println("END");
            }
            else{
                mod(main, (VersionedFile)dir);
            }
        }
    }
    
    private static void add(byte[] main, VersionedFile file) throws ApplicationException{
        System.out.println("A -F " + file.getName() );
        System.out.println(new String(diff(new VersionedFile(), file)));
        System.out.println("END");
    }
    
    private static void mod(byte[] main, VersionedFile file) throws ApplicationException{
        System.out.println("M -F " + file.getName() );
        System.out.println(new String(diff(new VersionedFile(), file)));
        System.out.println("END");
    }

    private static List<VersionedItem> lcs(List<VersionedItem> seq1, List<VersionedItem> seq2) throws ApplicationException {
        return Set.difference(seq1, seq2);
    }
    
    // Finalizado
    protected static byte[] lcs(byte[] seq1, byte[] seq2) {

        if (!isNull(seq1) && !isNull(seq2)) {
            byte[] fline_seq1 = get_first_sequence(seq1); // Identifica primeira linha seq1
            byte[] fline_seq2 = get_first_sequence(seq2); // Identifica segunda  linha seq2

            byte[] new_seq1 = del_first_sequence( fline_seq1.length, seq1); // Remove primeiras linhas das sequências
            byte[] new_seq2 = del_first_sequence( fline_seq2.length, seq2);

            if (has_diff( del_line_wraps( fline_seq1 ), del_line_wraps( fline_seq2 ) )) {
                // Chamar duas frentes de LCS
                byte[] lcs1 = lcs(new_seq1, seq2);
                byte[] lcs2 = lcs(seq1, new_seq2);

                // Verifica qual das duas frentes possui maior sequência comum
                // Retorna
                if (countLines(lcs1) >= countLines(lcs2)) {
                    return lcs1;
                } else {
                    return lcs2;
                }
            } else {
                return appends_at_the_beginning(fline_seq1, lcs(new_seq1, new_seq2));
            }

        } else {
            return new byte[0];
        }

    }

    // Finalizado
    protected static boolean has_diff(byte[] fline_seq1, byte[] fline_seq2) {
        if ( fline_seq1.length == fline_seq2.length) {
            for (int i = 0; i < fline_seq1.length; i++) {
                if (Byte.compare(fline_seq1[i], fline_seq2[i]) != 0)
                    return true;
            }
        } else {
            return true;
        }
        return false;
    }
    
    protected static boolean has_diff(List<VersionedItem> set1, List<VersionedItem> set2) throws ApplicationException {
        if ( set1.size() == set2.size() ) {
            boolean retorno = false;
            for (int i = 0; i < set1.size() && !retorno; i++) {
                for( int j = 0; j < set2.size() && !retorno; j++ ){
                    if( set1.get(i).isDir() && set2.get(j).isDir() ){
                        retorno = has_diff( ((VersionedDir)set1).getContainedItens(), ((VersionedDir)set2).getContainedItens() );
                    } else {
                        if( !set1.get(i).isDir() && !set2.get(j).isDir() ){
                            retorno = has_diff( ((VersionedFile)set1).getContent(), ((VersionedFile)set2).getContent() );
                        }
                    }
                }
            }
            return retorno;
        } else {
            return true;
        }
    }

    private static byte[] del_first_sequence(int first_seq_size, byte[] seq) {

        byte[] retorno = new byte[seq.length - first_seq_size];

        for (int j = 0; j < retorno.length; j++)
            retorno[j] = seq[j + first_seq_size];
        
        return retorno;

    }

    private static byte[] get_first_sequence(byte[] seq) {

        if (seq.length != 0) {
            int i = 0;
            while (Byte.compare(seq[i++], EOL[0]) != 0 && i+1 <= seq.length);
            byte[] retorno = new byte[i];

            for (int j = 0; j < retorno.length; j++) {
                retorno[j] = seq[j];
            }

            return retorno;
        } else {
            return new byte[0];
        }

    }

    private static byte[] appends_at_the_beginning(byte[] fline_seq1, byte[] seq) {
        byte[] retorno = new byte[fline_seq1.length + seq.length];

        for (int i = 0; i < fline_seq1.length; i++) {
            retorno[i] = fline_seq1[i];
        }
        for (int i = 0; i < seq.length; i++) {
            retorno[i + fline_seq1.length] = seq[i];
        }

        return retorno;
    }
    
    private static byte[] appends_at_the_end(byte[] final_seq, byte[] main_seq){
        byte[] retorno = new byte[main_seq.length + final_seq.length];

        for (int i = 0; i < main_seq.length; i++) {
            retorno[i] = main_seq[i];
        }
        for (int i = 0; i < final_seq.length; i++) {
            retorno[i + main_seq.length] = final_seq[i];
        }

        return retorno;
    }

    private static int countLines(byte[] lcs_file) {
        int tam = 0;
        for (int i = 0; i < lcs_file.length; i++) {
            if (Byte.compare(lcs_file[i], EOL[0]) == 0) {
                tam++;
            }
        }
        return tam+1;
    }

    private static boolean isNull(byte[] seq) {
        return (seq.length == 0);
    }
    
    private static byte[] del_line_wraps(byte[] seq){
        int tam;
        
        if(Byte.compare(seq[seq.length-1], EOL[0]) == 0) {
            tam = seq.length-1;
        }
        else {
            tam = seq.length;
        }
        
        byte[] aux = new byte[tam];
        for(int i = 0; i < aux.length; i++){
            aux[i] = seq[i];
        }
        return aux;
    }

    private static void add_head(byte[] ret, VersionedItem dir) {
        
        byte[] eol = "\n".getBytes();
        
        // INITIALIZE
        // // /§D
        // // /§F
        if( dir.isDir() ){
            appends_at_the_beginning(ID_DIR, ret);
        } else {
            appends_at_the_beginning(ID_FILE, ret);
            appends_at_the_beginning(eol, ret);
            appends_at_the_beginning(((VersionedFile)dir).getHash().getBytes(), ret);
        }
        
        appends_at_the_beginning(eol, ret);
        
        // REC HEADER
        // // NAME
        // // AUTHOR
        appends_at_the_beginning(dir.getName().getBytes(), ret);
        appends_at_the_beginning(eol, ret);
        appends_at_the_beginning(dir.getAuthor().getBytes(), ret);
        appends_at_the_beginning(eol, ret);
    }

    private static void add_tail(byte[] ret, VersionedItem dir) {
        byte[] eol = "\n".getBytes();
        
        // FINALIZE
        // // /§D
        // // /§F
        appends_at_the_end(eol, ret);
        if( dir.isDir() ){
            appends_at_the_end(ID_DIR, ret);
        } else {
            appends_at_the_end(ID_FILE, ret);
        }
    }
    
}
