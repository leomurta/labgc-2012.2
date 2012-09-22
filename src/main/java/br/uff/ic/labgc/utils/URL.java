package br.uff.ic.labgc.utils;

import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.properties.IPropertiesConstants;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa uma URL de conexão a um repositório. Para criar uma URL, é
 * possível enviar uma string de URL (ex.: rmi://servidor:porta/caminho).
 *
 * @author cristiano
 */
public class URL {

    private String url;
    private String protocol;
    private String host;
    private String userName;
    private String repoServer;
    private int port;
    private boolean isDefaultPort;
    private static final Map defaultPorts = new HashMap();

    static {
        defaultPorts.put(IPropertiesConstants.COMM_RMI_PROTOCOL, new Integer(1099));
        defaultPorts.put(IPropertiesConstants.COMM_FILE_PROTOCOL, new Integer(0));
    }

    public URL(String url) throws ApplicationException {
        if (url == null) {
            Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL não pode ser nula.");
            throw new ApplicationException("URL não pode ser nula.");
        }
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        int index = url.indexOf("://");
        if (index <= 0) {
            Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL mal formada.");
            throw new ApplicationException("URL malformada.");
        }
        protocol = url.substring(0, index);
        protocol = protocol.toLowerCase();
        if (!defaultPorts.containsKey(protocol)) {
            Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "Protocolo não suportado.");
            throw new ApplicationException("Protocolo não suportado.");
        }
        if (IPropertiesConstants.COMM_FILE_PROTOCOL.equals(protocol)) {

            String hostPath = url.substring("file://".length());
            int slashInd = hostPath.indexOf('/');
            if (slashInd == -1) {
                //sem path, apenas caminho
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL local: ''" + url + "'' contém apenas nome de host, sem path.");
                throw new ApplicationException("URL local: ''" + url + "'' contém apenas nome de host, sem path.");
            }

            repoServer = hostPath.substring(slashInd);
            if (hostPath.equals(repoServer)) {
                host = "";
            } else {
                host = hostPath.substring(0, slashInd);
            }
            
            java.net.URL testURL = null;
            try {
                testURL = new java.net.URL(protocol + "://" + hostPath);
            } catch (MalformedURLException e) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL malformada: ''" + url + "'': " + e.getLocalizedMessage());
                throw new ApplicationException("URL malformada: ''" + url + "'': " + e.getLocalizedMessage());
            }

            userName = testURL.getUserInfo();
            port = testURL.getPort();
        } else {
            //Criando uma url de teste com http, visto que java.net.URL não funciona com protocolo RMI
            String testURL = IPropertiesConstants.COMM_HTTP_PROTOCOL + url.substring(index);
            java.net.URL httpURL;
            try {
                httpURL = new java.net.URL(testURL);
            } catch (MalformedURLException e) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL malformada: ''" + url + "'': " + e.getLocalizedMessage());
                throw new ApplicationException("URL malformada: ''" + url + "'': " + e.getLocalizedMessage());
            }
            host = httpURL.getHost();
            if ("".equals(host)) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL malformada: ''" + url + "''");
                throw new ApplicationException("URL malformada: ''" + url + "''");
            }

            repoServer = getPath(httpURL);
            if ("".equals(repoServer)) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL malformada: ''" + url + "'' contém apenas nome de host, sem path.");
                throw new ApplicationException("URL malformada: ''" + url + "'' contém apenas nome de host, sem path.");
            } else {
                repoServer = repoServer.substring(1); //Retirando a barra inicial para representar o nome do repositório
            }

            if (repoServer.contains("/")) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL malformada: ''" + url + "'' não pode conter ''/''");
                throw new ApplicationException("URL malformada: ''" + url + "'' não pode conter ''/''");
            }
            
            userName = httpURL.getUserInfo();
            port = httpURL.getPort();
            isDefaultPort = port < 0;
            if (port < 0) {
                Integer defaultPort;
                defaultPort = (Integer) defaultPorts.get(protocol);
                port = defaultPort != null ? defaultPort.intValue() : 0;
            }
        }

        if (host != null) {
            host = host.toLowerCase();
        }
    }

    /**
     * Retorna a porta padrão do protocolo informado.
     *
     * @param protocol
     * @return default
     */
    public static int getDefaultPortNumber(String protocol) {
        if (protocol == null) {
            return -1;
        }
        protocol = protocol.toLowerCase();
        if (IPropertiesConstants.COMM_FILE_PROTOCOL.equals(protocol)) {
            return 0;
        }
        Integer dPort;
        synchronized (defaultPorts) {
            dPort = (Integer) defaultPorts.get(protocol);
        }
        if (dPort != null) {
            return dPort.intValue();
        }
        return -1;
    }

    /**
     * Retorna o protocolo da url representada por este objeto.
     *
     * @return a protocol name (like <code>http</code>)
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Retorna o servidor da url representada por este objeto.
     *
     * @return
     */
    public String getHost() {
        return host;
    }

    /**
     * Retorna a porta especificada (ou a default) deste objeto.
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * Retorna o usuário da url representada por este objeto.
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Retorna a string que representa a URL codificada.
     *
     * @return an encoded url string
     */
    @Override
    public String toString() {
        if (url == null) {
            url = composeURL(getProtocol(), getUserName(), getHost(), isDefaultPort ? -1 : getPort(), getRepoServer());
        }
        return url;
    }

    public boolean hasPort() {
        return !isDefaultPort;
    }

    /**
     * Compara este objeto com outro
     *
     * @param obj objeto a ser comparado
     * @return <span class="javakeyword">true</span> se <code>obj</code> é uma
     * instância de <b>URL</b> e possui os mesmos componentes que este objeto.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != URL.class) {
            return false;
        }
        URL u = (URL) obj;
        boolean eq = protocol.equals(u.getProtocol())
                && port == u.getPort()
                && host.equals(u.getHost())
                && hasPort() == u.hasPort();
        if (userName == null) {
            eq &= u.getUserName() == null;
        } else {
            eq &= userName.equals(u.getUserName());
        }
        if (repoServer == null) {
            eq &= u.getRepoServer() == null;
        } else {
            eq &= userName.equals(u.getRepoServer());
        }
        return eq;
    }

    private static String composeURL(String protocol, String userInfo, String host, int port, String repoServer) {
        StringBuilder url = new StringBuilder();
        url.append(protocol).append("://");
        if (userInfo != null) {
            url.append(userInfo).append("@");
        }
        if (host != null) {
            url.append(host);
        }
        if (port >= 0) {
            url.append(":").append(port);
        }
        if (repoServer != null) {
            url.append("/").append(repoServer);
        }
        return url.toString();
    }

    private static String getPath(java.net.URL url) {
        String path = url.getPath();
        String ref = url.getRef();
        if (ref != null) {
            if (path == null) {
                path = "";
            }
            path += '#' + ref;
        }
        return path;
    }

    /**
     *
     * @return
     */
    public String getRepoServer() {
        return repoServer;
    }

    /**
     *
     * @param myRepo
     */
    public void setRepoServer(String myRepo) {
        this.repoServer = myRepo;
    }
}
