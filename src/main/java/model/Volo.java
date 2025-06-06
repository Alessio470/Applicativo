package model;

import model.enums.StatoVolo;

/**
 * The type Volo.
 */
public abstract class Volo {
    /**
     * The Codice volo.
     */
    protected String codiceVolo;
    /**
     * The Compagnia aerea.
     */
    protected String compagniaAerea;
    /**
     * The Data volo.
     */
    protected String dataVolo;
    /**
     * The Orario previsto.
     */
    protected String orarioPrevisto;
    /**
     * The Ritardo.
     */
    protected String ritardo;
    /**
     * The Stato.
     */
    protected StatoVolo stato;

    /**
     * Instantiates a new Volo.
     *
     * @param codiceVolo     the codice volo
     * @param compagniaAerea the compagnia aerea
     * @param dataVolo       the data volo
     * @param orarioPrevisto the orario previsto
     * @param ritardo        the ritardo
     * @param stato          the stato
     */
// Costruttore
    public Volo(String codiceVolo, String compagniaAerea, String dataVolo, String orarioPrevisto, String ritardo, StatoVolo stato) {
        this.codiceVolo = codiceVolo;//
        this.compagniaAerea = compagniaAerea;//
        this.dataVolo = dataVolo;//
        this.orarioPrevisto = orarioPrevisto;//
        this.ritardo = ritardo;
        this.stato = stato;//
    }


    /**
     * Gets compagnia aerea.
     *
     * @return the compagnia aerea
     */
    public String getCompagniaAerea() { return compagniaAerea; }

    /**
     * Gets data volo.
     *
     * @return the data volo
     */
    public String getDataVolo() { return dataVolo; }

    /**
     * Gets orario previsto.
     *
     * @return the orario previsto
     */
    public String getOrarioPrevisto() { return orarioPrevisto; }

    /**
     * Gets ritardo.
     *
     * @return the ritardo
     */
    public String getRitardo() { return ritardo; }

    /**
     * Gets stato.
     *
     * @return the stato
     */
    public StatoVolo getStato() { return stato; }

    /**
     * Gets codice volo.
     *
     * @return the codice volo
     */
    public String getCodiceVolo() {
        return codiceVolo;
    }

}