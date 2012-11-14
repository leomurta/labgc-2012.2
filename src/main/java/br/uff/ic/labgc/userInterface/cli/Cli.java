package br.uff.ic.labgc.userInterface.cli;

import br.uff.ic.labgc.client.Client;
import br.uff.ic.labgc.client.IClient;
import br.uff.ic.labgc.core.EVCSConstants;
import br.uff.ic.labgc.core.IObserver;
import br.uff.ic.labgc.core.VersionedDir;
import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.ClientException;
import br.uff.ic.labgc.exception.ClientLoginRequiredException;
import br.uff.ic.labgc.exception.ClientWorkspaceUnavailableException;
import br.uff.ic.labgc.userInterface.common.Messages;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.*;
import org.apache.commons.lang.ObjectUtils;
import org.apache.derby.tools.sysinfo;

/**
 * Hello world!
 *
 */
public class Cli {

    private Options m_options;
    private IClient m_IClient;
    private String invocationPath;

    public Cli(String invocationPath) {
        this.invocationPath = invocationPath;
    }

    public void Cli() {
    }

    private void addOptions() {
        // create Options object
        m_options = new Options();

        //Add Help 
        m_options.addOption("help", false, "Display help");


        m_options.addOption(OptionBuilder.withDescription("Creates a directory")
                .hasArg()
                .withArgName("PATH")
                .create("mkdir"));

        m_options.addOption(OptionBuilder.withDescription("Checkout a branch or paths to the working tree")
                .hasArgs(2)
                .withArgName("HOST PATH")
                .create("checkout"));

        m_options.addOption(OptionBuilder.withDescription("Revert modfications on a especific file or files in a directory")
                .hasOptionalArg()
                .withArgName("FILENAME OR PATH (OPTIONAL)")
                .create("revert"));

        m_options.addOption(OptionBuilder.withDescription("Make login")
                .hasArgs(2)
                .withArgName("USER PASSWORD")
                .create("login"));

        m_options.addOption(OptionBuilder.withDescription("Get the status of a item or path")
                .hasOptionalArg()
                .withArgName("ITEM (OPTIONAL)")
                .create("status"));

        m_options.addOption(OptionBuilder.withLongOpt("m")
                .withDescription("Commit the changes made")
                .hasOptionalArg()
                .withArgName("COMMIT_MESSAGE")
                .create("commit"));


        m_options.addOption(OptionBuilder.withDescription("Log of the latest revisions")
                .hasOptionalArg()
                .withArgName("ITEM(OPTIONAL)")
                .create("log"));
    }

    private void dysplayHelp() {
        //throw new UnsupportedOperationException("Not yet implemented");
        Help help = new Help();
        //System.out.println(help.m_strHelp);
        help.run(m_options);
    }

    public boolean compare(VersionedItem object1, VersionedItem object2) {
        return object1.getStatus() <= object2.getStatus();
    }

    private String getStatus(int status) {
        switch (status) {
            case EVCSConstants.UNMODIFIED:
                return "Unmodified";
            case EVCSConstants.MODIFIED:
                return "Modified";
            case EVCSConstants.ADDED:
                return "Added";
            case EVCSConstants.DELETED:
                return "Deleted";
        }
        return "";
    }

    private void registerObserver() {
        IObserver clientObs = new IObserver() {
            public void sendNotify(String msg) {
                System.out.println(msg + "\n");
            }
        };

        m_IClient.registerInterest(clientObs);
    }

    public void run(String[] args) throws ParseException, ClientWorkspaceUnavailableException {

        //CommandLineParser parser = new PosixParser();
        
        /*args = new String[2];
        args[0]="-revert";
        args[1]="asas";*/
        
        CommandLineParser parser = new GnuParser();
        addOptions();
        Messages msg = new Messages();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(m_options, args);
        } catch (UnrecognizedOptionException e) {
            System.out.println(msg.GetUnrecognizedOptMsg());

            return;
        } catch (MissingArgumentException e) {
            System.out.println(msg.GetMissArgMsg());
            return;
        }

        if (cmd.hasOption("help")) {
            dysplayHelp();
            return;
        }

        if (cmd.hasOption("mkdir")) {
            String path = cmd.getOptionValue("mkdir");

            //runMakeDir(path);
            return;
        }

        if (cmd.hasOption("checkout")) {
            String[] checkArgs = cmd.getOptionValues("checkout");
            runCheckOut(checkArgs);

            return;
        }
        
        if(cmd.hasOption("revert")) 
        {
           //String fileName = cmd.getOptionValue("revert");
           String [] revertArg= null;
           revertArg = cmd.getOptionValues("revert");
           runRevert(revertArg);
          
            return;
        }

        if (cmd.hasOption("login")) {
            String[] loginArg = cmd.getOptionValues("login");
            runLogin(loginArg);

            return;
        }

        if (cmd.hasOption("status")) 
        {
            String[] statusArg = null;
            statusArg = cmd.getOptionValues("status");
            runStatus(statusArg);

            return;
        }

        if (cmd.hasOption("commit")) {
            String[] commitArg = null;
            commitArg = cmd.getOptionValues("commit");
            runCommit(commitArg);

            return;
        }


        if (cmd.hasOption("log")) {
            String[] logArg= null; 
            logArg = cmd.getOptionValues("log");
            runLog(logArg);

            return;
        }

        /*Option [] str =  cmd.getOptions();
         for (Option v : str)
         {
         System.out.println("saddsda");
         System.out.println(v.getValue());
         }*/


        System.out.println("Unrecognized Option ");

    }

    public void sendNotify(String strNotify) {
        System.out.println(strNotify);
    }

    private void runLogin(String[] loginArg) {

        String user, passWord, location;

        Messages msg = new Messages();

        location = "";

        if (loginArg.length > 1) {
            user = loginArg[0];
            passWord = loginArg[1];
            //location = loginArg[2];

            String strCurrentTerminalPath = "";

            m_IClient = new Client(strCurrentTerminalPath);
            registerObserver();
            try {
                m_IClient.login(user, passWord);
            } catch (ApplicationException ex) {
                Logger.getLogger(Cli.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(msg.GetExceptionMessage(ex));
                return;
            }

            System.out.println("Logged successfully.");

        } else {
            System.out.println("The amount of arguments is insufficient (" + loginArg.length + ").");
        }

        //System.out.println("Coming soon login to the repository "+location);   
    }

    private void runCheckOut(String[] checkArgs) {

        if (checkArgs.length > 1) {
            Messages msg = new Messages();

            String strUrl = checkArgs[0];
            // Break URL in repo and host
            //file:/// or http://
            //String [] strArrayUrl = strUrl.split("/");

            String strHost = checkArgs[0];
            String strRepository = checkArgs[1];

            //TODO: Check Local Path
            String strPath = this.invocationPath;
            //String path = System.getProperty("user.dir");

            m_IClient = new Client(strHost, strRepository, strPath);
            registerObserver();

            String strRevision = "";

            if (checkArgs.length > 2) {
                strRevision = checkArgs[2];
            } else {
                strRevision = EVCSConstants.REVISION_HEAD;
            }



            if (!checkLogin()) {
                return;
            }


            try {
                //host, repos, path , rev
                //m_IClient.checkout(strUrl, strPath,-1);
                m_IClient.checkout(strRevision);
            } catch (ClientWorkspaceUnavailableException workExc) {
                //Classe de Mensagens -- GetMessage (Exception)
                Logger.getLogger(Cli.class.getName()).log(Level.SEVERE, null, workExc);
                System.out.println(msg.GetExceptionMessage(workExc));
                return;
            } catch (ClientLoginRequiredException loginExc) {

                //Classe de Mensagens -- GetMessage (Exception)
                Logger.getLogger(Cli.class.getName()).log(Level.SEVERE, null, loginExc);
                System.out.println(msg.GetExceptionMessage(loginExc));
                return;
            } catch (ApplicationException ex) {
                Logger.getLogger(Cli.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(msg.GetExceptionMessage(ex));
                return;
            }

        } else {
            System.out.println("The amount of arguments is insufficient (" + checkArgs.length + ").");
        }
    }

    private boolean checkLogin() {

        //Verificando Login
        try {
            if (!m_IClient.isLogged()) {
                Scanner leitor = new Scanner(System.in);

                boolean bLogin = false;
                int cont = 0;

                while (cont < 4) {
                    cont++;
                    System.out.println("Type Login:");
                    String strLogin = leitor.nextLine();

                    System.out.println("Type Password:");
                    String strPass = leitor.nextLine();

                    m_IClient.login(strLogin, strPass);

                    if (m_IClient.isLogged()) {
                        bLogin = true;
                        break;
                    } else {
                        System.out.println("Login or Password incorrect.");
                    }
                }

                if (!bLogin) {
                    System.out.println("Transaction aborted, verify your login and password.");
                    return false;
                }

            }
        } catch (ApplicationException ex) {
            Logger.getLogger(Cli.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    private void runCommit(String[] commitArgs) 
    {
        
        String strItemPath = invocationPath;
        String strMessage="";
        
        if(commitArgs!=null)
        {
            if (commitArgs.length > 0) 
            {
                strItemPath += commitArgs[0];
            }
            
            if (commitArgs.length > 1) 
            {
                strMessage += commitArgs[1];
            }
            else
            {
                System.out.println("Please, enter the commit message\n");
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));   
                try 
                {
                    strMessage = in.readLine();
                } 
                catch (IOException ex) 
                {
                    System.out.println(ex.getMessage());
                }
            }
        
            m_IClient = new Client(strItemPath);
            registerObserver();
            try 
            {
                m_IClient.commit(strMessage);
            } 
            catch (ApplicationException ex)
            {
                System.out.println(ex.getMessage());
                return;
            }
        
        }
        else 
        {
            System.out.println("The amount of arguments is insufficient (0).");
        }

    }

    private void runStatus(String[] statusArg) {

        String strItemPath = invocationPath;
        if(statusArg!=null)
        {
            if (statusArg.length > 0) 
            {
                strItemPath += statusArg[0];
            }
        }
        m_IClient = new Client(strItemPath);

        VersionedItem status = new VersionedDir();
        try 
        {
            status = m_IClient.status();
            //Collections.sort(listItem, compare);
        } 
        catch (ApplicationException ex) 
        {
            //Logger.getLogger(Cli.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return;
        }

        VersionedDir dir = (VersionedDir) status;
        List<VersionedItem> listItem = dir.getContainedItens();

        for (VersionedItem v : listItem) 
        {
            System.out.println(v.getName() + "    " + getStatus(v.getStatus()));
        }

    }

    private void runLog(String[] logArg) 
    {

        String strItemPath = invocationPath;
        
        if(logArg != null)
        {
            if (logArg.length > 0)
                strItemPath += logArg[0];
        }    
        m_IClient = new Client(strItemPath);

        List<VersionedItem> listItem = new ArrayList<VersionedItem>(); 
        try 
        {
           listItem = m_IClient.log();
           //Collections.sort(listItem, compare);
        } 
        catch (ApplicationException ex) 
        {
            //Logger.getLogger(Cli.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }

        for (VersionedItem v : listItem)
        {

        } 
         
     }
     
    
      private void runRevert(String[] revertArg)
      {
             
          String strItemPath=invocationPath;
          
          if(revertArg!=null)
          {    
            if(revertArg.length>0)
            {
                strItemPath += revertArg[0];
                //System.out.println(strItemPath);
            }
          } 
          m_IClient = new Client(strItemPath);
          try 
          {
            if(!(m_IClient.revert()))
            {
               System.out.println("The revert operation could not be completed");
               return;
            }
          } 
          catch (ApplicationException ex) 
          {
            System.out.println(ex.getMessage());
            return;
          }
            
          System.out.println("Revert operation completed successfully");
                     
      }
    
    
}
