package model;

import model.enums.StatoVolo;

public class VoloArrivoANapoli extends Volo {
    private String aeroportoOrigine;
    private String aeroportoDestinazione = "Napoli";

    // Costruttore
    public VoloArrivoANapoli(String codiceVolo, String compagniaAerea, String dataVolo, String orarioPrevisto, String ritardo, StatoVolo stato, String aeroportoOrigine) {
        super(codiceVolo, compagniaAerea, dataVolo, orarioPrevisto, ritardo, stato);
        this.aeroportoOrigine = aeroportoOrigine;
    }

    public String getAeroportoOrigine() {
        return aeroportoOrigine;
    }

    public String getAeroportoDestinazione() {
        return aeroportoDestinazione;
    }
}