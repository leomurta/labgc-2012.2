/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.client;

import br.uff.ic.labgc.exception.*;
import br.uff.ic.labgc.core.*;


/**
 *
 * @author Felipe R
 */
public interface IClient {
    
    //comandos server
    
    boolean commit(String message);
    String update();
    
    String diff(String file, String version);
    String log();
    
    //comandos similares ao OS
    
    boolean remove(String file);
    boolean move(String file, String dest);
    boolean copy(String file, String dest);
    boolean mkdir(String name);
    
    //comandos do diretorio
    
    boolean add(String file);
    
    String status();
    boolean release();
    boolean resolve(String file);
    
    //implementados
    /**
     * Desfaz as alteracoes ainda nao comitadas do espaco de trabalho
     * @return
     * @throws ClientWorkspaceUnavailableException, workspace nao existe ou nao disponivel
     */
    boolean revert()throws ClientException;
    /**
     * Solicita o checkout de um repositório. Os checkouts são sempre de todo o repositório.
     * @param revision Número da revisão que se deseja. Caso seja desejada a revisão HEAD, passa a constant EVCSConstants.REVISION_HEAD
     * @throws ClientWorkspaceUnavailableException, workspace nao existe ou nao disponivel
     * @throws ClientLoginRequiredException , necessario logar para executar essa funcionalidade
     */
    void checkout(String revision) throws ClientException;
     /**
     * Executa login no servidor
     * @param user usuario cadastrado no sistema
     * @param pwd senha cadastrada no sistema
     * @param repository repositório no qual se deseja efetuar o login
     * @throws ClientServerNotAvailableException, servidor nao pode ser contactado
     */
    void login(String user, String pwd) throws ClientException;
    /**
     * Retorna true, quando o token de login existir no cliente e falso quando nao existir o token.
     * O metodo instancia o servidor e traz o token do arquivo para a memoria. Pode lancar excessoes em caso de falha ao conectar para o servidor
     *
     * @return
     * 
     */
    public boolean isLogged()throws ClientException;
     
    
}

