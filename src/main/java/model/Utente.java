package model;

import model.enums.RuoloUtente;

/**
 * The type Utente.
 */
public abstract class Utente {
    private String username;
    private String password;
    private RuoloUtente ruolo=null;

    /**
     * Instantiates a new Utente.
     *
     * @param username the username
     * @param password the password
     */
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() { return username; }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * Gets ruolo.
     *
     * @return the ruolo
     */
    public RuoloUtente getRuolo() { return ruolo; }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Sets ruolo.
     *
     * @param ruolo the ruolo
     */
    public void setRuolo(RuoloUtente ruolo) { this.ruolo = ruolo; }

    /**
     * Verifica password boolean.
     *
     * @param pass the pass
     * @return the boolean
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
