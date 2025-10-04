package model;

import model.enums.StatoBagaglio;

/**
 * The type Bagaglio.
 */
public class Bagaglio {
    private String codice;          // identificativo univoco del bagaglio
    private Passeggero passeggero;  // passeggero a cui appartiene
    private Volo volo;              // volo associato
    private StatoBagaglio stato;    // stato attuale del bagaglio

    /**
     * Instantiates a new Bagaglio.
     *
     * @param codice     the codice
     * @param passeggero the passeggero
     * @param volo       the volo
     * @param stato      the stato
     */
    public Bagaglio(String codice, Passeggero passeggero, Volo volo, StatoBagaglio stato) {
        this.codice = codice;
        this.passeggero = passeggero;
        this.volo = volo;
        this.stato = stato;
    }

    /**
     * Gets codice.
     *
     * @return the codice
     */
// --- Getter ---
    public String getCodice() { return codice; }

    /**
     * Gets passeggero.
     *
     * @return the passeggero
     */
    public Passeggero getPasseggero() { return passeggero; }

    /**
     * Gets volo.
     *
     * @return the volo
     */
    public Volo getVolo() { return volo; }

    /**
     * Gets stato.
     *
     * @return the stato
     */
    public StatoBagaglio getStato() { return stato; }

    /**
     * Sets codice.
     *
     * @param codice the codice
     */
// --- Setter ---
    public void setCodice(String codice) { this.codice = codice; }

    /**
     * Sets passeggero.
     *
     * @param passeggero the passeggero
     */
    public void setPasseggero(Passeggero passeggero) { this.passeggero = passeggero; }

    /**
     * Sets volo.
     *
     * @param volo the volo
     */
    public void setVolo(Volo volo) { this.volo = volo; }

    /**
     * Sets stato.
     *
     * @param stato the stato
     */
    public void setStato(StatoBagaglio stato) { this.stato = stato; }

    @Override
    public String toString() {
        return "Bagaglio{" +
                "codice='" + codice + '\'' +
                ", passeggero=" + passeggero +
                ", volo=" + (volo != null ? volo.getCodiceV() : "N/A") +
                ", stato=" + stato +
                '}';
    }
}
