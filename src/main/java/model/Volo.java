package model;

import model.enums.StatoVolo;
import java.time.LocalDateTime;

public class Volo {
    private String codice;
    private String compagnia;
    private String aeroportoOrigine;
    private String aeroportoDestinazione;
    private LocalDateTime dataOra;
    private int ritardoMinuti;
    private StatoVolo stato;
    private Integer numeroGate; // nullable se non ancora assegnato

    public Volo(String codice, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                LocalDateTime dataOra, int ritardoMinuti, StatoVolo stato, Integer numeroGate) {
        this.codice = codice;
        this.compagnia = compagnia;
        this.aeroportoOrigine = aeroportoOrigine;
        this.aeroportoDestinazione = aeroportoDestinazione;
        this.dataOra = dataOra;
        this.ritardoMinuti = ritardoMinuti;
        this.stato = stato;
        this.numeroGate = numeroGate;
    }

    public String getCodice() { return codice; }
    public String getCompagnia() { return compagnia; }
    public String getAeroportoOrigine() { return aeroportoOrigine; }
    public String getAeroportoDestinazione() { return aeroportoDestinazione; }
    public LocalDateTime getDataOra() { return dataOra; }
    public int getRitardoMinuti() { return ritardoMinuti; }
    public StatoVolo getStato() { return stato; }
    public Integer getNumeroGate() { return numeroGate; }

    public void setCodice(String codice) { this.codice = codice; }
    public void setCompagnia(String compagnia) { this.compagnia = compagnia; }
    public void setAeroportoOrigine(String aeroportoOrigine) { this.aeroportoOrigine = aeroportoOrigine; }
    public void setAeroportoDestinazione(String aeroportoDestinazione) { this.aeroportoDestinazione = aeroportoDestinazione; }
    public void setDataOra(LocalDateTime dataOra) { this.dataOra = dataOra; }
    public void setRitardoMinuti(int ritardoMinuti) { this.ritardoMinuti = ritardoMinuti; }
    public void setStato(StatoVolo stato) { this.stato = stato; }
    public void setNumeroGate(Integer numeroGate) { this.numeroGate = numeroGate; }

    @Override
    public String toString() {
        return "Volo{codice='" + codice + "', stato=" + stato + ", gate=" + numeroGate + '}';
    }
}
