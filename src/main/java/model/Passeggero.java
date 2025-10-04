package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Passeggero.
 */
public class Passeggero {
    private String nome;
    private String cognome;
    private String codiceFiscale;


    /**
     * Instantiates a new Passeggero.
     *
     * @param nome          the nome
     * @param cognome       the cognome
     * @param codiceFiscale the codice fiscale
     */
    public Passeggero(String nome, String cognome, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;

    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
// --- Getter ---
    public String getNome() { return nome; }

    /**
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() { return cognome; }

    /**
     * Gets codice fiscale.
     *
     * @return the codice fiscale
     */
    public String getCodiceFiscale() { return codiceFiscale; }


    // --- Metodi utili ---

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + codiceFiscale + ")";
    }
}
