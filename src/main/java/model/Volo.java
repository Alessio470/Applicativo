package model;

import model.enums.StatoVolo;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * The type Volo.
 */
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


    /**
     * Instantiates a new Volo.
     *
     * @param codicev               the codicev
     * @param compagnia             the compagnia
     * @param aeroportoOrigine      the aeroporto origine
     * @param aeroportoDestinazione the aeroporto destinazione
     * @param data                  the data
     * @param orario                the orario
     * @param ritardoMinuti         the ritardo minuti
     * @param stato                 the stato
     * @param gate                  the gate
     */
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

    /**
     * Instantiates a new Volo.
     *
     * @param codicev               the codicev
     * @param compagnia             the compagnia
     * @param aeroportoOrigine      the aeroporto origine
     * @param aeroportoDestinazione the aeroporto destinazione
     * @param data                  the data
     * @param orario                the orario
     * @param ritardoMinuti         the ritardo minuti
     * @param stato                 the stato
     * @param gate                  the gate
     */
    public Volo(String codicev, String compagnia, String aeroportoOrigine, String aeroportoDestinazione, String data, String orario, int ritardoMinuti, int stato, String gate) {
        this.codicevolo = codicev;
        this.compagnia = compagnia;
        this.aeroportoOrigine = aeroportoOrigine;
        this.aeroportoDestinazione = aeroportoDestinazione;
        this.data = data;
        this.orario = orario;
        this.ritardoMinuti = ritardoMinuti;
        this.stato = fromInt(stato);
        this.gate = gate;
    }


    /**
     * Gets codice v.
     *
     * @return the codice v
     */
// --- Getter e Setter ---
    public String getCodiceV() { return codicevolo; }

    /**
     * Gets compagnia.
     *
     * @return the compagnia
     */
    public String getCompagnia() { return compagnia; }

    /**
     * Gets aeroporto origine.
     *
     * @return the aeroporto origine
     */
    public String getAeroportoOrigine() { return aeroportoOrigine; }

    /**
     * Gets aeroporto destinazione.
     *
     * @return the aeroporto destinazione
     */
    public String getAeroportoDestinazione() { return aeroportoDestinazione; }

    /**
     * Gets data str.
     *
     * @return the data str
     */
    public String getDataStr() { return data; }

    /**
     * Gets orario str.
     *
     * @return the orario str
     */
    public String getOrarioStr() { return orario; }

    /**
     * Gets ritardo minuti.
     *
     * @return the ritardo minuti
     */
    public int getRitardoMinuti() { return ritardoMinuti; }

    /**
     * Gets stato.
     *
     * @return the stato
     */
    public StatoVolo getStato() { return stato; }

    /**
     * Gets gate.
     *
     * @return the gate
     */
    public String getGate() { return gate; }

    /**
     * Sets codice.
     *
     * @param codice the codice
     */
    public void setCodice(String codice) { this.codicevolo = codice; }

    /**
     * Sets compagnia.
     *
     * @param compagnia the compagnia
     */
    public void setCompagnia(String compagnia) { this.compagnia = compagnia; }

    /**
     * Sets aeroporto origine.
     *
     * @param aeroportoOrigine the aeroporto origine
     */
    public void setAeroportoOrigine(String aeroportoOrigine) { this.aeroportoOrigine = aeroportoOrigine; }

    /**
     * Sets aeroporto destinazione.
     *
     * @param aeroportoDestinazione the aeroporto destinazione
     */
    public void setAeroportoDestinazione(String aeroportoDestinazione) { this.aeroportoDestinazione = aeroportoDestinazione; }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(String data) { this.data = data; }

    /**
     * Sets orario.
     *
     * @param orario the orario
     */
    public void setOrario(String orario) { this.orario = orario; }

    /**
     * Sets ritardo minuti.
     *
     * @param ritardoMinuti the ritardo minuti
     */
    public void setRitardoMinuti(int ritardoMinuti) { this.ritardoMinuti = ritardoMinuti; }

    /**
     * Sets stato.
     *
     * @param stato the stato
     */
    public void setStato(StatoVolo stato) { this.stato = stato; }

    /**
     * Sets gate.
     *
     * @param gate the gate
     */
    public void setGate(String gate) { this.gate = gate; }


    /**
     * Is partenza da napoli boolean.
     *
     * @return the boolean
     */
// Helper per distinguere arrivi e partenze
    public boolean isPartenzaDaNapoli() { return "Napoli".equalsIgnoreCase(aeroportoOrigine); }

    /**
     * Is arrivo a napoli boolean.
     *
     * @return the boolean
     */
    public boolean isArrivoANapoli() { return "Napoli".equalsIgnoreCase(aeroportoDestinazione); }

    //Things

    /**
     * Gets orario local time.
     *
     * @return the orario local time
     */
    public LocalTime getOrarioLocalTime() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(orario, formatter);
        } catch (DateTimeParseException e) {
            return null; // segnala errore
        }
    }

    /**
     * Gets orario sql.
     *
     * @return the orario sql
     */
    public Time getOrarioSql() {
        return Time.valueOf(Objects.requireNonNull(this.getOrarioLocalTime()));
    }


    /**
     * Gets data date.
     *
     * @return the data date
     */
    public java.util.Date getDataDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(data);
        } catch (ParseException e) {
            return null; // segnala errore
        }
    }


    /**
     * Gets datasql.
     *
     * @return the datasql
     */
    public java.sql.Date getDatasql() {
        return new java.sql.Date(this.getDataDate().getTime());
    }

    /**
     * Gets stato to int.
     *
     * @return the stato to int
     */
    public int getStatoToInt() {
        return stato.ordinal();
    }

    /**
     * From int stato volo.
     *
     * @param codice the codice
     * @return the stato volo
     */
    public static StatoVolo fromInt(int codice) {
        return switch (codice) {
            case 1 -> StatoVolo.PROGRAMMATO;
            case 2 -> StatoVolo.DECOLLATO;
            case 3 -> StatoVolo.IN_RITARDO;
            case 4 -> StatoVolo.ATTERRATO;
            case 5 -> StatoVolo.CANCELLATO;
            default -> throw new IllegalArgumentException("Codice stato volo sconosciuto: " + codice);
        };
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

    /**
     * Sets numero gate.
     *
     * @param nuovoGate the nuovo gate
     */
//TODO da modificare che invece di farlo localmente lo fa al db (forse)
    public void setNumeroGate(String nuovoGate) {
        this.gate = nuovoGate;
    }


}
