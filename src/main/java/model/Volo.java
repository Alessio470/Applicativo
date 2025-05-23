package model;

import model.enums.StatoVolo;

public abstract class Volo {
    protected String codiceVolo;
    protected String compagniaAerea;
    protected String dataVolo;
    protected String orarioPrevisto;
    protected String ritardo;
    protected StatoVolo stato;

    // Costruttore
    public Volo(String codiceVolo, String compagniaAerea, String dataVolo, String orarioPrevisto, String ritardo, StatoVolo stato) {
        this.codiceVolo = codiceVolo;
        this.compagniaAerea = compagniaAerea;
        this.dataVolo = dataVolo;
        this.orarioPrevisto = orarioPrevisto;
        this.ritardo = ritardo;
        this.stato = stato;
    }

    public String getCodiceVolo() {
        return codiceVolo;
    }
}