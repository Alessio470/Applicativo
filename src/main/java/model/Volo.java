package model;

import model.enums.StatoVolo;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Volo {

    private String codicevolo;
    private String compagnia;
    private String aeroportoOrigine;
    private String aeroportoDestinazione;
    private String data;
    private String orario;
    private int ritardoMinuti;
    private StatoVolo stato;
    private String gate; // null se non ancora assegnato

    public Volo(String codicev, String compagnia, String aeroportoOrigine, String aeroportoDestinazione, String data, String orario, int ritardoMinuti, StatoVolo stato, String gate) {
        this.codicevolo = codicev;
        this.compagnia = compagnia;
        this.aeroportoOrigine = aeroportoOrigine;
        this.aeroportoDestinazione = aeroportoDestinazione;
        this.data = data;
        this.orario = orario;
        this.ritardoMinuti = ritardoMinuti;
        this.stato = stato;
        this.gate = gate;
    }

    // --- Getter e Setter ---
    public String getCodiceV() { return codicevolo; }
    public String getCompagnia() { return compagnia; }
    public String getAeroportoOrigine() { return aeroportoOrigine; }
    public String getAeroportoDestinazione() { return aeroportoDestinazione; }
    public String getDataStr() { return data; }
    public String getOrarioStr() { return orario; }
    public int getRitardoMinuti() { return ritardoMinuti; }
    public StatoVolo getStato() { return stato; }
    public String getGate() { return gate; }

    public void setCodice(String codice) { this.codicevolo = codice; }
    public void setCompagnia(String compagnia) { this.compagnia = compagnia; }
    public void setAeroportoOrigine(String aeroportoOrigine) { this.aeroportoOrigine = aeroportoOrigine; }
    public void setAeroportoDestinazione(String aeroportoDestinazione) { this.aeroportoDestinazione = aeroportoDestinazione; }
    public void setData(String data) { this.data = data; }
    public void setOrario(String orario) { this.orario = orario; }
    public void setRitardoMinuti(int ritardoMinuti) { this.ritardoMinuti = ritardoMinuti; }
    public void setStato(StatoVolo stato) { this.stato = stato; }
    public void setGate(String gate) { this.gate = gate; }


    // Helper per distinguere arrivi e partenze
    public boolean isPartenzaDaNapoli() { return "Napoli".equalsIgnoreCase(aeroportoOrigine); }
    public boolean isArrivoANapoli() { return "Napoli".equalsIgnoreCase(aeroportoDestinazione); }

    //Things

    public LocalTime getOrarioLocalTime() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(orario, formatter);
        } catch (DateTimeParseException e) {
            return null; // segnala errore
        }
    }

    public Time getOrarioSql() {
        return Time.valueOf(Objects.requireNonNull(this.getOrarioLocalTime()));
    }



    public java.util.Date getDataDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(data);
        } catch (ParseException e) {
            return null; // segnala errore
        }
    }


    public java.sql.Date getDatasql() {
        return new java.sql.Date(this.getDataDate().getTime());
    }

    public int getStatoToInt() {
        return stato.ordinal();
    }



    @Override
    public String toString() {
        return "Volo{" +
                "codice='" + this.codicevolo + '\'' +
                ", compagnia='" + this.compagnia + '\'' +
                ", da='" + this.aeroportoOrigine + '\'' +
                ", a='" + this.aeroportoDestinazione + '\'' +
                ", data=" + this.data +
                ", orario=" + this.orario +
                ", ritardo=" + this.ritardoMinuti + " min" +
                ", stato=" + this.stato +
                ", gate=" + this.gate +
                '}';
    }

    //TODO da modificare che invece di farlo localmente lo fa al db
    public void setNumeroGate(String nuovoGate) {
        this.gate = nuovoGate;
    }


}
