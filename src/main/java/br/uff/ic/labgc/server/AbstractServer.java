package br.uff.ic.labgc.server;

import br.uff.ic.labgc.core.VersionedItem;
import br.uff.ic.labgc.exception.ServerException;

/**
 * Classe que implementa métodos comuns a qualquer servidor.
 *
 * @author Cristiano
 */
public abstract class AbstractServer implements IServer {

    private String repPath;
    private String repHost;
    
    public AbstractServer(String hostName) {
        this.repHost = hostName;
    }

    public String getRepPath() {
        return repPath;
    }

    public String getRepHost() {
        return repHost;
    }

    protected void setRepPath(String path) {
        this.repPath = path;
    }

    protected void setRepHost(String hostName) {
        this.repHost = hostName;
    }

}
