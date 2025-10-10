package model;

import model.enums.RuoloUtente;


/**
 * Utente con privilegi standard.
 * <p>All’atto della creazione imposta il ruolo a {@link RuoloUtente#GENERICO}.</p>
 */
public class UtenteGenerico extends Utente {

    /**
     * Crea un utente generico con le credenziali fornite.
     * <p>Il ruolo viene impostato a {@link RuoloUtente#GENERICO}.</p>
     *
     * @param username nome utente
     * @param password password
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
