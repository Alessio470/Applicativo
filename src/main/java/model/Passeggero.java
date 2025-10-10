package model;


/**
 * Rappresenta un passeggero.
 * <p>Include nome, cognome e codice fiscale.
 */
public class Passeggero {

    /** Nome del passeggero. */
    private String nome;

    /** Cognome del passeggero. */
    private String cognome;

    /** Codice fiscale del passeggero. */
    private String codiceFiscale;

    /**
     * Crea un passeggero con i dati forniti.
     *
     * @param nome nome del passeggero
     * @param cognome cognome del passeggero
     * @param codiceFiscale codice fiscale del passeggero
     */
    public Passeggero(String nome, String cognome, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;

    }

    // --- Getter ---

    /**
     * Restituisce il nome del passeggero.
     *
     * @return nome
     */
    public String getNome() { return nome; }

    /**
     * Restituisce il cognome del passeggero.
     *
     * @return cognome
     */
    public String getCognome() { return cognome; }

    /**
     * Restituisce il codice fiscale del passeggero.
     *
     * @return codice fiscale
     */
    public String getCodiceFiscale() { return codiceFiscale; }

    // --- toString ---

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + codiceFiscale + ")";
    }
}
