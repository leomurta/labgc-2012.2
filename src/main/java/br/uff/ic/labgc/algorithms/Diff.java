package br.uff.ic.labgc.algorithms;

// Teste !
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.IncompatibleItensException;
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
    private final static byte EOL = (new String("\n")).getBytes()[0];
    private final static byte EOF = (new String("\n")).getBytes()[0];

    public static void main(String args[]) throws ApplicationException {
        byte[] testeA;
        byte[] testeB;
        String teste2 = "B" + '\n' + "C";
        String teste3 = "B" + '\n' + "A";

        testeA = teste2.getBytes();
        testeB = teste3.getBytes();

        //teste = getFline(teste);

        //System.out.println("-->\n" + new String(lcs(testeA, testeB)));
        System.out.println(new String(diff_archives(new byte[0], testeB)));
    }

    public static byte[] run(VersionedItem file1, VersionedItem file2) throws ApplicationException, IncompatibleItensException {

        if (isFile(file1) && isFile(file2)) {
            return diff_archives( ((VersionedFile)file1).getContent(), ((VersionedFile)file2).getContent() );
        } else {
            if (!isFile(file1) && !isFile(file2)) {
                return null; //diff_directories((VersionedDir) file1, (VersionedDir) file2);
            } else {
                throw new IncompatibleItensException();
            }
        }
    }

    public static byte[] patch(VersionedItem file1, byte[] delta) {

        byte[] delta_aux = delta;

        while (getFline(delta_aux)[0] != EOF) {
            // Comparação de Bytes
            // Se R, Remove
            // Se A, Adiciona
        }

        return null;

    }

    private static byte[] diff_archives(byte[] file1, byte[] file2) throws ApplicationException {
        // Verifica se os Hashes de arquivo são diferentes
        // Chama LCS
        // 
        
        String ret = "";
        
        // PRIMEIRO CASO
        // REMOVE TODOS OS ARQUIVOS DE FILE1
        // ADICIONA TODOS OS ARQUIVOS DE FILE2
        
        byte[] seq_aux2 = file2.clone();
        byte[] aux;
        ArrayList<String> linhas_seq2 = new ArrayList<>();
        if(file1.length != 0)
            System.out.println("R " + "0" + " " + countLines(file1));
        
        while(!isNull(seq_aux2)){
            aux = getFline(seq_aux2);
            linhas_seq2.add(new String(del_line_wraps(aux)));
            seq_aux2 = delFline(aux, seq_aux2);
        }
        System.out.println("A " + "0" + " " + linhas_seq2.size());
        for(int i = 0; i < linhas_seq2.size(); i++){
            System.out.println(linhas_seq2.get(i));
        }

        return ret.getBytes();
        
//        byte[] seq1 = file1.clone();
//        byte[] seq2 = file2.clone();
//        byte[] lcs = lcs(seq1, seq2);
//        byte[] lcs2 = lcs.clone();
//        
//        byte[] final_file;
//
//        ArrayList<String> linhas_add = new ArrayList<>();
//
//        System.out.println("-------------");
//        System.out.println("SEQ1---------");
//        System.out.println(new String(seq1));
//        System.out.println("SEQ2---------");
//        System.out.println(new String(seq2));
//        System.out.println("LCS----------");
//        System.out.println(new String(lcs));
//        System.out.println("-------------");
//
//        // Registra METADADOS dos arquivos que estão sendo diferenciados
//        int i = 0;
//        int j = 0;
//        int h = 0;
//        int k = 0;
//        int tam_seq1 = countLines(seq1) + 1;
//        int tam_seq2 = countLines(seq2);
//        int fim = countLines(lcs) + 1;
//
//        //if( file1.getHash() != file2.getHash() ){
//
//        while (h <= fim || k <= fim) {
//            if( h <= fim ){
//            if (!hasDiff(getFline(seq1), getFline(lcs))) {
//                seq1 = delFline(getFline(seq1), seq1);
//            } else {
//                // Remove da linha "i" até que seq1[i] == lcs[h]
//                int i_aux = i;
//                while (hasDiff(getFline(seq1), getFline(lcs)) && !isNull(seq1)) {
//                    seq1 = delFline(getFline(seq1), seq1);
//                    i++;
//                }
//                // R i_aux i
//                //System.out.println("R " + i_aux + " " + (i - 1));
//                retorno += "R " + i_aux + " " + (i - 1) + '\n';
//            }
//            i++;
//            lcs = delFline(getFline(lcs), lcs);
//            h++;
//            }
//            if( k <= fim ){
//            if (!hasDiff(getFline(seq2), getFline(lcs2))) {
//                seq2 = delFline(getFline(seq2), seq2);
//            } else {
//                // Remove da linha "i" até que seq1[i] == lcs[h]
//                int j_aux = j;
//                linhas_add.removeAll(linhas_add);
//                while (hasDiff(getFline(seq2), getFline(lcs2)) && !isNull(seq2)) {
//                    linhas_add.add(new String(getFline(seq2)));
//                    seq2 = delFline(getFline(seq2), seq2);
//                    j++;
//                }
//                seq2 = delFline(getFline(seq2), seq2);
//                // R i_aux i
//                //System.out.println("A " + j_aux + " " + linhas_add.size());
//                retorno += "A " + j_aux + " " + linhas_add.size() + '\n';
//                for (int z = 0; z < linhas_add.size(); z++) {
//                    //System.out.print(linhas_add.get(z));
//                    retorno += linhas_add.get(z);
//                    if( z + 1 == linhas_add.size() ){
//                        retorno += '\n';
//                    }
//                }
//            }
//            j++;
//            lcs2 = delFline(getFline(lcs2), lcs2);
//            k++;
//            }
//        }
//
//        if( (i-2) != (tam_seq1-1) ) {
//            //System.out.println("R " + (i - 2) + " " + (tam_seq1-1));
//            retorno += "R " + (i - 2) + " " + (tam_seq1-1) + '\n';
//        }
//        
//        linhas_add.removeAll(linhas_add);
//        while (!isNull(seq2)) {
//            linhas_add.add(new String(getFline(seq2)));
//            seq2 = delFline(getFline(seq2), seq2);
//        }
//        if (linhas_add.size() > 0) {
//            //System.out.println("A " + (j - 1) + " " + linhas_add.size());
//            retorno += "A " + (j - 1) + " " + linhas_add.size() + '\n';
//            for( int s = 0; s < linhas_add.size(); s++ ){
//                //System.out.println(linhas_add.get(s));
//                retorno += linhas_add.get(s);
//                if( s + 1 == linhas_add.size() ){
//                    retorno += '\n';
//                }
//            }
//        }
//
//
//        return retorno.getBytes();
    }

    private static byte[] diff_directories(List<VersionedItem> dir1, List<VersionedItem> dir2) throws ApplicationException {
        // PRIMEIRO CASO
        // DELETA TODOS OS ARQUIVOS E DIRETÓRIOS DE DIR1
        // ADICIONA TODOS OS ARQUIVOS E DIRETÓRIOS DE DIR2
        byte[] ret = new byte[0];
        
        int count_diff = 0;
        byte[] arq_diff = new byte[0];
        
        for(int i = 0; i < dir1.size(); i++){
            if(dir1.get(i).isDir())
                System.out.println("R " + dir1.get(i).getName() + " -D");
            else
                System.out.println("R " + dir1.get(i).getName() + " -F");
        }
        add_directory_recursively(ret, arq_diff, count_diff, dir2);
        
        return null;
    }
    
    private static void add_directory_recursively(byte[] main, byte[] diff, int count_diff, List<VersionedItem> dir) throws ApplicationException{
        
        for(int i = 0; i < dir.size(); i++){
            if(dir.get(i).isDir()){
                System.out.println("A " + dir.get(i).getName() + " -D");
                add_directory_recursively(main, diff, count_diff, dir);
                System.out.println("ENDD");
            }
            else{
                add_file(main, diff, count_diff, ((VersionedFile)dir).getName(), ((VersionedFile)dir).getContent());
                count_diff++;
            }
        }
    }
    
    private static void add_file(byte[] main, byte[] diff, int count_diff, String file_name, byte[] file_content) throws ApplicationException{
        System.out.println("A " + file_name + " -F " + count_diff);
        diff = addFline(diff_archives(new byte[0], file_content), diff);
        diff = addFline(("\n§§" + count_diff).getBytes(), diff);
    }

    // Finalizado
    private static boolean isFile(VersionedItem file1) {
        return !file1.isDir();
    }
    
    private static byte[] lcs_dir(List<VersionedItem> seq1, List<VersionedItem> seq2) {
        return null;
//        if (!isNull(seq1) && !isNull(seq2)) {
//            byte[] fline_seq1 = getFline(seq1); // Identifica primeira linha seq1
//            byte[] fline_seq2 = getFline(seq2); // Identifica segunda  linha seq2
//
//            byte[] new_seq1 = delFline(fline_seq1, seq1); // Remove primeiras linhas das sequências
//            byte[] new_seq2 = delFline(fline_seq2, seq2);
//
//            if (hasDiff(fline_seq1, fline_seq2)) {
//                // Chamar duas frentes de LCS
//                byte[] lcs1 = lcs(new_seq1, seq2);
//                byte[] lcs2 = lcs(seq1, new_seq2);
//
//                // Verifica qual das duas frentes possui maior sequência comum
//                // Retorna
//                if (countLines(lcs1) >= countLines(lcs2)) {
//                    return lcs1;
//                } else {
//                    return lcs2;
//                }
//            } else {
//                return addFline(fline_seq1, lcs(new_seq1, new_seq2));
//            }
//
//        } else {
//            return new byte[0];
//        }

    }

    // Finalizado
    private static byte[] lcs(byte[] seq1, byte[] seq2) {

        if (!isNull(seq1) && !isNull(seq2)) {
            byte[] fline_seq1 = getFline(seq1); // Identifica primeira linha seq1
            byte[] fline_seq2 = getFline(seq2); // Identifica segunda  linha seq2

            byte[] new_seq1 = delFline(fline_seq1, seq1); // Remove primeiras linhas das sequências
            byte[] new_seq2 = delFline(fline_seq2, seq2);

            if (hasDiff(fline_seq1, fline_seq2)) {
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
                return addFline(fline_seq1, lcs(new_seq1, new_seq2));
            }

        } else {
            return new byte[0];
        }

    }

    // Finalizado
    private static boolean hasDiff(byte[] fline_seq1, byte[] fline_seq2) {
        if (!(isNull(fline_seq1) && isNull(fline_seq2)) && fline_seq1.length == fline_seq2.length) {
            for (int i = 0; i < fline_seq1.length; i++) {
                if (Byte.compare(fline_seq1[i], fline_seq2[i]) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static byte[] getAndDelFline(byte[] seq) {
        byte[] retorno = getFline(seq);
        seq = delFline(retorno, seq);
        return retorno;
    }

    private static byte[] delFline(byte[] fline_seq1, byte[] seq) {

        byte[] retorno = new byte[seq.length - fline_seq1.length];

        for (int j = 0; j < retorno.length; j++) {
            retorno[j] = seq[j + fline_seq1.length];
        }

        return retorno;

    }

    private static byte[] getFline(byte[] seq) {

        if (seq.length != 0) {
            int i = 0;
            while (Byte.compare(seq[i++], EOL) != 0 && i+1 <= seq.length);
            byte[] retorno = new byte[i];

            for (int j = 0; j < i; j++) {
                retorno[j] = seq[j];
            }

            return retorno;
        } else {
            return new byte[0];
        }

    }

    private static byte[] addFline(byte[] fline_seq1, byte[] seq) {
        byte[] retorno = new byte[fline_seq1.length + seq.length];

        for (int i = 0; i < fline_seq1.length; i++) {
            retorno[i] = fline_seq1[i];
        }
        for (int i = 0; i < seq.length; i++) {
            retorno[i + fline_seq1.length] = seq[i];
        }

        return retorno;
    }

    private static int countLines(byte[] lcs_file) {
        int tam = 0;
        for (int i = 0; i < lcs_file.length; i++) {
            if (Byte.compare(lcs_file[i], EOL) == 0) {
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
        
        if(Byte.compare(seq[seq.length-1], EOL) == 0)
            tam = seq.length-1;
        else
            tam = seq.length;
        
        byte[] aux = new byte[tam];
        for(int i = 0; i < aux.length; i++){
            aux[i] = seq[i];
        }
        return aux;
    }
}
