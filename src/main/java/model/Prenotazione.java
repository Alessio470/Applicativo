package model;

import model.enums.StatoPrenotazione;

public class Prenotazione {
    private int id;
    private String usernameUtente;    // può essere null se prenotazione senza login
    private String codiceVolo;        // riferimento al codice del volo
    private String nomePasseggero;    // nome completo passeggero
    private String numeroBiglietto;   // identificativo biglietto
    private String posto;             // posto a sedere
    private StatoPrenotazione stato;

    public Prenotazione(int id, String usernameUtente, String codiceVolo, String nomePasseggero,
                        String numeroBiglietto, String posto, StatoPrenotazione stato) {
        this.id = id;
        this.usernameUtente = usernameUtente;
        this.codiceVolo = codiceVolo;
        this.nomePasseggero = nomePasseggero;
        this.numeroBiglietto = numeroBiglietto;
        this.posto = posto;
        this.stato = stato;
    }

    // --- Getter ---
    public int getId() { return id; }
    public String getUsernameUtente() { return usernameUtente; }
    public String getCodiceVolo() { return codiceVolo; }
    public String getNomePasseggero() { return nomePasseggero; }
    public String getNumeroBiglietto() { return numeroBiglietto; }
    public String getPosto() { return posto; }
    public StatoPrenotazione getStato() { return stato; }

    // --- Setter ---
    public void setId(int id) { this.id = id; }
    public void setUsernameUtente(String usernameUtente) { this.usernameUtente = usernameUtente; }
    public void setCodiceVolo(String codiceVolo) { this.codiceVolo = codiceVolo; }
    public void setNomePasseggero(String nomePasseggero) { this.nomePasseggero = nomePasseggero; }
    public void setNumeroBiglietto(String numeroBiglietto) { this.numeroBiglietto = numeroBiglietto; }
    public void setPosto(String posto) { this.posto = posto; }
    public void setStato(StatoPrenotazione stato) { this.stato = stato; }

    // --- Metodi utili ---
    public boolean isConfermata() {
        return stato == StatoPrenotazione.CONFERMATA;
    }

    public boolean appartieneUtente(String username) {
        return usernameUtente != null && usernameUtente.equals(username);
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", utente='" + usernameUtente + '\'' +
                ", codiceVolo='" + codiceVolo + '\'' +
                ", passeggero='" + nomePasseggero + '\'' +
                ", biglietto='" + numeroBiglietto + '\'' +
                ", posto='" + posto + '\'' +
                ", stato=" + stato +
                '}';
    }
}
