package br.uff.ic.labgc.core;

/**
 *
 * @author Cristiano
 */
public interface IObservable {
    /**
     * Registra o interesse de um observador neste objeto observável. Sempre que 
     * ocorrer uma ação que necessite de notificação aos observadores, o objeto
     * observável deve invocar o método sendNotify do objeto observador
     * @param obs 
     */
    public void registerInterest(IObserver obs);
}
