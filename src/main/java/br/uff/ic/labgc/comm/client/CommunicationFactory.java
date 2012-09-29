/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.CommunicationException;
import br.uff.ic.labgc.properties.ApplicationProperties;
import br.uff.ic.labgc.properties.IPropertiesConstants;
import br.uff.ic.labgc.server.IServer;
import br.uff.ic.labgc.utils.URL;
import java.lang.reflect.Constructor;
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

    public static IServer getFactory(String hostname, String repository) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
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
     * @param hostName servidor de repositório. Caso seja localhost, devolverá uma instância local do servidor
     * @param repoName Nome do repositório.
     * @return
     */
    public IServer getServer(String hostName, String repoName) throws ApplicationException {

        //TODO CRISTIANO não precisa receber o nome do repositório, pois isso vai ficar no token de login. mudar a lógica do server. se for hostname, devolver instância local, senão, devolver rmi
        try {
            URL connURL = new URL(hostName);
            if (commClient == null) {
                String serverClass = ApplicationProperties.getPropertyValue(connURL.getProtocol() + IPropertiesConstants.CONNECTOR_CLASS);

                Constructor c = Class.forName(serverClass).getConstructor(String.class, String.class);
                commClient = (IServer) c.newInstance(connURL.getHost(), repoName);
            }
        } catch (ApplicationException ex) {
            throw ex;
        }catch (Exception ex) {
            Logger.getLogger(CommunicationFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationException("Não foi possível instanciar um servidor.", ex);
        }
        return commClient;

    }
}
