package br.uff.ic.labgc.core;

/**
 *
 *  Classe que representa objetos que aguardam notificações 
 * @author Cristiano
 */
public interface IObserver {
    /**
     * Recebe notificações de alguma ação foi realizada em um item versionado.
     * O path completo do item versionado é enviado como parâmetro, para que possa
     * ser apresentado ao usuário. Para receber notificações, um objeto observador
     * deve se registrar junto a objetos observáveis utilizando o método registerInterest.
     * @param path 
     */
    public void sendNotify(String path);
}
