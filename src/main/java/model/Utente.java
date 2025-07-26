package model;

import model.enums.RuoloUtente;

public class Utente {
    private int id;
    private String username;
    private String password;
    private RuoloUtente ruolo;

    public Utente(int id, String username, String password, RuoloUtente ruolo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public RuoloUtente getRuolo() { return ruolo; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRuolo(RuoloUtente ruolo) { this.ruolo = ruolo; }

    @Override
    public String toString() {
        return "Utente{id=" + id + ", username='" + username + "', ruolo=" + ruolo + '}';
    }

}
