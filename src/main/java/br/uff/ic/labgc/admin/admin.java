/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.admin;

import br.uff.ic.labgc.exception.StorageException;
import br.uff.ic.labgc.exception.StorageObjectAlreadyExistException;
import br.uff.ic.labgc.storage.Storage;
import br.uff.ic.labgc.storage.util.HibernateUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jokerfvd
 */
public class admin {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int opcao;
        String name, username, password,projName;
        Storage storage = new Storage();
        while(true){
            System.out.println("O que vc deseja fazer?");
            System.out.println("1- Cadastrar usuario");
            System.out.println("2- Adicionar usuário a um projeto");
            System.out.println("3- Sair");   
            opcao = Integer.parseInt(reader.readLine().toString());
            switch (opcao){
                case 1:
                    System.out.println("Digite o nome do usuário");
                    name = reader.readLine();
                    System.out.println("Digite o username do usuário");
                    username = reader.readLine();
                    System.out.println("Digite a senha do usuário");
                    password = reader.readLine();
            try {
                HibernateUtil.beginTransaction();
                storage.addUser(name,username,password);
                HibernateUtil.commitTransaction();
            } catch (StorageObjectAlreadyExistException ex) {
                Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Usuario já existe");
                return;
            }
                    break;
                case 2:
                    System.out.println("Digite o nome do projeto");
                    projName = reader.readLine();
                    System.out.println("Digite o username do usuário");
                    username = reader.readLine();
            try {
                HibernateUtil.beginTransaction();
                storage.addUserToProject(projName,username);
                HibernateUtil.commitTransaction();
            } catch (StorageException ex) {
                Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Erro: "+ex.getMessage());
                return;
            }
                    break;
                case 3:
                    return;
            }
        }
    }
}
