// VoloPartenzaDaNapoli.java
package model;

import model.enums.StatoVolo;

/**
 * The type Volo partenza da napoli.
 */
public class VoloPartenzaDaNapoli extends Volo {
    private String aeroportoOrigine = "Napoli";
    private String aeroportoDestinazione;
    private Gate gate;

    /**
     * Instantiates a new Volo partenza da napoli.
     *
     * @param codiceVolo            the codice volo
     * @param compagniaAerea        the compagnia aerea
     * @param dataVolo              the data volo
     * @param orarioPrevisto        the orario previsto
     * @param ritardo               the ritardo
     * @param stato                 the stato
     * @param aeroportoDestinazione the aeroporto destinazione
     */
// Costruttore
    public VoloPartenzaDaNapoli(String codiceVolo, String compagniaAerea, String dataVolo, String orarioPrevisto, String ritardo, StatoVolo stato, String aeroportoDestinazione) {
        super(codiceVolo, compagniaAerea, dataVolo, orarioPrevisto, ritardo, stato);
        this.aeroportoDestinazione = aeroportoDestinazione;
    }

    /**
     * Sets gate.
     *
     * @param gate the gate
     */
// Metodo per modificare il gate di imbarco del volo
    public void setGate(Gate gate) {
        this.gate = gate;
    }

    /**
     * Gets aeroporto origine.
     *
     * @return the aeroporto origine
     */
    public String getAeroportoOrigine() {
        return aeroportoOrigine;
    }

    /**
     * Gets aeroporto destinazione.
     *
     * @return the aeroporto destinazione
     */
    public String getAeroportoDestinazione() {
        return aeroportoDestinazione;
    }

    /**
     * Gets gate.
     *
     * @return the gate
     */
    public Gate getGate() {
        return gate;
    }

    /**
     * Gets numero gate.
     *
     * @return the numero gate
     */
    public String getNumeroGate() {
        return (gate != null) ? gate.getNumeroGate() : null;
    }

}