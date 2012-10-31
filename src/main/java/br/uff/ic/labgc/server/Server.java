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
import java.io.InputStream;
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

        return this.createProjectDir();
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
        
        File file = new File("..//..//" + serverTempFile);
        
        try {
            
            return getBytesFromFile(file);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException("Não foi possivel ler arquivo");
        }
        
    }

    /*
     * 
     * metodos temporarios que serao excluidos
     */
    private String tempSHA1() throws IOException, NoSuchAlgorithmException {

        MessageDigest md;

        md = MessageDigest.getInstance("SHA1");

        FileInputStream fis = new FileInputStream("..//..//" + serverTempFile);
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

    private String sha1() {
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

    private VersionedItem createProjectDir() {

        VersionedDir dir = new VersionedDir();
        VersionedFile file = this.createFile();
        VersionedFile file2 = this.createFile2();

        dir.setName(getRepPath());
        dir.setAuthor(file.getAuthor());
        dir.setCommitMessage(file.getCommitMessage());
        dir.setLastChangedRevision(file.getLastChangedRevision());
        dir.setLastChangedTime(file.getLastChangedTime());
        dir.addItem(file);
        dir.addItem(file2);


        return dir;

    }

    private VersionedFile createFile() {

        VersionedFile file = new VersionedFile();

        file.setAuthor("lagc");
        file.setName(serverTempFile);
        file.setLastChangedRevision("5");
        file.setLastChangedTime(new Date(1349792243));
        file.setCommitMessage("primeiro commit");
        

        return file;
    }

    private VersionedFile createFile2() {

        VersionedFile file = new VersionedFile();

        file.setAuthor("lagc");
        file.setName("a" + serverTempFile);
        file.setLastChangedRevision("5");
        file.setLastChangedTime(new Date(1349792243));
        file.setCommitMessage("primeiro commit");

        return file;
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        
        String value = new String(bytes);
        
        System.out.println("\n\n\nConteudo do pom.xml:\n\n"+value);
        
        return bytes;
    }
}
