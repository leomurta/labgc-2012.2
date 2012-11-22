package br.uff.ic.labgc.comm.client;

import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.CommunicationException;
import br.uff.ic.labgc.properties.ApplicationProperties;
import br.uff.ic.labgc.properties.IPropertiesConstants;
import br.uff.ic.labgc.server.IServer;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cristiano
 */
public class CommunicationFactory {

    private static CommunicationFactory instance;
    private Map<String, IServer> commClientList;
    //private IServer server;

    private CommunicationFactory() {
        LoggerFactory.getLogger(CommunicationFactory.class).debug("Inicializando CommunicationFactory.");
        commClientList = new ConcurrentHashMap<>(2);
        LoggerFactory.getLogger(CommunicationFactory.class).debug("Inicialização de CommunicationFactory finalizada com sucesso.");
    }

    /**
     * Método que retorna um singleton da fábrica de objetos de comunicação
     *
     * @return
     */
    public synchronized static CommunicationFactory getFactory() {
        LoggerFactory.getLogger(CommunicationFactory.class).trace("getFactory -> Entry");
        if (instance == null) {
            LoggerFactory.getLogger(CommunicationFactory.class).debug("CommunicationFactory nulo, criando nova instancia.");
            instance = new CommunicationFactory();
        }

        LoggerFactory.getLogger(CommunicationFactory.class).trace("getFactory -> Exit");
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
        LoggerFactory.getLogger(CommunicationFactory.class).trace("getServer -> Entry");
        LoggerFactory.getLogger(CommunicationFactory.class).debug("procurando conector para o servidor {}.", hostName);
        IServer retorno = null;
        if (hostName == null || "".equals(hostName)) {
            LoggerFactory.getLogger(CommunicationFactory.class).error("hostName nao pode ser nulo ou vazio.");
            throw new ApplicationException("hostName não pode ser nulo ou vazio.");
        }
        if (commClientList.get(hostName) == null) {
            LoggerFactory.getLogger(CommunicationFactory.class).debug("Ainda nao há conector para {}. Um novo conector será inicializado.", hostName);
            try {
                String serverClass = ApplicationProperties.getPropertyValue(getCommunicationStrategy(hostName));
                Constructor c = Class.forName(serverClass).getConstructor(String.class);
//                server = (IServer)c.newInstance(hostName);
                commClientList.put(hostName, (IServer) c.newInstance(hostName));
                LoggerFactory.getLogger(CommunicationFactory.class).debug("Conector para {} inicializado e armazenado no cache.", hostName);
            } catch (Exception ex) {
                Logger.getLogger(CommunicationFactory.class.getName()).log(Level.SEVERE, null, ex);
                throw new CommunicationException("Não foi possível instanciar um servidor.", ex);
            }
        } else {
            LoggerFactory.getLogger(CommunicationFactory.class).debug("Conector para {} recuperado do cache.", hostName);
        }
        LoggerFactory.getLogger(CommunicationFactory.class).trace("getServer -> Exit");
        return commClientList.get(hostName);

    }

//     public IServer getServer() throws ApplicationException {
//        return server;
//
//    }
//    
    /**
     * Retorna o identificador da estratégia de comunicação. Caso o hostname
     * seja localhost ou o mesmo hostname da propriedade localAddressIdentifier,
     * retorna uma estratégia local. Caso contrário, retorna uma estratégia
     * remota.
     *
     * @param hostName
     * @return
     */
    private String getCommunicationStrategy(String hostName) {
        LoggerFactory.getLogger(CommunicationFactory.class).trace("getCommunicationStrategy -> Entry");
        LoggerFactory.getLogger(CommunicationFactory.class).debug("Buscando estratégia de comunicação com o servidor {}", hostName);
        String commStrategy = IPropertiesConstants.COMM_LOCAL_CONNECTOR;
        if (!IPropertiesConstants.COMM_LOCAL_HOST.equalsIgnoreCase(hostName)
                && !IPropertiesConstants.COMM_LOCAL_IP.equalsIgnoreCase(hostName)) {
            commStrategy = IPropertiesConstants.COMM_REMOTE_CONNECTOR;
        }
        LoggerFactory.getLogger(CommunicationFactory.class).debug("Estratégia utilizada para comunicar-se com {} será {}.", hostName, commStrategy);
        LoggerFactory.getLogger(CommunicationFactory.class).trace("getCommunicationStrategy -> Exit");
        return commStrategy;
    }
}
