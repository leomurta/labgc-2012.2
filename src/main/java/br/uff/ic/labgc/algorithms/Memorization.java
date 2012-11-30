/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.algorithms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import sun.security.util.Length;

/**
 *
 * @author Evangelista
 */
public class Memorization {
    
    private final static int TAM_I = 50;
    private final static int TAM_J = 50;
    
    private static ArrayList<byte[]> line = new ArrayList<>();
    private static ArrayList<byte[]> column = new ArrayList<>();
    private static int[][] matrix = new int[5000][5000];
    private static ArrayList<byte[]> values = new ArrayList<>();
    
    public static byte[] consider_values( byte[] seq1, byte[] seq2 ){
        
        int i = 0;
        int j = 0;
        
        byte[] hash1 = generate_hash(seq1);
        byte[] hash2 = generate_hash(seq2);
        
//        System.out.println("INI------------");
//        System.out.println(new String(seq1));
//        System.out.println("---------------");
//        System.out.println(new String(hash1));
//        System.out.println("---------------");
//        System.out.println(new String(seq2));
//        System.out.println("---------------");
//        System.out.println(new String(hash2));
//        System.out.println("FIM------------");
        
        if( belongs_to(hash1, true) != -1 ){
            
            if( belongs_to(hash2, false) != -1 ){
                
                // Retorna valor
                if( matrix[belongs_to(hash1, true)][belongs_to(hash2, false)] != -1 ){
                    return values.get(matrix[belongs_to(hash1, true)][belongs_to(hash2, false)]);
                } else {
                    i = belongs_to(hash1, true);
                    j = belongs_to(hash2, false);
                }
                
            } else {
                
                if( belongs_to(hash2, true) == -1 ){
                    
                    // ADD H2 COLUNA
                    column.add(hash2);
                    i = belongs_to(hash1, true);
                    j = column.size() - 1;
                    
                }
                
            }
            
        } else {
            
            if( belongs_to(hash1, false) != -1 ){
                
                if( belongs_to(hash2, true) != -1 ){
                    
                    // Retorna valor
                    if( matrix[belongs_to(hash2, true)][belongs_to(hash1, false)] != -1 ){
                        return values.get(matrix[belongs_to(hash2, true)][belongs_to(hash1, false)]);
                    } else {
                        i = belongs_to(hash2, true);
                        j = belongs_to(hash1, false);
                    }
                    
                } else {
                    
                    if( belongs_to(hash2, false) == -1 ){
                        
                        // ADD H2 LINHA
                        line.add(hash2);
                        i = line.size() - 1;
                        j = belongs_to(hash1, false);
                        
                    }
                    
                }
                
            } else {
                
                if( belongs_to(hash2, true) != -1 ){
                    
                    // ADD H1 COLUNA
                    column.add(hash1);
                    i = belongs_to(hash2, true);
                    j = column.size() - 1;
                    
                } else {
                    
                    if( belongs_to(hash2, false) != -1 ){
                        
                        // ADD H1 LINHA
                        line.add(hash1);
                        i = line.size() - 1;
                        j = belongs_to(hash2, false);
                        
                    } else {
                        
                        // ADD H1 LINHA
                        line.add(hash1);
                        i = line.size() - 1;
                        j = belongs_to(hash2, false);
                        // ADD H2 COLUNA
                        column.add(hash2);
                        i = belongs_to(hash1, true);
                        j = column.size() - 1;
                        
                    }
                    
                }
                
            }
            
        }
        
        // Retorna valor
        values.add(Diff.lcs_dp(seq1.clone(), seq2.clone()));
        matrix[i][j] = values.size() - 1;
        return values.get(matrix[i][j]);
        
    }
    
    private static int belongs_to( byte[] hash, boolean is_line ){
        
        if( is_line ){
            for( int i = 0; i < line.size(); i++ ){
                if( !Diff.has_diff(hash, line.get(i)) ){
                    return i;
                }
            }
        } else {
            for( int i = 0; i < column.size(); i++ ){
                if( !Diff.has_diff(hash, column.get(i)) ){
                    return i;
                }
            }
        }
        
        return -1;
        
    }
    
    protected static byte[] get_memorized_value( byte[] seq1, byte[] seq2 ){
        byte[] hash1 = generate_hash(seq1);
        byte[] hash2 = generate_hash(seq2);
        
        int a = belongs_to(hash1, true);
        int b = belongs_to(hash1, false);
        int c = belongs_to(hash2, true);
        int d = belongs_to(hash2, false);
        
        if( a != -1 && d != -1 ){
            if( matrix[a][d] != -1 ){
                System.out.println("RETORNEI: " + new String(values.get(matrix[a][d])));
                return values.get(matrix[a][d]);
            } else {
                return new byte[0];
            }
        } else {
            if( b != -1 && c != -1 ){
                if( matrix[b][c] != -1 ){
                    System.out.println("RETORNEI: " + new String(values.get(matrix[a][d])));
                    return values.get(matrix[b][c]);
                } else {
                    return new byte[0];
                }
            } else {
                //consider_values(hash1, hash2, Diff.lcs_dp(seq1.clone(), seq2.clone()));
                return get_memorized_value(seq1, seq2);
            }
        }
    }
    
    public static void init_matrix(){
        for( int i = 0; i < TAM_I; i++ ){
            for( int j = 0; j < TAM_J; j++ ){
                matrix[i][j] = -1;
            }
        }
    }
    
    private static byte[] generate_hash(byte[] seq) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(seq);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
}
