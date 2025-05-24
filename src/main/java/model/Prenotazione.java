package model;

import model.enums.StatoPrenotazione;

public class Prenotazione {
    private String numeroBiglietto;
    private String codiceFiscalePasseggero;
    private String nomePasseggero;
    private String cognomePasseggero;
    private String numeroPosto;
    private StatoPrenotazione statoPrenotazione;
    private Volo volo;

    // Costruttore
    public Prenotazione(String numeroBiglietto, String codiceFiscalePasseggero, String nomePasseggero, String cognomePasseggero, String numeroPosto, StatoPrenotazione statoPrenotazione, Volo volo) {
        this.numeroBiglietto = numeroBiglietto;
        this.codiceFiscalePasseggero = codiceFiscalePasseggero;
        this.nomePasseggero = nomePasseggero;
        this.cognomePasseggero = cognomePasseggero;
        this.numeroPosto = numeroPosto;
        this.statoPrenotazione = statoPrenotazione;
        this.volo = volo;
    }

    public String getNumeroBiglietto() {
        return numeroBiglietto;
    }

    public String getNomePasseggero() {
        return nomePasseggero;
    }
    public String getCodiceFiscalePasseggero() {return codiceFiscalePasseggero; }

    public String getCognomePasseggero() { return cognomePasseggero; }

    public String getNumeroPosto() { return numeroPosto; }

    public StatoPrenotazione getStatoPrenotazione() { return statoPrenotazione; }

    public Volo getVolo() { return volo; }


    // Metodo per visualizzare le informazioni del biglietto
    public void visualizzaBiglietto() {
        System.out.println("Biglietto: " + numeroBiglietto + ", Passeggero: " + nomePasseggero + " " + cognomePasseggero);
    }


    @Override
    public String toString() {
        return "Prenotazione: " + numeroBiglietto + ", Passeggero: " + nomePasseggero + " " + cognomePasseggero + ", Posto: " + numeroPosto + ", Stato: " + statoPrenotazione;
    }
}
