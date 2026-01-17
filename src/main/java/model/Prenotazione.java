package model;

import model.enums.StatoPrenotazione;

/**
 * Rappresenta una prenotazione di volo.
 * <p>Contiene riferimenti all’utente (facoltativo), al volo, ai dati del passeggero,
 * al numero biglietto, al posto e allo stato; può includere anche il {@link Volo} associato.</p>
 */
public class Prenotazione {

    /** Username dell’utente che ha effettuato la prenotazione (campo facoltativo). */
    private String usernameUtente;

    /** Codice del volo prenotato (riferimento esterno). */
    private String codiceVolo;

    /** Nome del passeggero. */
    private String nomePasseggero;

    /** Cognome del passeggero. */
    private String cognomePasseggero;

    /** Identificativo del biglietto. */
    private String numeroBiglietto;

    /** Posto a sedere assegnato. */
    private String posto;

    /** Stato corrente della prenotazione. */
    private StatoPrenotazione stato;

    /** Codice fiscale del passeggero. */
    private String codicefiscalepasseggero;

    //numerobiglietto,username,codvolo,nomepasseggero,cognomepasseggero,numeroposto,statoprenotazione,codicefiscalepasseggero

    /** Entità volo eventualmente collegata alla prenotazione. */
    private Volo voloassociato;


    /**
     * Crea una prenotazione specificando tutti i dati, con lo stato come valore dell’enum {@link StatoPrenotazione}.
     *
     * @param numeroBiglietto         identificativo del biglietto
     * @param usernameUtente          username dell’utente (facoltativo)
     * @param codiceVolo              codice del volo
     * @param nomePasseggero          nome del passeggero
     * @param cognomePasseggero       cognome del passeggero
     * @param posto                   posto a sedere
     * @param stato                   stato della prenotazione
     * @param codicefiscalepasseggero codice fiscale del passeggero
     */
    public Prenotazione(String numeroBiglietto,String usernameUtente, String codiceVolo, String nomePasseggero,String cognomePasseggero,String posto, StatoPrenotazione stato, String codicefiscalepasseggero) {

        this.usernameUtente = usernameUtente;
        this.codiceVolo = codiceVolo;
        this.nomePasseggero = nomePasseggero;
        this.cognomePasseggero = cognomePasseggero;
        this.numeroBiglietto = numeroBiglietto;
        this.posto = posto;
        this.stato = stato;
        this.codicefiscalepasseggero = codicefiscalepasseggero;
    }

    /**
     * Crea una prenotazione specificando lo stato come intero (verrà convertito).
     * <p>La conversione usa {@link #fromInt(int)} con la mappatura: 1→IN_ATTESA, 2→CONFERMATA, 3→CANCELLATA.</p>
     *
     * @param numeroBiglietto         identificativo del biglietto
     * @param usernameUtente          username dell’utente (facoltativo)
     * @param codiceVolo              codice del volo
     * @param nomePasseggero          nome del passeggero
     * @param cognomePasseggero       cognome del passeggero
     * @param posto                   posto a sedere
     * @param stato                   stato della prenotazione come intero (1, 2, 3)
     * @param codicefiscalepasseggero codice fiscale del passeggero
     * @throws IllegalArgumentException se il codice stato non è supportato
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
     * Restituisce l'username dell’utente.
     *
     * @return String username (se presente)
     */
// --- Getter ---
    public String getUsernameUtente() { return usernameUtente; }

    /**
     * Restituisce il codice del volo prenotato.
     *
     * @return String codice volo
     */
    public String getCodiceVolo() { return codiceVolo; }

    /**
     * Restituisce il nome del passeggero.
     *
     * @return String nome passeggero
     */
    public String getNomePasseggero() { return nomePasseggero; }

    /**
     * Restituisce il cognome del passeggero.
     *
     * @return String cognome passeggero
     */
    public String getCognomePasseggero() { return cognomePasseggero; }

    /**
     * Restituisce il codice fiscale del passeggero.
     *
     * @return String codice fiscale
     */
    public String getCodicefiscalepasseggero() { return codicefiscalepasseggero; }

    /**
     * Restituisce il numero del biglietto.
     *
     * @return String numero biglietto
     */
    public String getNumeroBiglietto() { return numeroBiglietto; }

    /**
     * Restituisce il posto assegnato.
     *
     * @return String posto
     */
    public String getPosto() { return posto; }

    /**
     * @return {@code StatoPrenotazione} stato
     */
    public StatoPrenotazione getStato() { return stato; }

    /**
     * Restituisce il volo eventualmente associato.
     *
     * @return {@code Volo} volo associato, se presente
     */
    public Volo getVoloassociato() {
        return voloassociato;
    }

    /**
     * Imposta l'username dell’utente.
     *
     * @param usernameUtente username da impostare
     */
// --- Setter ---
    public void setUsernameUtente(String usernameUtente) { this.usernameUtente = usernameUtente; }

    /**
     * Imposta il codice del volo.
     *
     * @param codiceVolo codice volo da impostare
     */
    public void setCodiceVolo(String codiceVolo) { this.codiceVolo = codiceVolo; }

    /**
     * Imposta il nome del passeggero.
     *
     * @param nomePasseggero nome da impostare
     */
    public void setNomePasseggero(String nomePasseggero) { this.nomePasseggero = nomePasseggero; }

    /**
     * Imposta il cognome del passeggero.
     *
     * @param cognomePasseggero cognome da impostare
     */
    public void setCognomePasseggero(String cognomePasseggero) { this.cognomePasseggero = cognomePasseggero; }

    /**
     * Imposta il codice fiscale del passeggero.
     *
     * @param codicefiscalepasseggero codice fiscale da impostare
     */
    public void setCodicefiscalepasseggero(String codicefiscalepasseggero) { this.codicefiscalepasseggero = codicefiscalepasseggero; }

    /**
     * Imposta il numero del biglietto.
     *
     * @param numeroBiglietto numero biglietto da impostare
     */
    public void setNumeroBiglietto(String numeroBiglietto) { this.numeroBiglietto = numeroBiglietto; }

    /**
     * Imposta il posto assegnato.
     *
     * @param posto posto da impostare
     */
    public void setPosto(String posto) { this.posto = posto; }

    /**
     * Imposta lo stato della prenotazione.
     *
     * @param stato nuovo stato
     */
    public void setStato(StatoPrenotazione stato) { this.stato = stato; }

    /**
     * Imposta il volo associato alla prenotazione.
     *
     * @param voloassociato entità volo da associare
     */
    public void setVoloassociato(Volo voloassociato) { this.voloassociato = voloassociato; }


    /**
     * Indica se la prenotazione è confermata.
     *
     * @return boolean {@code true} se lo stato è CONFERMATA, altrimenti {@code false}
     */
// --- Metodi utili ---
    public boolean isConfermata() {
        return stato == StatoPrenotazione.CONFERMATA;
    }

    /**
     * Verifica se la prenotazione appartiene all’utente indicato.
     *
     * @param username username da verificare
     * @return boolean {@code true} se l’username coincide con quello della prenotazione (se presente)
     */
    public boolean appartieneUtente(String username) {
        return usernameUtente != null && usernameUtente.equals(username);
    }

    /**
     * Converte un codice numerico di stato nella corrispondente costante {@link StatoPrenotazione}.
     * <p>Mappatura: 1→IN_ATTESA, 2→CONFERMATA, 3→CANCELLATA.</p>
     *
     * @param stato codice numerico dello stato
     * @return {@code StatoPrenotazione} stato convertito
     * @throws IllegalArgumentException se il codice non è supportato
     */
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
