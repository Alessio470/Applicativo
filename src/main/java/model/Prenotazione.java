package model;

import model.enums.StatoPrenotazione;
import model.enums.StatoVolo;

/**
 * The type Prenotazione.
 */
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

    private Volo voloassociato;


    /**
     * Instantiates a new Prenotazione.
     *
     * @param numeroBiglietto         the numero biglietto
     * @param usernameUtente          the username utente
     * @param codiceVolo              the codice volo
     * @param nomePasseggero          the nome passeggero
     * @param cognomePasseggero       the cognome passeggero
     * @param posto                   the posto
     * @param stato                   the stato
     * @param codicefiscalepasseggero the codicefiscalepasseggero
     */
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

    /**
     * Instantiates a new Prenotazione.
     *
     * @param numeroBiglietto         the numero biglietto
     * @param usernameUtente          the username utente
     * @param codiceVolo              the codice volo
     * @param nomePasseggero          the nome passeggero
     * @param cognomePasseggero       the cognome passeggero
     * @param posto                   the posto
     * @param stato                   the stato
     * @param codicefiscalepasseggero the codicefiscalepasseggero
     */
    public Prenotazione(String numeroBiglietto,String usernameUtente, String codiceVolo, String nomePasseggero,String cognomePasseggero,String posto, int stato, String codicefiscalepasseggero) {

        this.usernameUtente = usernameUtente;
        this.codiceVolo = codiceVolo;
        this.nomePasseggero = nomePasseggero;
        this.cognomePasseggero=cognomePasseggero;
        this.numeroBiglietto = numeroBiglietto;
        this.posto = posto;
        this.stato = fromInt(stato);
        this.codicefiscalepasseggero= codicefiscalepasseggero;
    }


    /**
     * Gets username utente.
     *
     * @return the username utente
     */
// --- Getter ---
    public String getUsernameUtente() { return usernameUtente; }

    /**
     * Gets codice volo.
     *
     * @return the codice volo
     */
    public String getCodiceVolo() { return codiceVolo; }

    /**
     * Gets nome passeggero.
     *
     * @return the nome passeggero
     */
    public String getNomePasseggero() { return nomePasseggero; }

    /**
     * Gets cognome passeggero.
     *
     * @return the cognome passeggero
     */
    public String getCognomePasseggero() { return cognomePasseggero; }

    /**
     * Gets codicefiscalepasseggero.
     *
     * @return the codicefiscalepasseggero
     */
    public String getCodicefiscalepasseggero() { return codicefiscalepasseggero; }

    /**
     * Gets numero biglietto.
     *
     * @return the numero biglietto
     */
    public String getNumeroBiglietto() { return numeroBiglietto; }

    /**
     * Gets posto.
     *
     * @return the posto
     */
    public String getPosto() { return posto; }

    /**
     * Gets stato.
     *
     * @return the stato
     */
    public StatoPrenotazione getStato() { return stato; }

    /**
     * Gets voloassociato.
     *
     * @return the voloassociato
     */
    public Volo getVoloassociato() {
        return voloassociato;
    }

    /**
     * Sets username utente.
     *
     * @param usernameUtente the username utente
     */
// --- Setter ---
    public void setUsernameUtente(String usernameUtente) { this.usernameUtente = usernameUtente; }

    /**
     * Sets codice volo.
     *
     * @param codiceVolo the codice volo
     */
    public void setCodiceVolo(String codiceVolo) { this.codiceVolo = codiceVolo; }

    /**
     * Sets nome passeggero.
     *
     * @param nomePasseggero the nome passeggero
     */
    public void setNomePasseggero(String nomePasseggero) { this.nomePasseggero = nomePasseggero; }

    /**
     * Sets cognome passeggero.
     *
     * @param cognomePasseggero the cognome passeggero
     */
    public void setCognomePasseggero(String cognomePasseggero) { this.cognomePasseggero = cognomePasseggero; }

    /**
     * Sets codicefiscalepasseggero.
     *
     * @param codicefiscalepasseggero the codicefiscalepasseggero
     */
    public void setCodicefiscalepasseggero(String codicefiscalepasseggero) { this.codicefiscalepasseggero = codicefiscalepasseggero; }

    /**
     * Sets numero biglietto.
     *
     * @param numeroBiglietto the numero biglietto
     */
    public void setNumeroBiglietto(String numeroBiglietto) { this.numeroBiglietto = numeroBiglietto; }

    /**
     * Sets posto.
     *
     * @param posto the posto
     */
    public void setPosto(String posto) { this.posto = posto; }

    /**
     * Sets stato.
     *
     * @param stato the stato
     */
    public void setStato(StatoPrenotazione stato) { this.stato = stato; }

    /**
     * Sets voloassociato.
     *
     * @param voloassociato the voloassociato
     */
    public void setVoloassociato(Volo voloassociato) { this.voloassociato = voloassociato; }


    /**
     * Is confermata boolean.
     *
     * @return the boolean
     */
// --- Metodi utili ---
    public boolean isConfermata() {
        return stato == StatoPrenotazione.CONFERMATA;
    }

    /**
     * Appartiene utente boolean.
     *
     * @param username the username
     * @return the boolean
     */
    public boolean appartieneUtente(String username) {
        return usernameUtente != null && usernameUtente.equals(username);
    }


    private StatoPrenotazione fromInt(int stato) {

        return switch (stato) {
            case 1 -> StatoPrenotazione.IN_ATTESA;
            case 2 -> StatoPrenotazione.CONFERMATA;
            case 3 -> StatoPrenotazione.CANCELLATA;

            default -> throw new IllegalArgumentException("Codice stato volo sconosciuto: " + stato);
        };

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
