/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.algorithms;

import br.uff.ic.labgc.core.VersionedFile;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo
 */
public class Merge 
{
    
   private static boolean bConflict;
   
   public static VersionedFile TwoWayMerge(VersionedFile mineVersion, VersionedFile theirsVersion) throws ApplicationException
   {
       VersionedFile mergedVersion = new VersionedFile();
       bConflict = false;
       
       byte bMine[] = mineVersion.getContent();
       byte bTheirs[] = theirsVersion.getContent();
       
       byte[] lcs = Diff.lcs(bMine, bTheirs);
       
       String strMine =  new String(bMine);
       String strTheirs =  new String(bTheirs);
       
       String[] mineArray  = strMine.split(System.getProperty("line.separator"));
       String[] theirsArray  = strTheirs.split(System.getProperty("line.separator"));
       
       String strMerge="";
       int lcsIndex =0;
       int mineIndex =0;
       int mineLastIndex =0;
       int theirsIndex =0;
       int theirsLastIndex =0;
       
        String strLcs = new String(lcs);
        String [] lcsArray = strLcs.split(System.getProperty("line.separator"));  
       
        while(mineIndex<mineArray.length)
        {
            
              if(lcsIndex>=lcsArray.length)
                  break;
              
              if(mineIndex<mineArray.length)
              {
                if(mineArray[mineIndex].equals(lcsArray[lcsIndex]) )
                {
                    
                    String [] mineJoin;
                    if(mineLastIndex==mineIndex)
                    {
                        mineJoin = new String[1];
                        mineJoin[0] = lcsArray[lcsIndex]+System.getProperty("line.separator");
                    }    
                    else
                      mineJoin = ArrayCopy (mineArray,mineLastIndex+1,mineIndex+1);
                    
                    for(theirsIndex=theirsLastIndex;theirsIndex<theirsArray.length;theirsIndex++)
                    {
                        
                        if(theirsArray[theirsIndex].equals(lcsArray[lcsIndex]))
                            break;
                    }
                    
                    String [] theirsJoin;
                    if(theirsLastIndex == theirsIndex)
                    {
                        theirsJoin = new String[1];
                        theirsJoin[0]=lcsArray[lcsIndex]+System.getProperty("line.separator");
                    }
                    else
                      theirsJoin= ArrayCopy (theirsArray,theirsLastIndex+1,theirsIndex+1);
                    
                    strMerge += JoinText(mineJoin,theirsJoin);
                  
                    lcsIndex++;
                    mineLastIndex = mineIndex;
                    theirsLastIndex = theirsIndex;
                }
              }
              
              
              mineIndex++;
              theirsIndex++;           
        }
        
          
        String [] mineFinalJoin = ArrayCopy (mineArray,mineLastIndex+1,mineArray.length);
        String [] theirsFinalJoin = ArrayCopy (theirsArray,theirsLastIndex+1,theirsArray.length);
        
        strMerge += JoinText(mineFinalJoin,theirsFinalJoin); 
        
        byte[] bMergedContent = strMerge.getBytes();
        mergedVersion.setContent(bMergedContent);
        mergedVersion.setConflict(bConflict);
        
       
      return mergedVersion;    
   }
   
   
   public static VersionedFile ThreeWayMerge(VersionedFile mineVersion, VersionedFile theirsVersion, VersionedFile ancestralVersion ) throws ApplicationException
   {
       VersionedFile mergedVersion = new VersionedFile();
       bConflict = false;
       
       byte bMine[] = mineVersion.getContent();
       byte bTheirs[] = theirsVersion.getContent();
       byte bAncestral[] = ancestralVersion.getContent();
       
       
       byte[] lcsTmp = Diff.lcs(bMine, bTheirs);
       byte[] lcs = Diff.lcs(lcsTmp, bAncestral);
       
       String strMine =  new String(bMine);
       String strTheirs =  new String(bTheirs);
       String strAncestral =  new String(bAncestral);
       
       String[] mineArray  = strMine.split(System.getProperty("line.separator"));
       String[] theirsArray  = strTheirs.split(System.getProperty("line.separator"));
       String[] ancestralArray = strAncestral.split(System.getProperty("line.separator")); 
        
        String strMerge="";
        int lcsIndex =0;
        int mineIndex =0;
        int mineLastIndex =0;
        int theirsIndex =0;
        int theirsLastIndex =0;
        int ancestralIndex =0;
        int ancestralLastIndex=0;
        
        String strLcs = new String(lcs);
        String [] lcsArray = strLcs.split(System.getProperty("line.separator"));        
        
        while(mineIndex<mineArray.length)
        {
            
              if(lcsIndex>=lcsArray.length)
                  break;
              
              if(mineIndex<mineArray.length)
              {
                if(mineArray[mineIndex].equals(lcsArray[lcsIndex]) )
                {
                    
                    String [] mineJoin;
                    if(mineLastIndex==mineIndex)
                    {
                        mineJoin = new String[1];
                        mineJoin[0] = lcsArray[lcsIndex]+System.getProperty("line.separator");
                    }    
                    else
                      mineJoin = ArrayCopy (mineArray,mineLastIndex+1,mineIndex+1);
                    
                    for(theirsIndex=theirsLastIndex;theirsIndex<theirsArray.length;theirsIndex++)
                    {
                        
                        if(theirsArray[theirsIndex].equals(lcsArray[lcsIndex]))
                            break;
                    }
                    
                    String [] theirsJoin;
                    if(theirsLastIndex == theirsIndex)
                    {
                        theirsJoin = new String[1];
                        theirsJoin[0]=lcsArray[lcsIndex]+System.getProperty("line.separator");
                    }
                    else
                      theirsJoin= ArrayCopy (theirsArray,theirsLastIndex+1,theirsIndex+1);
                    
                    for(ancestralIndex=ancestralLastIndex; ancestralIndex<ancestralArray.length;ancestralIndex++)
                    {
                        
                        if(ancestralArray[ancestralIndex].equals(lcsArray[lcsIndex]))
                            break;
                    }
                    String [] ancestralJoin; 
                    if(ancestralLastIndex==ancestralIndex)
                    {
                       ancestralJoin = new String[1];
                       ancestralJoin[0] = lcsArray[lcsIndex]+System.getProperty("line.separator");
                    }
                    else
                      ancestralJoin = ArrayCopy (ancestralArray,ancestralLastIndex+1,ancestralIndex+1);
                    
                    strMerge += JoinText(mineJoin,theirsJoin,ancestralJoin);
                  
                    lcsIndex++;
                    mineLastIndex = mineIndex;
                    theirsLastIndex = theirsIndex;
                    ancestralLastIndex = ancestralIndex;
                }
              }
              
              
              mineIndex++;
              theirsIndex++;
              ancestralIndex++;
             
        }
        
          
        String [] mineFinalJoin = ArrayCopy (mineArray,mineLastIndex+1,mineArray.length);
        String [] theirsFinalJoin = ArrayCopy (theirsArray,theirsLastIndex+1,theirsArray.length);
        String [] ancestralFinalJoin = ArrayCopy (ancestralArray,ancestralLastIndex+1,ancestralArray.length);
        
        
        strMerge += JoinText(mineFinalJoin,theirsFinalJoin,ancestralFinalJoin); 
        
        byte[] bMergedContent = strMerge.getBytes();
        mergedVersion.setContent(bMergedContent);
        mergedVersion.setConflict(bConflict);
        
       
      return mergedVersion;
   }
   
    
   private static String[] ArrayCopy(String [] ArraySource,int nBeginIndex, int nEndIndex)
   {
      int nLength = nEndIndex-nBeginIndex;       
      if(nLength<=0)
          nLength=1;
       
      String [] RetArray  =  new String[nLength];
      for(int i =0;i<RetArray.length;i++)
      {
          RetArray[i]="";
      }
      
      
      int nRetIndex =0;
      for(int i =nBeginIndex;i<nEndIndex;i++)
      {
          RetArray[nRetIndex] = ArraySource[i]+System.getProperty("line.separator");
          nRetIndex++;
      }
      
      return RetArray;
   }
   
   
   private static String JoinText(String [] MineArray,String [] TheirsArray, String [] AncestralArray)
   {
      String strJoin ="";
      int mineIndex =0;
      int theirsIndex =0;
      int ancestralIndex = 0;
      
      while(mineIndex<MineArray.length || mineIndex<TheirsArray.length)
      {
            String strMine ="";
            String strTheirs ="";

            if(mineIndex<MineArray.length)
            strMine = MineArray[mineIndex];

            if(theirsIndex<TheirsArray.length)
            strTheirs = TheirsArray[theirsIndex];

            if(strMine.equals(strTheirs))
            {
                if(!strMine.isEmpty())
                strJoin+= strMine;
            }
            else
            {
                String strAncestral ="";
                if(ancestralIndex<AncestralArray.length)
                    strAncestral  = AncestralArray[ancestralIndex];

                if( !(strMine.equals(strAncestral)) )
                {
                    //Existe em Theirs e no Ancestral
                    if(strTheirs.equals(strAncestral))
                    {
                        //ADD em Mine
                        strJoin+= strMine;
                    }
                    else//Conflito : Não Existe no Ancestral, e Mine e Theirs são diferentes
                    {
                        strJoin += ">>>>>>>>>>>>>>>> Mine >>>>>>>>>>>>>>>>"+System.getProperty("line.separator");
                        strJoin+="\t"+strMine;

                        strJoin += "===================================="+System.getProperty("line.separator");

                        strJoin+="\t"+strTheirs;
                        strJoin += "<<<<<<<<<<<<<<<< Theirs <<<<<<<<<<<<<<<"+System.getProperty("line.separator");

                        //Sinalizacao de Conflito
                        bConflict =true;

                    }
                }
                else //Existe em Mine
                {
                    //Não Existe em Theirs 
                    if(! (strTheirs.equals(strAncestral)) )
                    {
                        //Delete de linhs do Ancestral e/ou adicao  de novas linhas
                        strJoin+= strTheirs;
                    }
                }
            }

            mineIndex++;
            theirsIndex++;
            ancestralIndex++;
      }
      
      return strJoin;
  }
   
   
    private static String JoinText(String [] MineArray,String [] TheirsArray)
   {
      String strJoin ="";
      int mineIndex =0;
      int theirsIndex =0;
      
      while(mineIndex<MineArray.length || mineIndex<TheirsArray.length)
      {
            String strMine ="";
            String strTheirs ="";

            if(mineIndex<MineArray.length)
            strMine = MineArray[mineIndex];

            if(theirsIndex<TheirsArray.length)
            strTheirs = TheirsArray[theirsIndex];

            if(strMine.equals(strTheirs))
            {
                if(!strMine.isEmpty())
                strJoin+= strMine;
            }
            else
            {
                strJoin += ">>>>>>>>>>>>>>>> Mine >>>>>>>>>>>>>>>>"+System.getProperty("line.separator");
                strJoin+="\t"+strMine;

                strJoin += "===================================="+System.getProperty("line.separator");

                strJoin+="\t"+strTheirs;
                strJoin += "<<<<<<<<<<<<<<<< Theirs <<<<<<<<<<<<<<<"+System.getProperty("line.separator");

                //Sinalizacao de Conflito
                bConflict =true;
            }

            mineIndex++;
            theirsIndex++;
      }
      
      return strJoin;
  }
    
}
