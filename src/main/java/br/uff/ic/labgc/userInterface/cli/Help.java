package br.uff.ic.labgc.userInterface.cli;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.*;

/**
 * Hello world!
 *
 */
public class Help 
{
    public String m_strHelp ="The most commonly used git commands are: \n \t mkdir Creates a directory \n";
   
    public void run(Options _options) //throws ParseException
    {
        HelpFormatter f = new HelpFormatter();
        f.printHelp("labgc-2012.2", _options);
    }
}
