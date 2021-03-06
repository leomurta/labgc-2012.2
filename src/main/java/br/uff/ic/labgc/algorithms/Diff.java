package br.uff.ic.labgc.algorithms;

// Teste !
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.IncompatibleItensException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Evangelista
 */
public class Diff {

    // OUTPUT DELTA
    // <- Não definido ainda ! ->
    //
    private final static byte[] EOL = ("\n").getBytes();
    private final static byte[] EOF = ("\n").getBytes();
    
    private final static byte[] ID_DIR  = (new String("/§D")).getBytes();
    private final static byte[] ID_END_DIR  = (new String("/§§D")).getBytes();
    private final static byte[] ID_FILE = (new String("/§F")).getBytes();
    private final static byte[] ID_END_FILE = (new String("/§§F")).getBytes();
    
    private final static byte[] ADD = (new String("A")).getBytes();
    private final static byte[] DEL = (new String("R")).getBytes();
    private final static byte[] MOD = (new String("M")).getBytes();
    
    private final static byte[] FILE_PARAM = (new String("-F")).getBytes();
    private final static byte[] DIR_PARAM  = (new String("-D")).getBytes();
    
    private static byte[] delta1;
    
    private static byte[] read_file( String name ){
        byte[] ret = new byte[0];
        System.out.println(System.getProperty("user.home"));
        try {
            BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\" + name));
            String str;
            while (in.ready()) {
                str = in.readLine() + "\n";
                ret = appends_at_the_end(str.getBytes(), ret);
            }
            in.close();
        } catch (IOException e) {
        }
        return ret;
    }

    public static void main(String args[]) throws ApplicationException {
        byte[] testeA;
        byte[] testeB;
        String teste2 = "Caramba !" + '\n' + "tt" + '\n' + "GH" + '\n' + "G" + '\n' + "R" + '\n' + "Z";
        String teste3 = "Todos os papeis são amarelos" + '\n' + "Caramba !" + '\n' + "HTT" + '\n' + "I" + '\n' + "TG" + '\n' + "G" + '\n' + "R" + '\n' + "S";
//
        testeA = teste2.getBytes();
        testeB = teste3.getBytes();
//        
//        VersionedFile testeC = new VersionedFile();
//        VersionedFile testeD = new VersionedFile();
//        
//        testeC.setContent(testeA);
//        testeD.setContent(testeB);
//
//        //teste = getFline(teste);
//
//        //System.out.println("-->\n" + new String(lcs(testeA, testeB)));
//        System.out.println(new String(diff(testeC, testeD)));
        
        // TESTE DIFF DIRETÓRIOS
        
        // ATIVOS DE TESTE
        // // DIRETÓRIO 1
//        VersionedDir dir1 = new VersionedDir();
//        dir1.setName("MyDirectory1");
//        dir1.setAuthor("Raphael");
        
//        // // // ARQUIVO 1
//        VersionedFile file1 = new VersionedFile();
//        file1.setName("FILE1");
//        file1.setAuthor("Raphael");
//        file1.setContent(read_file("labgc_diff v2.txt"));
//        
//        // // // ARQUIVO 3
//        VersionedFile file3 = new VersionedFile();
//        file3.setName("FILE3");
//        file3.setAuthor("Raphael");
//        file3.setContent(testeA);
        
//        // // // SUB-DIRETÓRIO 1
//        VersionedDir dir3 = new VersionedDir();
//        dir3.setName("MyDirectory10");
//        
//        // // // ARQUIVO 5
//        VersionedFile file5 = new VersionedFile();
//        file5.setName("FILE5");
//        file5.setAuthor("Raphael");
//        file5.setContent(testeB);
//        
//        dir3.addItem(file5);
//        
//        // // DIRETÓRIO 2
//        VersionedDir dir2 = new VersionedDir();
//        dir2.setName("MyDirectory2");
//        dir2.setAuthor("Raphael");
        
//        // // // SUB-DIRETÓRIO 1
//        VersionedDir dir4 = new VersionedDir();
//        dir4.setName("MyDirectory4");
//        
//        // // // ARQUIVO 6
//        VersionedFile file6 = new VersionedFile();
//        file6.setName("FILE7");
//        file6.setAuthor("Raphael");
//        file6.setContent(testeB);
//        
//        dir4.addItem(file6);
        
        // // // SUB-DIRETÓRIO 2
//        VersionedDir dir5 = new VersionedDir();
//        dir5.setName("MyDirectory10");
//        
//        // // // ARQUIVO 7
//        VersionedFile file7 = new VersionedFile();
//        file7.setName("FILE7");
//        file7.setAuthor("Raphael");
//        file7.setContent(testeB);
//        
//        dir5.addItem(file7);
        
//        // // // ARQUIVO 2
//        VersionedFile file2 = new VersionedFile();
//        file2.setName("FILE2");
//        file2.setAuthor("Raphael");
//        file2.setContent(read_file("labgc_diff v3.txt"));
//        
//        // // // ARQUIVO 4
//        VersionedFile file4 = new VersionedFile();
//        file4.setName("FILE3");
//        file4.setAuthor("Raphael");
//        file4.setContent(testeB);
//        
//        dir1.addItem(file1);
//        dir1.addItem(file3);
//        dir1.addItem(dir3);

//        dir2.addItem(file2);
//        dir2.addItem(file4);
//        dir2.addItem(dir5);
//
//        System.out.println("-------------------------");
//        for( int i = 0; i < dir1.getContainedItens().size(); i++ ){
//            System.out.println( dir1.getContainedItens().get(i).getName() );
//        }
//        System.out.println("-------------------------");
//        for( int i = 0; i < dir2.getContainedItens().size(); i++ ){
//            System.out.println( dir2.getContainedItens().get(i).getName() );
//        }
//        System.out.println("-------------------------");
//        List<VersionedItem> teste = lcs(dir1.getContainedItens(), dir2.getContainedItens());
//        for( int i = 0; i < teste.size(); i++ ){
//            System.out.println( teste.get(i).getName() );
//        }
//        System.out.println("-------------------------");
//        System.out.println(new String(diff(dir1, dir2)));
//        System.out.println("-------------------------");
//        VersionedDir teste_final = apply( dir1, diff(dir1, dir2) );
//        System.out.println("-------------------------");
//        System.out.println(teste_final.getName());
//        show(teste_final.getContainedItens(), 1);
//        System.out.println("-------------------------");
//        Memorization.init_matrix();
//        Memorization.consider_values(file1.getContent().clone(), file2.getContent().clone(), file1.getContent().clone());
//        Memorization.consider_values(file1.getContent().clone(), file1.getContent().clone(), file1.getContent().clone());
//        System.out.println(new String(lcs_dp(file1.getContent().clone(), file2.getContent().clone())));
//        System.out.println(new String(lcs(file1.getContent().clone(), file2.getContent().clone())));
//        System.out.println(new String(lcs_dp(file1.getContent().clone(), file2.getContent().clone())));
//        System.out.println(new String(diff(file1, file2)));
//        System.out.println(new String(lcs(file1.getContent().clone(), file1.getContent().clone())));
        //System.out.println(new String(lcs(file1.getContent().clone(), file2.getContent().clone())));
        //show( teste_final.getContainedItens(), 0 );
        
    }
    
    public static void show( List<VersionedItem> lista, int ident ) throws ApplicationException{
        String vazio = "";
        for( int i = 0; i < ident; i++ ){
            vazio += " ";
        }
        for( int i = 0; i < lista.size(); i++ ){
            System.out.println( vazio + "\\ " + lista.get(i).getName() );
            if( lista.get(i).isDir() ){
                show( ((VersionedDir)lista.get(i)).getContainedItens(), ident+1 );
            } else {
                System.out.println(new String(((VersionedFile)lista.get(i)).getContent()));
            }
        }
    }

    public static byte[] run(VersionedItem file1, VersionedItem file2) throws ApplicationException, IncompatibleItensException {

        if ( !file1.isDir() && !file2.isDir() ) {
            return diff( ((VersionedFile)file1), ((VersionedFile)file2) );
        } else {
            if ( file1.isDir() && file2.isDir() ) {
                return diff( ((VersionedDir)file1), ((VersionedDir)file2) );
            } else {
                if ( file1.isDir() && !file2.isDir() ){
                    return null;
                }
            }
        }
        return null;
    }

    public static byte[] apply(VersionedFile file1, byte[] delta) throws ApplicationException {
        
        // ARQUIVO DE RETORNO
        byte[] ret  = new byte[0];
        byte[] file = file1.getContent().clone();
        
        delta1 = delta.clone();
        byte[] first_line = get_first_sequence(delta1);
        delta1 = del_first_sequence(first_line.length, delta1);

        
        int block = 0;
        int current_line = 0;

        if( !has_diff( del_line_wraps(first_line), ID_FILE ) ){
        
            // ITERAR ATÉ ACHAR O ID_END_FILE
            first_line = get_first_sequence(delta1);
            delta1 = del_first_sequence(first_line.length, delta1);
            
            while( has_diff( del_line_wraps(first_line), ID_END_FILE ) ){
                
                while( current_line < Integer.valueOf( new String( get_param( del_line_wraps(first_line), 3 ) ) ) ){
                    ret = appends_at_the_end(get_first_sequence(file), ret);
                    file = del_first_sequence(get_first_sequence(file).length, file);
                    current_line++;
                }
                // IDENTIFICA QUAL COMANDO
                // A ou R
                if( !has_diff( get_param( del_line_wraps(first_line), 1 ), DEL ) ){
                    // DEL_LINE
                    // SE O BLOCO FOR O MESMO
                    // ITERAR NO ARQUIVO PRINCIPAL ATÉ A LINHA CORRESPONDENTE
                    if( block == Integer.valueOf( new String( get_param( del_line_wraps(first_line), 2 ) ) ) ){
                        int it = Integer.valueOf( new String( get_param( del_line_wraps(first_line), 4 ) ) );
                        for( int i = Integer.valueOf( new String( get_param( del_line_wraps(first_line), 3 ) ) ); i < it+1; i++ ){
                            first_line = get_first_sequence(file);
                            file = del_first_sequence(first_line.length, file);
                            current_line++;
                        }
                        first_line = get_first_sequence(delta1);
                        delta1 = del_first_sequence(first_line.length, delta1);
                    }
                }

                
                if( !has_diff( get_param( first_line, 1 ), ADD ) ){
                    // ADD_LINE
                    // SE O BLOCO FOR O MESMO
                    // ADD LINHAS NOVAS NO ARQUIVO DE RETORNO
                    if( block == Integer.valueOf( new String( get_param( del_line_wraps(first_line), 2 ) ) ) ){
                        int it = Integer.valueOf( new String( get_param( del_line_wraps(first_line), 4 ) ) );
                        for( int i = 0; i < it; i++ ){
                            first_line = get_first_sequence(delta1);
                            delta1 = del_first_sequence(first_line.length, delta1);
                            ret = appends_at_the_end(first_line, ret);
                        }
                        first_line = get_first_sequence(delta1);
                        delta1 = del_first_sequence(first_line.length, delta1);
                    }
                }
                
                block++;
            }
            
        } else {
            // TÁ ERRADO
        }
        
        while( !isNull(file) ){
            ret = appends_at_the_end(get_first_sequence(file), ret);
            file = del_first_sequence(get_first_sequence(file).length, file);
        }
        
        return ret;
    }
    
    public static VersionedDir apply(VersionedDir dir, byte[] delta) throws ApplicationException{
        
        
        // DIRETÓRIO DE RETORNO
        VersionedDir ret = new VersionedDir();
        ret.setName(dir.getName());
        ret.addItem(dir.getContainedItens());
        
        delta1 = delta.clone();        
        byte[] first_line = get_first_sequence(delta1);
        delta1 = del_first_sequence(first_line.length, delta1);
        
        if( !has_diff( del_line_wraps(first_line), ID_DIR ) ){
            
            first_line = get_first_sequence(delta1);
            delta1 = del_first_sequence(first_line.length, delta1);
            
            while( has_diff( del_line_wraps(first_line), ID_END_DIR ) ){
                
                if( !has_diff( get_param(first_line, 1), ADD ) ){
                    
                    if( !has_diff( get_param(first_line, 2), FILE_PARAM ) ){
                        
                        // CRIA NOVO ARQUIVO
                        VersionedFile file_aux = new VersionedFile();
                        file_aux.setName( new String( del_line_wraps(get_param(first_line, 3) )) );
                        file_aux.setContent( new byte[0] );
                        
                        file_aux.setContent( apply( file_aux, delta1 ) );
                        
                        ret.addItem( file_aux );
                        
                    } else {
                        
                        if( !has_diff( get_param(first_line, 2), DIR_PARAM ) ){
                            
                            // CRIA NOVO DIRETÓRIO
                            VersionedDir dir_aux = new VersionedDir();
                            dir_aux.setName( new String( del_line_wraps(get_param(first_line, 3) )) );
                            dir_aux = apply( dir_aux, delta1 );
                            
                            ret.addItem( dir_aux );
                            
                        }
                        
                    }
                    
                } else {
                    if( !has_diff( get_param(first_line, 1), DEL ) ){

                        if( !has_diff( get_param(first_line, 2), FILE_PARAM ) ){

                            // REMOVE ARQUIVO
                            List<VersionedItem> list_aux = ret.getContainedItens();
                            VersionedFile aux = new VersionedFile();
                            aux.setName( new String( del_line_wraps(get_param(first_line, 3))) );
                            list_aux.remove( Set.belongs_to( new String( del_line_wraps(get_param(first_line, 3))) , list_aux) );
                            
                            ret.setContainedItens( list_aux );

                        } else {

                            if( !has_diff( get_param(first_line, 2), DIR_PARAM ) ){

                                // REMOVE DIRETÓRIO
                                List<VersionedItem> list_aux = ret.getContainedItens();
                                VersionedDir aux = new VersionedDir();
                                list_aux.remove( Set.belongs_to(new String(del_line_wraps(get_param(first_line, 3))), list_aux) );

                                ret.setContainedItens( list_aux );

                            }

                        }

                    } else {
                        
                        if( !has_diff( get_param(first_line, 1), MOD ) ){

                            if( !has_diff( get_param(first_line, 2), FILE_PARAM ) ){

                                // MODIFICA ARQUIVO
                                List<VersionedItem> list_aux = ret.getContainedItens();
                                VersionedFile older = new VersionedFile();
                                VersionedFile newer = new VersionedFile();
                                
                                older = (VersionedFile)list_aux.get( Set.belongs_to(new String( del_line_wraps( get_param(first_line, 3) ) ), list_aux) );
                                
                                newer.setName( new String( del_line_wraps( get_param(first_line, 3) ) ) );
                                
                                newer.setContent( apply( older, delta1 ) );
                                
                                list_aux.set( Set.belongs_to(new String( del_line_wraps( get_param(first_line, 3) ) ), list_aux), newer );

                                ret.setContainedItens( list_aux );

                            } else {

                                if( !has_diff( get_param(first_line, 2), DIR_PARAM ) ){

                                    // MODIFICA DIRETÓRIO
                                    List<VersionedItem> list_aux = ret.getContainedItens();
                                    
                                    VersionedDir older = new VersionedDir();
                                    older = (VersionedDir)list_aux.get( Set.belongs_to(new String( del_line_wraps( get_param(first_line, 3) ) ), list_aux) );
                                    
                                    VersionedDir newer = apply( older, delta1 );

                                    list_aux.set( Set.belongs_to(new String( del_line_wraps( get_param(first_line, 3) ) ), list_aux), newer );

                                    ret.setContainedItens( list_aux );

                                }

                            }

                        }
                        
                    }
                }
                
            first_line = get_first_sequence(delta1);
            delta1 = del_first_sequence(first_line.length, delta1);
                
            }
        }
        
        return ret;
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
        
        ret += new String( add_head(new byte[0], file1) );
        
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
                ret += ("A " + h + " " + i_aux + " " + lines_added.size() ) + '\n';
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
        int i_aux = i;
        boolean teste = false;
        while( !isNull(seq1) ){
            i++;
            seq1 = del_first_sequence( get_first_sequence(seq1).length, seq1 );
            teste = true;
        }
        if(teste)
            ret += ("R " + h + " " + i_aux + " " + (i-1) ) + '\n';
        
        // ADICIONA TODAS AS LINHAS APÓS LCS DA SEQ2

            lines_added.removeAll(lines_added);
            teste = false;
            while( !isNull(seq2) ){
                lines_added.add( new String( del_line_wraps( get_first_sequence(seq2) ) ) );
                seq2 = del_first_sequence( get_first_sequence(seq2).length, seq2 );
                teste = true;
            }
            if(teste){
                ret += ("A " + h + " " + i_aux + " " + lines_added.size() ) + '\n';
                for( int r = 0; r < lines_added.size(); r++ ){
                    ret += ( lines_added.get(r) ) + '\n';
                }
            }
        
        ret += new String( add_tail(new byte[0], file1) );

        return ret.getBytes();
    }

    private static byte[] diff(VersionedDir d1, VersionedDir d2) throws ApplicationException {
        // DIFFERENCE BETWEEN DIRECTORIES
        // TODO: Transformar o contador de Diff em uma variável global.

        byte[] ret = new byte[0];
        
        int count_diff = 0;
        byte[] arq_diff = new byte[0];
        
        ArrayList<VersionedItem> d1_aux = new ArrayList<>();
        ArrayList<VersionedItem> d2_aux = new ArrayList<>();
        
        d1_aux.addAll(d1.getContainedItens());
        d2_aux.addAll(d2.getContainedItens());
        
        List<VersionedItem> dir1 = d1_aux.subList(0, d1_aux.size());
        List<VersionedItem> dir2 = d2_aux.subList(0, d2_aux.size());
        
        int i = 0;
        int j = 0;
        
        List<VersionedItem> lcs = lcs(dir1, dir2);
        
        ret = appends_at_the_beginning(ret, add_head( ret, d1 ));
        
        while( dir1.size() > 0 || dir2.size() > 0 ){
            if( dir1.size() > 0 ){
                if( Set.belongs_to(dir1.get(0), lcs) == -1 ){
                    if( dir1.get(0).isDir() ){
                        ret = appends_at_the_beginning(ret, ("R -D " + dir1.get(0).getName() + '\n').getBytes());
                    } else {
                        ret = appends_at_the_beginning(ret, ("R -F " + dir1.get(0).getName() + '\n').getBytes());
                    }
                } else {
                    if( dir1.get(0).isDir() ){
                        ret = mod_recursively(ret, ((VersionedDir)dir1.get(0)).getContainedItens(), ((VersionedDir)dir2.get(Set.belongs_to(dir1.get(0), dir2))).getContainedItens());
                    } else {
                        ret = mod(ret, (VersionedFile)dir1.get(0), (VersionedFile)dir2.get(Set.belongs_to(dir1.get(0), dir2)));
                    }
                    dir2.remove(Set.belongs_to(dir1.get(0), dir2));
                }
                dir1.remove(dir1.get(0));
            }
            if( dir2.size() > 0 ){
                if( Set.belongs_to(dir2.get(0), lcs) == -1 ){
                    if( dir2.get(0).isDir() ){
                        ret = appends_at_the_beginning(ret, ("A -D " + ((VersionedDir)dir2.get(0)).getName() + '\n').getBytes());
                        ret = appends_at_the_beginning(ret, add_head( ret, (VersionedDir)dir2.get(0) ));
                        ret = add_recursively(ret, ((VersionedDir)dir2.get(0)).getContainedItens());
                        ret = appends_at_the_beginning(ret, add_tail( ret, ((VersionedDir)dir2.get(0)) ));
                    } else {
                        ret = add(ret, (VersionedFile)dir2.get(0));
                    }
                } else {
                    if( dir2.get(0).isDir() ){
                        ret = appends_at_the_beginning(ret, ("M -D " + d2.getName() + '\n').getBytes());
                        ret = appends_at_the_beginning(ret, add_head( ret, d2 ));
                        ret = mod_recursively(ret, ((VersionedDir)dir1.get(Set.belongs_to(dir2.get(0), dir1))).getContainedItens(), ((VersionedDir)dir2.get(0)).getContainedItens());
                        ret = appends_at_the_beginning(ret, add_tail( ret, d2 ));
                    } else {
                        ret = mod(ret, ((VersionedFile)dir1.get(Set.belongs_to(dir2.get(0), dir1))), (VersionedFile)dir2.get(0));
                    }
                    dir1.remove(Set.belongs_to(dir2.get(0), dir1));
                }
                dir2.remove(dir2.get(0));
            }
        }
        
        ret = appends_at_the_beginning(ret, add_tail( ret, d1 ));
        
        return ret;
    }
    
    private static byte[] add_recursively(byte[] main, List<VersionedItem> dir ) throws ApplicationException{
        for(int i = 0; i < dir.size(); i++){
            if(dir.get(i).isDir()){
                main = appends_at_the_beginning(main, ("A -D " + dir.get(i).getName() + '\n').getBytes());
                main = appends_at_the_beginning(main, add_head( main, dir.get(i) ));
                main = add_recursively(main, dir);
                main = appends_at_the_beginning(main, add_tail( main, dir.get(i) ));
            }
            else{
                main = add(main, (VersionedFile)dir.get(i));
            }
        }
        return main;
    }
    
    private static byte[] mod_recursively(byte[] main, List<VersionedItem> dir, List<VersionedItem> dir_final ) throws ApplicationException{
        for(int i = 0; i < dir.size(); i++){
            if(dir.get(i).isDir()){
                if( Set.belongs_to(dir.get(i), dir_final) != -1 ){
                    main = appends_at_the_beginning(main, ("M -D " + dir.get(i).getName() + '\n').getBytes());
                    main = appends_at_the_beginning(main, add_head( main, dir.get(i) ));
                    main = mod_recursively(main, dir, ((VersionedDir)dir_final.get(Set.belongs_to(dir.get(i), dir_final))).getContainedItens());
                    main = appends_at_the_beginning(main, add_tail( main, dir.get(i) ));
                    dir_final.remove(Set.belongs_to(dir.get(i), dir_final));
                } else {
                    main = appends_at_the_beginning(main, ("R -D " + dir.get(i).getName() + '\n').getBytes());
                }
            }
            else{
                if( Set.belongs_to(dir.get(i), dir_final) != -1 ){
                    main = mod(main, (VersionedFile)dir.get(i), (VersionedFile)dir_final.get(Set.belongs_to(dir.get(i), dir_final)));
                    dir_final.remove(Set.belongs_to(dir.get(i), dir_final));
                } else {
                    main = appends_at_the_beginning(main, ("R -F " + dir.get(i).getName() + '\n').getBytes());
                }
            }
        }
        for( int i = 0; i < dir_final.size(); i++ ){
            if( dir_final.get(i).isDir() ){
            main = appends_at_the_beginning(main, ("A -D " + dir_final.get(i).getName() + '\n').getBytes());
            main = appends_at_the_beginning(main, add_head( main, dir_final.get(i) ));
            add_recursively(main, ((VersionedDir)dir_final.get(i)).getContainedItens());
            main = appends_at_the_beginning(main, add_tail( main, dir.get(i) ));
            } else {
                add(main, (VersionedFile)dir_final.get(i));
            }
        }
        return main;
    }
    
    private static byte[] add(byte[] main, VersionedFile file) throws ApplicationException{
        VersionedFile aux = new VersionedFile("", new Date(), "", "", "");
        aux.setContent(new byte[0]);
        main = appends_at_the_beginning(main, ("A -F " + file.getName()  + '\n').getBytes());
        main = appends_at_the_beginning(main, diff(aux, file));
        return main;
    }
    
    private static byte[] mod(byte[] main, VersionedFile file, VersionedFile file_final) throws ApplicationException{
        main = appends_at_the_beginning(main, ("M -F " + file.getName()  + '\n').getBytes());
        main = appends_at_the_beginning(main, diff(file, file_final));
        return main;
    }

    private static List<VersionedItem> lcs(List<VersionedItem> seq1, List<VersionedItem> seq2) throws ApplicationException {
        return Set.intersection(seq1, seq2);
    }
    
    protected static byte[] lcs(byte[] seq1, byte[] seq2) {
        Memorization.init_matrix();
        return lcs_dp(seq1, seq2);
    }
    
    // Finalizado
    protected static byte[] lcs_wdp(byte[] seq1, byte[] seq2) {

        if (!isNull(seq1) && !isNull(seq2)) {
            byte[] fline_seq1 = get_first_sequence(seq1); // Identifica primeira linha seq1
            byte[] fline_seq2 = get_first_sequence(seq2); // Identifica segunda  linha seq2

            byte[] new_seq1 = del_first_sequence( fline_seq1.length, seq1.clone()); // Remove primeiras linhas das sequências
            byte[] new_seq2 = del_first_sequence( fline_seq2.length, seq2.clone());

            if (has_diff( del_line_wraps( fline_seq1 ), del_line_wraps( fline_seq2 ) )) {
                // Chamar duas frentes de LCS
                byte[] lcs1;
                byte[] lcs2;
                lcs1 = lcs_wdp(new_seq1, seq2);
                lcs2 = lcs_wdp(seq1, new_seq2);

                // Verifica qual das duas frentes possui maior sequência comum
                // Retorna
                if (countLines(lcs1) >= countLines(lcs2)) {
                    return lcs1;
                } else {
                    return lcs2;
                }
            } else {
                return appends_at_the_beginning(fline_seq1, lcs_wdp(new_seq1, new_seq2));
            }

        } else {
            return new byte[0];
        }

    }
    
    protected static byte[] lcs_dp(byte[] seq1, byte[] seq2) {

        if (!isNull(seq1) && !isNull(seq2)) {
            
            byte[] fline_seq1 = get_first_sequence(seq1); // Identifica primeira linha seq1
            byte[] fline_seq2 = get_first_sequence(seq2); // Identifica segunda  linha seq2

            byte[] new_seq1 = del_first_sequence( fline_seq1.length, seq1.clone()); // Remove primeiras linhas das sequências
            byte[] new_seq2 = del_first_sequence( fline_seq2.length, seq2.clone());

            if (has_diff( del_line_wraps( fline_seq1 ), del_line_wraps( fline_seq2 ) )) {
                // Chamar duas frentes de LCS
                byte[] lcs1;
                byte[] lcs2;
                lcs1 = Memorization.consider_values(new_seq1, seq2);
                lcs2 = Memorization.consider_values(seq1, new_seq2);

                // Verifica qual das duas frentes possui maior sequência comum
                // Retorna
                if (countLines(lcs1) >= countLines(lcs2)) {
                    return lcs1;
                } else {
                    return lcs2;
                }
            } else {
                //System.out.println(new String(Memorization.consider_values(new_seq1, new_seq2)));
                return appends_at_the_beginning(fline_seq1, Memorization.consider_values(new_seq1, new_seq2));
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
        if( lcs_file != null ){
        int tam = 0;
        for (int i = 0; i < lcs_file.length; i++) {
            if (Byte.compare(lcs_file[i], EOL[0]) == 0) {
                tam++;
            }
        }
        return tam+1;
        } else {
            return 0;
        }
    }

    protected static boolean isNull(byte[] seq) {
        return (seq.length == 0 );
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

    private static byte[] add_head(byte[] ret, VersionedItem dir) {
        
        byte[] eol = "\n".getBytes();
        byte[] ret_aux = new byte[0];
        
        // INITIALIZE
        // // /§D
        // // /§F
        if( dir.isDir() ){
            ret_aux = appends_at_the_end(ID_DIR, ret_aux);
        } else {
            ret_aux = appends_at_the_end(ID_FILE, ret_aux);
//            ret_aux = appends_at_the_end(eol, ret_aux);
//            ret_aux = appends_at_the_end(((VersionedFile)dir).getHash().getBytes(), ret_aux);
        }
        
        ret_aux = appends_at_the_end(eol, ret_aux);
        
        // REC HEADER
        // // NAME
        // // AUTHOR
//        System.out.println(dir.getName());
//        ret_aux = appends_at_the_end(dir.getName().getBytes(), ret_aux);
//        ret_aux = appends_at_the_end(eol, ret_aux);
//        ret_aux = appends_at_the_end(dir.getAuthor().getBytes(), ret_aux);
//        ret_aux = appends_at_the_end(eol, ret_aux);
        
        return ret_aux;
    }

    private static byte[] add_tail(byte[] ret, VersionedItem dir) {
        byte[] eol = "\n".getBytes();
        
        // FINALIZE
        // // /§D
        // // /§F
        // ret = appends_at_the_end(eol, ret);
        if( dir.isDir() ){
            ret = appends_at_the_end(ID_END_DIR, new byte[0]);
        } else {
            ret = appends_at_the_end(ID_END_FILE, new byte[0]);
        }
        ret = appends_at_the_end(eol, ret);
        
        return ret;
    }
    
    private static byte[] get_param( byte[] args, int param ){
        String[] retorno = (new String(args)).split(" ");
        return retorno[param-1].getBytes();
    }
    
}
