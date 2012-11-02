/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.storage;

import br.uff.ic.labgc.storage.util.ObjectNotFoundException;
import org.junit.Test;

/**
 *
 * @author jokerfvd
 */
public interface IDAOTest {
    
    @Test
    public void testAdd();
    
    @Test
    public void testRemove();
    
    @Test (expected=ObjectNotFoundException.class)
    public void testUpdate();
    
}
