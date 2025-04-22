package model;
import model.enums.*;

public class Prenotazione {
    private String numeroBiglietto;
    private String codiceFiscalePasseggero;
    private String nomePasseggero;
    private String cognomePasseggero;
    private String numeroPosto;
    // Stato attuale della prenotazione (confermata, in attesa, cancellata).
    private StatoPrenotazione statoPrenotazione;
    private Volo volo;

    //Costruttore
    public Prenotazione(String numeroBiglietto, String codiceFiscalePasseggero, String nomePasseggero, String cognomePasseggero, String numeroPosto, StatoPrenotazione statoPrenotazione, Volo volo) {
        this.numeroBiglietto = numeroBiglietto;
        this.codiceFiscalePasseggero = codiceFiscalePasseggero;
        this.nomePasseggero = nomePasseggero;
        this.cognomePasseggero = cognomePasseggero;
        this.numeroPosto = numeroPosto;
        this.statoPrenotazione = statoPrenotazione;
        this.volo = volo;
    }

    //Restituisce il numero del biglietto della prenotazione.
    public String getNumeroBiglietto() {
        return numeroBiglietto;
    }

    //Restituisce il nome del passeggero associato alla prenotazione.
    public String getNomePasseggero() {
        return nomePasseggero;
    }

    public String visualizzaBiglietto() {
        return nomePasseggero + " " + cognomePasseggero + " - Biglietto: " + numeroBiglietto;
    }
}
