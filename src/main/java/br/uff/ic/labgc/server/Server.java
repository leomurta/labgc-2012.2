/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.ApplicationException;
import br.uff.ic.labgc.exception.ServerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe R
 */
public class Server extends AbstractServer {

    String serverTempToken = "zyx";
    String serverTempFile = "pom.xml";

    public Server(String hostName) {
        super(hostName);
    }

    public String commit(VersionedItem file, String token) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionedItem update(String revision, String token) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionedItem checkout(String revision, String token) throws ApplicationException {



        VersionedFile file = new VersionedFile();

        file.setAuthor("lagc");
        file.setHash(sha1());
        file.setName(serverTempFile);
        file.setLastChangedRevision("5");
        file.setLastChangedTime(new Date(1349792243));
        file.setSize(tempGetSize());

        return file;
    }

    public String diff(VersionedItem file, String version) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String log() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String login(String user, String pwd, String repository) throws ApplicationException {
        setRepPath(repository);
        return serverTempToken;
    }

    public byte[] getItemContent(String hash) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String tempSHA1() throws IOException, NoSuchAlgorithmException {

        MessageDigest md;

        md = MessageDigest.getInstance("SHA1");

        FileInputStream fis = new FileInputStream("..//..//"+serverTempFile);
        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };

        byte[] mdbytes = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();

    }
    private String sha1(){
        try {
            return tempSHA1();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private long tempGetSize() {

        File file = new File(serverTempFile);
        return file.length();
    }
}
