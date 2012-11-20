package br.uff.ic.labgc.algorithms;

import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Evangelista
 */
public class Set {
    
    public static List<VersionedItem> intersection( List<VersionedItem> set1, List<VersionedItem> set2 ) throws ApplicationException{
        ArrayList<VersionedItem> retorno = new ArrayList<>();
        if( set1.size() > 0 || set2.size() > 0 ){
            for( int i = 0; i < set1.size(); i++ ){
                for( int j = 0; j < set2.size(); j++ ){
                    if( set1.get(i).isDir() && set2.get(j).isDir() ){
                        if( !Diff.has_diff( ((VersionedDir)set1).getContainedItens(), ((VersionedDir)set2).getContainedItens() ) ){
                            retorno.add(set1.get(i));
                        }
                    } else {
                        if( !set1.get(i).isDir() && !set2.get(j).isDir() ){
                            if( !Diff.has_diff( ((VersionedFile)set1).getContent(), ((VersionedFile)set2).getContent() ) ){
                                retorno.add(set1.get(i));
                            }
                        }
                    }
                }
            }
        }
        return retorno.subList(0, retorno.size());
    }
    
    public static List<VersionedItem> difference( List<VersionedItem> set1, List<VersionedItem> set2 ) throws ApplicationException{
        ArrayList<VersionedItem> retorno = new ArrayList<>();
        if( set1.size() > 0 || set2.size() > 0 ){
            for( int i = 0; i < set1.size(); i++ ){
                if( !belongs_to(set1.get(i), set2)){
                    retorno.add(set1.get(i));
                }
            }
        }
        return retorno.subList(0, retorno.size());
    }
    
    public static boolean belongs_to( VersionedItem item, List<VersionedItem> set1 ) throws ApplicationException{
        for( int i = 0; i < set1.size(); i++ ){
            if( item.isDir() && set1.get(i).isDir() ){
                return Diff.has_diff( ((VersionedDir)item).getContainedItens(), ((VersionedDir)set1.get(i)).getContainedItens() );
            } else {
                if( !item.isDir() && !set1.get(i).isDir() ){
                    return Diff.has_diff( ((VersionedFile)item).getContent(), ((VersionedFile)set1.get(i)).getContent() );
                }
            }
        }
        return false;
    }
    
}
