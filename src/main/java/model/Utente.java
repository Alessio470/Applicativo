package model;

import model.enums.RuoloUtente;

public abstract class Utente {
    private String username;
    private String password;
    private RuoloUtente ruolo;

    public Utente(String username, String password, RuoloUtente ruolo) {
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public RuoloUtente getRuolo() { return ruolo; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRuolo(RuoloUtente ruolo) { this.ruolo = ruolo; }

    // Metodo utile per login
    public boolean verificaPassword(String pass) {
        return password.equals(pass);
    }

    @Override
    public String toString() {
        return "Utente{username='" + username + "', ruolo=" + ruolo + "}";
    }
}
