package model;

import model.enums.StatoBagaglio;

/**
 * Rappresenta un bagaglio nel sistema.
 * <p>Include identificativo, proprietario, volo (eventuale) e stato corrente.
 *
 * @see Passeggero
 * @see Volo
 * @see StatoBagaglio
 */
public class Bagaglio {
    /** Identificativo univoco del bagaglio.*/
    private String codice;

    /** Passeggero proprietario del bagaglio. */
    private Passeggero passeggero;

    /** Volo associato al bagaglio. */
    private Volo volo;

    /** Stato operativo del bagaglio. */
    private StatoBagaglio stato;

    /**
     * Crea un bagaglio con i dati forniti.
     *
     * @param codice identificativo univoco del bagaglio
     * @param passeggero proprietario del bagaglio
     * @param volo volo associato
     * @param stato stato operativo del bagaglio
     */
    public Bagaglio(String codice, Passeggero passeggero, Volo volo, StatoBagaglio stato) {
        this.codice = codice;
        this.passeggero = passeggero;
        this.volo = volo;
        this.stato = stato;
    }

    // --- Getter ---

    /**
     * Restituisce il codice del bagaglio.
     *
     * @return codice del bagaglio
     */
    public String getCodice() { return codice; }

    /**
     * Restituisce il passeggero proprietario.
     *
     * @return passeggero proprietario
     */
    public Passeggero getPasseggero() { return passeggero; }

    /**
     * Restituisce il volo associato.
     *
     * @return volo associato
     */
    public Volo getVolo() { return volo; }

    /**
     * Restituisce lo stato del bagaglio.
     *
     * @return stato del bagaglio
     */
    public StatoBagaglio getStato() { return stato; }

    // --- Setter ---

    /**
     * Imposta il codice del bagaglio.
     *
     * @param codice nuovo codice
     */
    public void setCodice(String codice) { this.codice = codice; }

    /**
     * Imposta il passeggero proprietario.
     *
     * @param passeggero nuovo proprietario
     */
    public void setPasseggero(Passeggero passeggero) { this.passeggero = passeggero; }

    /**
     * Imposta il volo associato.
     *
     * @param volo nuovo volo;
     */
    public void setVolo(Volo volo) { this.volo = volo; }

    /**
     * Imposta lo stato del bagaglio.
     *
     * @param stato nuovo stato
     */
    public void setStato(StatoBagaglio stato) { this.stato = stato; }

    // --- toString ---

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
