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
                System.out.println(set1.get(i).getName());
                if( belongs_to(set1.get(i), set2) != -1 ){
                    retorno.add(set1.get(i));
                }
            }
        }
        return retorno.subList(0, retorno.size());
    }
    
    public static int belongs_to( VersionedItem item, List<VersionedItem> set1 ) throws ApplicationException{
        int ret = -1;
        for( int i = 0; i < set1.size() && ret == -1; i++ ){
            if( item.isDir() && set1.get(i).isDir() ){
                if((item).getName().equals((set1.get(i)).getName())){
                    ret = i;
                }
            } else {
                if( !item.isDir() && !set1.get(i).isDir() ){
                    if((item).getName().equals((set1.get(i)).getName())){
                        ret = i;
                    }
                }
            }
        }
        return ret;
    }
    
}
