package br.uff.ic.labgc.userInterface.cli;
import br.uff.ic.labgc.client.Client;
import br.uff.ic.labgc.client.IClient;
import br.uff.ic.labgc.core.EVCSConstants;
import br.uff.ic.labgc.core.IObserver;
import br.uff.ic.labgc.exception.ClientException;
import br.uff.ic.labgc.exception.ClientLoginRequiredException;
import br.uff.ic.labgc.exception.ClientWorkspaceUnavailableException;
import br.uff.ic.labgc.userInterface.common.Messages;
import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.*;
import org.apache.derby.tools.sysinfo;

/**
 * Hello world!
 *
 */
public class Cli implements IObserver
{
    private Options m_options;
    private IClient m_IClient;

    public void Cli()
    {  
        
    }
    
    private void addOptions()
    {
         // create Options object
        m_options = new Options();
        
        //Add Help 
        m_options.addOption(" ","help", false, "Display help"); 
        
        //m_options.addOption(" ","mkdir", true, "Creates a directory");
        m_options.addOption( OptionBuilder.withLongOpt( "mkdir" )
                                        .withDescription( "Creates a directory" )
                                        .hasArg()
                                        .withArgName("PATH")
                                        .create("mkdir") );       
        
        m_options.addOption( OptionBuilder.withLongOpt( "checkout" )
                                        .withDescription( "Checkout a branch or paths to the working tree" )
                                        .hasArgs(3)
                                        .withArgName("HOST REPOSITORY")
                                        .create("checkout") );
        
        m_options.addOption( OptionBuilder.withLongOpt( "revert" )
                                        .withDescription( "Revert modfications on a especific file or files in a directory" )
                                        .hasArg()
                                        .withArgName("FILENAME OR PATH")
                                        .create("revert") );
        
        m_options.addOption( OptionBuilder.withLongOpt( "login" )
                                        .withDescription( "Make login" )
                                        .hasArgs(2)
                                        .withArgName("USER PASSWORD")
                                        .create("login") );
    }
    
    private void dysplayHelp() 
    {
        //throw new UnsupportedOperationException("Not yet implemented");
         Help help = new Help();
         //System.out.println(help.m_strHelp);
         help.run(m_options);
    }
    
    public void  run(String[] args) throws ParseException, ClientWorkspaceUnavailableException
    {
        
        CommandLineParser parser = new PosixParser();
        addOptions(); 
        Messages msg = new Messages();   
        CommandLine cmd = null;
        try
        {
          cmd = parser.parse( m_options, args);
        }
        catch (UnrecognizedOptionException e)
        {
            System.out.println(msg.GetUnrecognizedOptMsg()); 
            
            return;
        }
        catch (MissingArgumentException e)
        {
            System.out.println(msg.GetMissArgMsg()); 
            return;
        }
        
        if(cmd.hasOption("help")) 
        {
           dysplayHelp();
           return;
        }
        
        if(cmd.hasOption("mkdir")) 
        {
            String path = cmd.getOptionValue("mkdir");
            
            runMakeDir(path);
            return;
        }
        
        if(cmd.hasOption("checkout")) 
        {
           String [] checkArgs = cmd.getOptionValues("checkout");
           runCheckOut(checkArgs);
           
           return;
        }
        
        if(cmd.hasOption("revert")) 
        {
           String fileName = cmd.getOptionValue("revert");
           //String path = System.getProperty("user.dir");
           
           System.out.println("Coming soon revert from file or directory "+fileName);
           
            return;
        }
        
        if(cmd.hasOption("login")) 
        {
           String [] loginArg = cmd.getOptionValues("login");
           runLogin(loginArg);
           
            return;
        }
        
        System.out.println("Unrecognized Option "); 
       
    }

    public void sendNotify(String strNotify) 
    {
         System.out.println(strNotify); 
    }
    
    
    private void runLogin(String [] loginArg)
    {
        
        String user,passWord,location;
           
           Messages msg =new Messages();
           
           location ="";
           
           if(loginArg.length>1)
           {
             user = loginArg[0];
             passWord = loginArg[1];
             //location = loginArg[2];
             
             String strCurrentTerminalPath = "";
             
             m_IClient = new Client(strCurrentTerminalPath);
             try 
             {
                 m_IClient.login(user, passWord);
             } 
             catch (ClientException ex) 
             {
                  Logger.getLogger(Cli.class.getName()).log(Level.SEVERE, null, ex);
                  System.out.println(msg.GetExceptionMessage(ex));
                  return;
             }
             
             System.out.println("Logged successfully.");
            
           }
           else
           {
             System.out.println("The amount of arguments is insufficient ("+loginArg.length+").");
           }
           
           //System.out.println("Coming soon login to the repository "+location);   
    }
    
    private void runCheckOut(String [] checkArgs)
    {
        
           if(checkArgs.length > 1)
           {
               Messages msg = new Messages();
               for (int i = 0; i < checkArgs.length; i++) {
                   System.out.println(i + "==>" + checkArgs[i]);
                   
               }
               String strUrl = checkArgs[0];
               // Break URL in repo and host
               //file:/// or http://
               //String [] strArrayUrl = strUrl.split("/");
               
               String strHost = checkArgs[0];
               String strRepository = checkArgs[1];

               //TODO: Check Local Path
               String strPath = checkArgs[2]; 
               //String path = System.getProperty("user.dir");
           
               m_IClient = new Client(strHost, strRepository, strPath) ;
               
               String strRevision = "";
               
               if(checkArgs.length >3)
                   strRevision = checkArgs[3];
               else
                   strRevision= EVCSConstants.REVISION_HEAD;
               
               
               
               if(!checkLogin())
                   return;
                
               
               try
               {
                    //host, repos, path , rev
                    //m_IClient.checkout(strUrl, strPath,-1);
                    m_IClient.checkout(strRevision);
               }
               catch(ClientWorkspaceUnavailableException workExc)
               {
                   //Classe de Mensagens -- GetMessage (Exception)
                   System.out.println(msg.GetExceptionMessage(workExc));
                   return;
               }
               catch(ClientLoginRequiredException loginExc)
               {
                   
                   //Classe de Mensagens -- GetMessage (Exception)
                   System.out.println(msg.GetExceptionMessage(loginExc));
                   return ;
               }
               catch(ClientException ex)
               {
                   System.out.println(msg.GetExceptionMessage(ex));
                   return;
               }
               
           }
           else
           {
             System.out.println("The amount of arguments is insufficient ("+checkArgs.length+").");
           }
    }
    
    private boolean checkLogin()
    {
        
         //Verificando Login
        try 
        {
            if(!m_IClient.isLogged())
            {
                Scanner leitor = new Scanner(System.in); 

                boolean bLogin =false;
                int cont =0;

                while(cont<4)
                {
                    cont++;
                    System.out.println("Type Login:");
                    String strLogin = leitor.nextLine();  

                    System.out.println("Type Password:");
                    String strPass = leitor.nextLine();

                    m_IClient.login(strLogin, strPass);

                    if(m_IClient.isLogged())
                    {
                        bLogin= true;
                        break;
                    }
                    else
                        System.out.println("Login or Password incorrect."); 
                }

                if(!bLogin)
                {
                    System.out.println("Transaction aborted, verify your login and password.");
                    return false;
                }

            }
        } 
        catch (ClientException ex) 
        {
            Logger.getLogger(Cli.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    private void runMakeDir(String path)
    {
         String strCurrentTerminalPath = "";    
         m_IClient = new Client(strCurrentTerminalPath);
        
        if(m_IClient.mkdir(path))
            System.out.println("Directory successfully created.");  
        else
            System.out.println("The directory can not be created (Check permissions and the remaining space on the disc).");
    }
     
}
