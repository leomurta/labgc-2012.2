/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.server;

import br.uff.ic.labgc.core.*;
import br.uff.ic.labgc.exception.*;

/**
 *
 * @author Felipe R
 */
public interface IServer {

    /**
     * Efetua o commit do item versionado (que pode ser um item composto). A
     * mensagem de commit encontra-se no primeiro item versionado (raiz da
     * árvore a ser atualizada no sistema de controle de versões)
     *
     * @param item Item composto a ser versionado
     * @param token Token que identifica o usuário e o repositório
     * @return Número da nova revisão do repositório
     */
    public String commit(VersionedItem file, String message, String token) throws ApplicationException;

    /**
     * Atualiza a cópia de trabalho com a revisão solicitada.
     *
     * @param revision Revisão para a qual se deseja atualizar a cópia de
     * trabalho
     * @param token Token que identifica o usuário e o repositório
     * @return Item versionado composto referente à revisão solicitada
     */
    public VersionedItem update(String revision, String token) throws ApplicationException;

    /**
     * Efetua o checkout da revisão solicitada.
     *
     * @param revision Revisão que se deseja efetuar o checkout
     * @param token Token que identifica o usuário e o repositório
     * @return Item versionado composto referente à revisão solicitada
     * @throws ServerException
     */
    public VersionedItem checkout(String revision, String token) throws ApplicationException;

    /**
     * Efetua o login de um usuário no repositório solicitado
     *
     * @param user Usuário cadastrado no sistema
     * @param pwd Senha cadastrada no sistema
     * @param repository Repositório no qual se deseja efetuar o login
     * @return Token que identifica o usuário no repositório solicitado
     * @throws ServerException
     */
    public String login(String user, String pwd, String repository) throws ApplicationException;

    public VersionedItem log(String token) throws ApplicationException;

    public VersionedItem log(int qtdeRevisions, String token) throws ApplicationException;

    public String getRepPath();

    public String getRepHost();

    /**
     * Retorna o conteúdo do item versionado representado pelo hash informado.
     *
     * @param hash Identifica o item versionado para o qual se deseja retornar o
     * conteúdo
     * @return Conteúdo do item versionado solicitado
     * @throws ServerException
     */
    public byte[] getItemContent(String hash, String projectName) throws ApplicationException;    
    
    /**
     * ADM Método  para adicionar projeto ao repostório
     * @param project nome do projeto
     * @param user usuário do sistema
     * @throws ApplicationException 
     */
    public void addProject(String project, String user) throws ApplicationException;
    
    /**
     * ADM Método para enviar arquivos para o repositório. Cria uma nova HEAD
     * @param item arquivos, VersionedItem
     * @param user usuário
     * @throws ApplicationException 
     */
    public void init(VersionedItem item, String user) throws ApplicationException;
    
    /**
     * ADM Método para criar um novo usuário no repositório
     * @param name display name do usuário
     * @param username login do usuário
     * @param password senha do usuário
     * @throws ApplicationException 
     */
    public void addUser(String name, String username, String password) throws ApplicationException;
    
    /**
     * ADM Método para vincular um usuário a um projeto
     * @param project nome de um projeto existente no repositório
     * @param user nome de um usuário existe no repositório
     * @throws ApplicationException 
     */
    public void addUserToProject(String project, String user)throws ApplicationException;
}
