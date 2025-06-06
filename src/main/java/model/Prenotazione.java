package model;

import model.enums.StatoPrenotazione;

/**
 * The type Prenotazione.
 */
public class Prenotazione {
    private String numeroBiglietto;
    private String codiceFiscalePasseggero;
    private String nomePasseggero;
    private String cognomePasseggero;
    private String numeroPosto;
    private StatoPrenotazione statoPrenotazione;
    private Volo volo;

    /**
     * Instantiates a new Prenotazione.
     *
     * @param numeroBiglietto         the numero biglietto
     * @param codiceFiscalePasseggero the codice fiscale passeggero
     * @param nomePasseggero          the nome passeggero
     * @param cognomePasseggero       the cognome passeggero
     * @param numeroPosto             the numero posto
     * @param statoPrenotazione       the stato prenotazione
     * @param volo                    the volo
     */
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

    /**
     * Gets numero biglietto.
     *
     * @return the numero biglietto
     */
    public String getNumeroBiglietto() {
        return numeroBiglietto;
    }

    /**
     * Gets nome passeggero.
     *
     * @return the nome passeggero
     */
    public String getNomePasseggero() {
        return nomePasseggero;
    }

    /**
     * Gets codice fiscale passeggero.
     *
     * @return the codice fiscale passeggero
     */
    public String getCodiceFiscalePasseggero() {return codiceFiscalePasseggero; }

    /**
     * Gets cognome passeggero.
     *
     * @return the cognome passeggero
     */
    public String getCognomePasseggero() { return cognomePasseggero; }

    /**
     * Gets numero posto.
     *
     * @return the numero posto
     */
    public String getNumeroPosto() { return numeroPosto; }

    /**
     * Gets stato prenotazione.
     *
     * @return the stato prenotazione
     */
    public StatoPrenotazione getStatoPrenotazione() { return statoPrenotazione; }

    /**
     * Gets volo.
     *
     * @return the volo
     */
    public Volo getVolo() { return volo; }


    /**
     * Visualizza biglietto.
     */
// Metodo per visualizzare le informazioni del biglietto
    public void visualizzaBiglietto() {
        System.out.println("Biglietto: " + numeroBiglietto + ", Passeggero: " + nomePasseggero + " " + cognomePasseggero);
    }


    @Override
    public String toString() {
        return "Prenotazione: " + numeroBiglietto + ", Passeggero: " + nomePasseggero + " " + cognomePasseggero + ", Posto: " + numeroPosto + ", Stato: " + statoPrenotazione;
    }
}
