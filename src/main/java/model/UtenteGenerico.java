package model;

import model.enums.RuoloUtente;


/**
 * The type Utente generico.
 */
public class UtenteGenerico extends Utente {

    /**
     * Instantiates a new Utente generico.
     *
     * @param username the username
     * @param password the password
     */
    public UtenteGenerico(String username, String password) {
        super(username, password);
        this.setRuolo(RuoloUtente.GENERICO);

    }


    @Override
    public String toString() {
        return "UtenteGenerico{" + getUsername() + "}";
    }
}
