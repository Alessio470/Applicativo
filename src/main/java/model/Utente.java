package model;

import model.enums.RuoloUtente;

public class Utente {
    private String username;   // identificatore unico
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

    @Override
    public String toString() {
        return "Utente{username='" + username + "', ruolo=" + ruolo + '}';
    }
}
