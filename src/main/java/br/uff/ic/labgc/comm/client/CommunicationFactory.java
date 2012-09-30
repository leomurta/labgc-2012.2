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
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristiano
 */
public class CommunicationFactory {

    private static CommunicationFactory instance;
    private HashMap<String, IServer> commClient;

    private CommunicationFactory() {
        commClient = new HashMap<String, IServer>(2);
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
     * @param hostName servidor de repositório. Caso seja localhost, devolverá
     * uma instância local do servidor.
     * @return Instância local ou remota que implementa a interface IServer.
     */
    public IServer getServer(String hostName) throws ApplicationException {
        IServer retorno = null;
        if (hostName == null || "".equals(hostName)) {
            throw new ApplicationException("hostName não pode ser nulo ou vazio.");
        }
        if (commClient.get(hostName) == null) {
            try {
                String commStrategy = IPropertiesConstants.COMM_LOCAL_CONNECTOR;
                if (!IPropertiesConstants.COMM_LOCAL_HOST.equalsIgnoreCase(hostName)) {
                    commStrategy = IPropertiesConstants.COMM_REMOTE_CONNECTOR;
                }

                String serverClass = ApplicationProperties.getPropertyValue(commStrategy);
                Constructor c = Class.forName(serverClass).getConstructor(String.class);
                commClient.put(hostName, (IServer) c.newInstance(hostName));
            } catch (Exception ex) {
                Logger.getLogger(CommunicationFactory.class.getName()).log(Level.SEVERE, null, ex);
                throw new CommunicationException("Não foi possível instanciar um servidor.", ex);
            }
        }
        return commClient.get(hostName);

    }
}
