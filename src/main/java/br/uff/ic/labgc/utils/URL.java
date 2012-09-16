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

    private String myURL;
    private String myProtocol;
    private String myHost;
    private String myUserName;
    private String myRepo;
    private int myPort;
    private boolean myIsDefaultPort;
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
        myProtocol = url.substring(0, index);
        myProtocol = myProtocol.toLowerCase();
        if (!defaultPorts.containsKey(myProtocol)) {
            Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "Protocolo não suportado.");
            throw new ApplicationException("Protocolo não suportado.");
        }
        if (IPropertiesConstants.COMM_FILE_PROTOCOL.equals(myProtocol)) {

            String hostPath = url.substring("file://".length());
            int slashInd = hostPath.indexOf('/');
            if (slashInd == -1) {
                //sem path, apenas caminho
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL local: ''" + url + "'' contém apenas nome de host, sem path.");
                throw new ApplicationException("URL local: ''" + url + "'' contém apenas nome de host, sem path.");
            }

            myRepo = hostPath.substring(slashInd);
            if (hostPath.equals(myRepo)) {
                myHost = "";
            } else {
                myHost = hostPath.substring(0, slashInd);
            }
            
            java.net.URL testURL = null;
            try {
                testURL = new java.net.URL(myProtocol + "://" + hostPath);
            } catch (MalformedURLException e) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL malformada: ''" + url + "'': " + e.getLocalizedMessage());
                throw new ApplicationException("URL malformada: ''" + url + "'': " + e.getLocalizedMessage());
            }

            myUserName = testURL.getUserInfo();
            myPort = testURL.getPort();
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
            myHost = httpURL.getHost();
            if ("".equals(myHost)) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL malformada: ''" + url + "''");
                throw new ApplicationException("URL malformada: ''" + url + "''");
            }

            myRepo = getPath(httpURL);
            if ("".equals(myRepo)) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL malformada: ''" + url + "'' contém apenas nome de host, sem path.");
                throw new ApplicationException("URL malformada: ''" + url + "'' contém apenas nome de host, sem path.");
            } else {
                myRepo = myRepo.substring(1); //Retirando a barra inicial para representar o nome do repositório
            }

            if (myRepo.contains("/")) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, "URL malformada: ''" + url + "'' não pode conter ''/''");
                throw new ApplicationException("URL malformada: ''" + url + "'' não pode conter ''/''");
            }
            
            myUserName = httpURL.getUserInfo();
            myPort = httpURL.getPort();
            myIsDefaultPort = myPort < 0;
            if (myPort < 0) {
                Integer defaultPort;
                defaultPort = (Integer) defaultPorts.get(myProtocol);
                myPort = defaultPort != null ? defaultPort.intValue() : 0;
            }
        }

        if (myHost != null) {
            myHost = myHost.toLowerCase();
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
        return myProtocol;
    }

    /**
     * Retorna o servidor da url representada por este objeto.
     *
     * @return
     */
    public String getHost() {
        return myHost;
    }

    /**
     * Retorna a porta especificada (ou a default) deste objeto.
     *
     * @return
     */
    public int getPort() {
        return myPort;
    }

    /**
     * Retorna o usuário da url representada por este objeto.
     *
     * @return
     */
    public String getUserInfo() {
        return myUserName;
    }

    /**
     * Retorna a string que representa a URL codificada.
     *
     * @return an encoded url string
     */
    public String toString() {
        if (myURL == null) {
            myURL = composeURL(getProtocol(), getUserInfo(), getHost(), myIsDefaultPort ? -1 : getPort());
        }
        return myURL;
    }

    public boolean hasPort() {
        return !myIsDefaultPort;
    }

    /**
     * Compara este objeto com outro
     *
     * @param obj objeto a ser comparado
     * @return <span class="javakeyword">true</span> se <code>obj</code> é uma
     * instância de <b>URL</b> e possui os mesmos componentes que este objeto.
     */
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != URL.class) {
            return false;
        }
        URL url = (URL) obj;
        boolean eq = myProtocol.equals(url.myProtocol)
                && myPort == url.myPort
                && myHost.equals(url.myHost)
                && hasPort() == url.hasPort();
        if (myUserName == null) {
            eq &= url.myUserName == null;
        } else {
            eq &= myUserName.equals(url.myUserName);
        }
        return eq;
    }

    private static String composeURL(String protocol, String userInfo, String host, int port) {
        StringBuffer url = new StringBuffer();
        url.append(protocol);
        url.append("://");
        if (userInfo != null) {
            url.append(userInfo);
            url.append("@");
        }
        if (host != null) {
            url.append(host);
        }
        if (port >= 0) {
            url.append(":");
            url.append(port);
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

    public String getMyRepo() {
        return myRepo;
    }

    public void setMyRepo(String myRepo) {
        this.myRepo = myRepo;
    }
}
