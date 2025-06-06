package model;

import model.enums.StatoVolo;

/**
 * The type Volo arrivo a napoli.
 */
public class VoloArrivoANapoli extends Volo {
    private String aeroportoOrigine;
    private String aeroportoDestinazione = "Napoli";

    /**
     * Instantiates a new Volo arrivo a napoli.
     *
     * @param codiceVolo       the codice volo
     * @param compagniaAerea   the compagnia aerea
     * @param dataVolo         the data volo
     * @param orarioPrevisto   the orario previsto
     * @param ritardo          the ritardo
     * @param stato            the stato
     * @param aeroportoOrigine the aeroporto origine
     */
// Costruttore
    public VoloArrivoANapoli(String codiceVolo, String compagniaAerea, String dataVolo, String orarioPrevisto, String ritardo, StatoVolo stato, String aeroportoOrigine) {
        super(codiceVolo, compagniaAerea, dataVolo, orarioPrevisto, ritardo, stato);
        this.aeroportoOrigine = aeroportoOrigine;
    }

    /**
     * Gets aeroporto origine.
     *
     * @return the aeroporto origine
     */
    public String getAeroportoOrigine() {
        return aeroportoOrigine;
    }

}