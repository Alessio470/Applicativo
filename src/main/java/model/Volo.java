package model;

import model.enums.StatoVolo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Volo {
    private String codice;
    private String compagnia;
    private String aeroportoOrigine;
    private String aeroportoDestinazione;
    private LocalDateTime dataOra;
    private int ritardoMinuti;
    private StatoVolo stato;
    private Gate gate; // null se non ancora assegnato
    private List<Prenotazione> prenotazioni;

    public Volo(String codice, String compagnia, String aeroportoOrigine, String aeroportoDestinazione,
                LocalDateTime dataOra, int ritardoMinuti, StatoVolo stato, Gate gate) {
        this.codice = codice;
        this.compagnia = compagnia;
        this.aeroportoOrigine = aeroportoOrigine;
        this.aeroportoDestinazione = aeroportoDestinazione;
        this.dataOra = dataOra;
        this.ritardoMinuti = ritardoMinuti;
        this.stato = stato;
        this.gate = gate;
        this.prenotazioni = new ArrayList<>();
    }

    // --- Getter e Setter ---
    public String getCodice() { return codice; }
    public String getCompagnia() { return compagnia; }
    public String getAeroportoOrigine() { return aeroportoOrigine; }
    public String getAeroportoDestinazione() { return aeroportoDestinazione; }
    public LocalDateTime getDataOra() { return dataOra; }
    public int getRitardoMinuti() { return ritardoMinuti; }
    public StatoVolo getStato() { return stato; }
    public Gate getGate() { return gate; }
    public List<Prenotazione> getPrenotazioni() { return prenotazioni; }

    public void setCodice(String codice) { this.codice = codice; }
    public void setCompagnia(String compagnia) { this.compagnia = compagnia; }
    public void setAeroportoOrigine(String aeroportoOrigine) { this.aeroportoOrigine = aeroportoOrigine; }
    public void setAeroportoDestinazione(String aeroportoDestinazione) { this.aeroportoDestinazione = aeroportoDestinazione; }
    public void setDataOra(LocalDateTime dataOra) { this.dataOra = dataOra; }
    public void setRitardoMinuti(int ritardoMinuti) { this.ritardoMinuti = ritardoMinuti; }
    public void setStato(StatoVolo stato) { this.stato = stato; }
    public void setGate(Gate gate) { this.gate = gate; }

    // --- Metodi utili ---
    public void aggiungiPrenotazione(Prenotazione p) { prenotazioni.add(p); }
    public void rimuoviPrenotazione(Prenotazione p) { prenotazioni.remove(p); }

    public List<Prenotazione> getPrenotazioniConfermate() {
        List<Prenotazione> confermate = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            if (p.getStato() == model.enums.StatoPrenotazione.CONFERMATA) {
                confermate.add(p);
            }
        }
        return confermate;
    }

    // Helper per distinguere arrivi e partenze
    public boolean isPartenzaDaNapoli() { return "Napoli".equalsIgnoreCase(aeroportoOrigine); }
    public boolean isArrivoANapoli() { return "Napoli".equalsIgnoreCase(aeroportoDestinazione); }

    @Override
    public String toString() {
        return "Volo{" +
                "codice='" + codice + '\'' +
                ", compagnia='" + compagnia + '\'' +
                ", da='" + aeroportoOrigine + '\'' +
                ", a='" + aeroportoDestinazione + '\'' +
                ", dataOra=" + dataOra +
                ", ritardo=" + ritardoMinuti + " min" +
                ", stato=" + stato +
                ", gate=" + (gate != null ? gate.getNumero() : "-") +
                ", prenotazioni=" + prenotazioni.size() +
                '}';
    }

    public void setNumeroGate(Gate nuovoGate) {
        this.gate = nuovoGate;
    }
}
