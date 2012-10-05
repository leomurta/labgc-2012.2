/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.userInterface.common;

import br.uff.ic.labgc.exception.ClientException;
import br.uff.ic.labgc.exception.ClientLoginRequiredException;
import br.uff.ic.labgc.exception.ClientWorkspaceUnavailableException;

/**
 *
 * @author Leonardo
 */
public class Messages 
{
    
    private String strUnrecognizedOpt = "Unrecognized Option";
    private String strMissArg = "Missing argument";
    
    
    public String GetUnrecognizedOptMsg()
    {
        return strUnrecognizedOpt;
    }
    
    
    public String GetMissArgMsg()
    {
        return strMissArg;
    }
    
    public String GetExceptionMessage(Exception ex)
    {
        if(ex.getClass() == ClientException.class)
            return "";
        
        if(ex.getClass() == ClientWorkspaceUnavailableException.class)
            return "EXCEPTION : -> The Workspace is unavaliable.";
        
        if(ex.getClass() == ClientLoginRequiredException.class)
            return "EXCEPTION : -> Client login is required.";
    
        return "EXCEPTION : -> Unknow Exception.";
    }
}
