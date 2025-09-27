package model;

import model.enums.StatoPrenotazione;

public class Prenotazione {
    private String usernameUtente;    // può essere null se prenotazione senza login
    private String codiceVolo;        // riferimento al codice del volo
    private String nomePasseggero;    // nome passeggero
    private String cognomePasseggero; // cognome passeggero
    private String numeroBiglietto;   // identificativo biglietto
    private String posto;             // posto a sedere
    private StatoPrenotazione stato;
    private String codicefiscalepasseggero;

    //numerobiglietto,username,codvolo,nomepasseggero,cognomepasseggero,numeroposto,statoprenotazione,codicefiscalepasseggero


    public Prenotazione(String numeroBiglietto,String usernameUtente, String codiceVolo, String nomePasseggero,String cognomePasseggero,String posto, StatoPrenotazione stato, String codicefiscalepasseggero) {

        this.usernameUtente = usernameUtente;
        this.codiceVolo = codiceVolo;
        this.nomePasseggero = nomePasseggero;
        this.cognomePasseggero=cognomePasseggero;
        this.numeroBiglietto = numeroBiglietto;
        this.posto = posto;
        this.stato = stato;
        this.codicefiscalepasseggero= codicefiscalepasseggero;
    }

    // --- Getter ---
    public String getUsernameUtente() { return usernameUtente; }
    public String getCodiceVolo() { return codiceVolo; }
    public String getNomePasseggero() { return nomePasseggero; }
    public String getCognomePasseggero() { return cognomePasseggero; }
    public String getCodicefiscalepasseggero() { return codicefiscalepasseggero; }
    public String getNumeroBiglietto() { return numeroBiglietto; }
    public String getPosto() { return posto; }
    public StatoPrenotazione getStato() { return stato; }

    // --- Setter ---
    public void setUsernameUtente(String usernameUtente) { this.usernameUtente = usernameUtente; }
    public void setCodiceVolo(String codiceVolo) { this.codiceVolo = codiceVolo; }
    public void setNomePasseggero(String nomePasseggero) { this.nomePasseggero = nomePasseggero; }
    public void setCognomePasseggero(String cognomePasseggero) { this.cognomePasseggero = cognomePasseggero; }
    public void setCodicefiscalepasseggero(String codicefiscalepasseggero) { this.codicefiscalepasseggero = codicefiscalepasseggero; }
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
                ", utente='" + usernameUtente + '\'' +
                ", codiceVolo='" + codiceVolo + '\'' +
                ", passeggero='" + nomePasseggero + '\'' +
                ", biglietto='" + numeroBiglietto + '\'' +
                ", posto='" + posto + '\'' +
                ", stato=" + stato +
                '}';
    }
}
