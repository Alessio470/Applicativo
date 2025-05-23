// VoloPartenzaDaNapoli.java
package model;

import model.enums.StatoVolo;

public class VoloPartenzaDaNapoli extends Volo {
    private String aeroportoOrigine = "Napoli";
    private String aeroportoDestinazione;
    private Gate gate;

    // Costruttore
    public VoloPartenzaDaNapoli(String codiceVolo, String compagniaAerea, String dataVolo, String orarioPrevisto, String ritardo, StatoVolo stato, String aeroportoDestinazione) {
        super(codiceVolo, compagniaAerea, dataVolo, orarioPrevisto, ritardo, stato);
        this.aeroportoDestinazione = aeroportoDestinazione;
    }
    // Metodo per modificare il gate di imbarco del volo
    public void setGate(Gate gate) {
        this.gate = gate;
    }
}