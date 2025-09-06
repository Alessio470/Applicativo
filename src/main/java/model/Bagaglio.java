package model;

import model.enums.StatoBagaglio;

public class Bagaglio {
    private String codice;          // identificativo univoco del bagaglio
    private Passeggero passeggero;  // passeggero a cui appartiene
    private Volo volo;              // volo associato
    private StatoBagaglio stato;    // stato attuale del bagaglio

    public Bagaglio(String codice, Passeggero passeggero, Volo volo, StatoBagaglio stato) {
        this.codice = codice;
        this.passeggero = passeggero;
        this.volo = volo;
        this.stato = stato;
    }

    // --- Getter ---
    public String getCodice() { return codice; }
    public Passeggero getPasseggero() { return passeggero; }
    public Volo getVolo() { return volo; }
    public StatoBagaglio getStato() { return stato; }

    // --- Setter ---
    public void setCodice(String codice) { this.codice = codice; }
    public void setPasseggero(Passeggero passeggero) { this.passeggero = passeggero; }
    public void setVolo(Volo volo) { this.volo = volo; }
    public void setStato(StatoBagaglio stato) { this.stato = stato; }

    @Override
    public String toString() {
        return "Bagaglio{" +
                "codice='" + codice + '\'' +
                ", passeggero=" + passeggero +
                ", volo=" + (volo != null ? volo.getCodice() : "N/A") +
                ", stato=" + stato +
                '}';
    }
}
