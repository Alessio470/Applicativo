package model;

import model.enums.RuoloUtente;

/**
 * Modello astratto per un utente del sistema.
 * <p>Comprende credenziali (username, password) e il {@link RuoloUtente} associato.</p>
 */
public abstract class Utente {

    /** Nome utente. */
    private String username;

    /** Password dell’utente (confrontata in chiaro in questa implementazione). */
    private String password;

    /** Ruolo dell’utente. */
    private RuoloUtente ruolo=null;

    /**
     * Crea un utente con credenziali.
     *
     * @param username nome utente
     * @param password password
     */
    protected Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Restituisce lo username.
     *
     * @return String username
     */
    public String getUsername() { return username; }

    /**
     * Restituisce la password.
     *
     * @return String password
     */
    public String getPassword() { return password; }

    /**
     * Restituisce il ruolo dell’utente.
     *
     * @return {@code RuoloUtente} ruolo utente
     */
    public RuoloUtente getRuolo() { return ruolo; }

    /**
     * Imposta lo username.
     *
     * @param username nuovo username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Imposta la password.
     *
     * @param password nuova password
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Imposta il ruolo dell’utente.
     *
     * @param ruolo nuovo ruolo
     */
    public void setRuolo(RuoloUtente ruolo) { this.ruolo = ruolo; }

    /**
     * Verifica se la password indicata coincide con quella dell’utente.
     *
     * @param pass password da verificare
     * @return boolean {@code true} se coincide, altrimenti {@code false}
     */
    // Metodo utile per login
    public boolean verificaPassword(String pass) {
        return password.equals(pass);
    }

    @Override
    public String toString() {
        return "Utente{username='" + username + "', ruolo=" + ruolo + "}";
    }
}
