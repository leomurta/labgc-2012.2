/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.properties.IPropertiesConstants;
import br.uff.ic.labgc.server.IServer;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristiano
 */
public class CommunicationFactory {

    private static CommunicationFactory instance;
    private IServer commClient;

    private CommunicationFactory() {
    }

    /**
     * Método que retorna um singleton da fábrica de objetos de comunicação
     *
     * @return
     */
    public synchronized static CommunicationFactory getFactory() {
        if (instance == null) {
            instance = new CommunicationFactory();
        }

        return instance;
    }

    /**
     * Retorna uma instância de servidor, conforme especificado na propriedade
     * serverclass do arquivo labgc.properties
     *
     * @return
     */
    public synchronized IServer getServer() throws RuntimeException {

        if (commClient == null) {
            ResourceBundle bundle = ResourceBundle.getBundle(IPropertiesConstants.PROPERTIES_FILE_NAME, 
                    Locale.getDefault(), ClassLoader.getSystemClassLoader());

            String serverClass = bundle.getString(IPropertiesConstants.SERVER_CLASS);

            try {
                commClient = (IServer) Class.forName(serverClass).newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(CommunicationFactory.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Não foi possível instanciar um servidor.", ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(CommunicationFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CommunicationFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return commClient;

    }
}
