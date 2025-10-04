package model;

import model.enums.RuoloUtente;


/**
 * The type Utente amministratore.
 */
public class UtenteAmministratore extends Utente {


    /**
     * Instantiates a new Utente amministratore.
     *
     * @param username the username
     * @param password the password
     */
    public UtenteAmministratore(String username, String password) {
        super(username, password);

        this.setRuolo(RuoloUtente.AMMINISTRATORE);
    }


    /**
     * Modifica gate.
     */
//TODO
    public void modificaGate() {  }

    @Override
    public String toString() {
        return "UtenteAmministratore{" + getUsername() + "}";
    }
}
