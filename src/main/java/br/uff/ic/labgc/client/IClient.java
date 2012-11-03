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
public interface IClient extends IObservable{
    
    //comandos server
    
    public VersionedItem commit(String message) throws ApplicationException;
    public VersionedItem update(String revision) throws ApplicationException;
    public VersionedItem diff(String file, String version) throws ApplicationException;
    public VersionedItem log() throws ApplicationException;
    public VersionedItem status() throws ApplicationException;
    public boolean resolve(String file) throws ApplicationException;
    
    //implementados
    /**
     * Desfaz as alteracoes ainda nao comitadas do espaco de trabalho
     * @return
     * @throws ClientWorkspaceUnavailableException, workspace nao existe ou nao disponivel
     */
    boolean revert()throws ApplicationException;
    /**
     * Solicita o checkout de um repositório. Os checkouts são sempre de todo o repositório.
     * @param revision Número da revisão que se deseja. Caso seja desejada a revisão HEAD, passa a constant EVCSConstants.REVISION_HEAD
     * @throws ClientWorkspaceUnavailableException, workspace nao existe ou nao disponivel
     * @throws ClientLoginRequiredException , necessario logar para executar essa funcionalidade
     */
    void checkout(String revision) throws ApplicationException;
     /**
     * Executa login no servidor
     * @param user usuario cadastrado no sistema
     * @param pwd senha cadastrada no sistema
     * @param repository repositório no qual se deseja efetuar o login
     * @throws ClientServerNotAvailableException, servidor nao pode ser contactado
     */
    void login(String user, String pwd) throws ApplicationException;
    /**
     * Retorna true, quando o token de login existir no cliente e falso quando nao existir o token.
     * O metodo instancia o servidor e traz o token do arquivo para a memoria. Pode lancar excessoes em caso de falha ao conectar para o servidor
     *
     * @return
     * 
     */
    public boolean isLogged()throws ApplicationException;
     
    
}

