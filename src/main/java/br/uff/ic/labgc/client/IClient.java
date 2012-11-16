/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.client;

import br.uff.ic.labgc.exception.*;
import br.uff.ic.labgc.core.*;
import java.util.List;


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
    public boolean resolve(String file) throws ApplicationException;
    //implementados
    
    /**
     * Método para verificar o estado do workspace, relacionando itens novos, itens removidos e itens modificados
     * @return
     * @throws ApplicationException 
     */
    public VersionedItem status() throws ApplicationException;
    
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
    
    /**
     * Método para verificar se existe um workspace
     * @return 
     */
    public boolean hasWorkspace();
    
    /**
     * ADM métodos
     */
    
    /**
     * ADM Método criar projeto
     * @param repository nome do novo projeto/reopsitório
     * @param user login do usuário
     * @throws ApplicationException 
     */
    public void admCreateProject(String repository, String user)throws ApplicationException;
    public void admDeleteProject()throws ApplicationException;
    /**
     * ADM método para criar usuário no servidor
     * @param name displayname do usuario
     * @param username login
     * @param password senha
     * @throws ApplicationException 
     */
    public void admCreateUser(String name, String username, String password)throws ApplicationException;
    public void admDeleteUser()throws ApplicationException;
    /**
     * ADM Método para vincular um usuário existente a um projeto existente no servidor
     * @param repository nome do repositório/projeto
     * @param user login do usuário
     * @throws ApplicationException 
     */
    public void admAddUserToProject(String repository, String user)throws ApplicationException;
    public void admRemoveUserFromProject()throws ApplicationException;
    /**
     * ADM Método para criar um novo repositorio no servidor por envio de arquivos
     * @param username nome do usuário
     * @throws ApplicationException 
     */
    public void admCheckin(String username)throws ApplicationException;
}

