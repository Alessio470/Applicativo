package model;

import model.enums.RuoloUtente;


/**
 * Utente con privilegi amministrativi.
 * <p>All’atto della creazione imposta il ruolo a {@link RuoloUtente#AMMINISTRATORE}.</p>
 */
public class UtenteAmministratore extends Utente {

    /**
     * Crea un utente amministratore con le credenziali fornite.
     * <p>Il ruolo viene impostato a {@link RuoloUtente#AMMINISTRATORE}.</p>
     *
     * @param username nome utente
     * @param password password
     */
    public UtenteAmministratore(String username, String password) {
        super(username, password);

        this.setRuolo(RuoloUtente.AMMINISTRATORE);
    }

    /**
     * Punto d’estensione per la modifica dei gate.
     * <p>Da implementare: logica di aggiornamento dei gate lato amministratore.</p>
     */
    public void modificaGate() {  }//TODO

    @Override
    public String toString() {
        return "UtenteAmministratore{" + getUsername() + "}";
    }
}
