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
 * Rappresenta un volo.
 * <p>Comprende codice, compagnia, aeroporti di origine/destinazione, data, orario,
 * ritardo in minuti, stato operativo e gate (facoltativo).</p>
 */
public class Volo {

    /** Codice del volo (es. {@code CV123}). */
    private String codicevolo;
    /** Compagnia aerea. */
    private String compagnia;
    /** Aeroporto di partenza. */
    private String aeroportoOrigine;
    /** Aeroporto di arrivo. */
    private String aeroportoDestinazione;
    /** Data del volo in formato {@code dd/MM/yyyy}. */
    private String data;
    /** Orario previsto in formato {@code HH:mm}. */
    private String orario;
    /** Ritardo espresso in minuti (può essere 0). */
    private int ritardoMinuti;
    /** Stato operativo del volo. */
    private StatoVolo stato = null;
    /** Numero/etichetta del gate assegnato (campo facoltativo). */
    private String gate; // null se non ancora assegnato


    /**
     * Crea un volo specificando lo stato come enum.
     *
     * @param codicev               codice del volo
     * @param compagnia             compagnia aerea
     * @param aeroportoOrigine      aeroporto di partenza
     * @param aeroportoDestinazione aeroporto di arrivo
     * @param data                  data in formato {@code dd/MM/yyyy}
     * @param orario                orario in formato {@code HH:mm}
     * @param ritardoMinuti         ritardo in minuti
     * @param stato                 stato operativo
     * @param gate                  gate assegnato (facoltativo)
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
     * Crea un volo specificando lo stato come intero.
     * <p>La conversione segue {@link #fromInt(int)}.</p>
     *
     * @param codicev               codice del volo
     * @param compagnia             compagnia aerea
     * @param aeroportoOrigine      aeroporto di partenza
     * @param aeroportoDestinazione aeroporto di arrivo
     * @param data                  data in formato {@code dd/MM/yyyy}
     * @param orario                orario in formato {@code HH:mm}
     * @param ritardoMinuti         ritardo in minuti
     * @param stato                 codice numerico dello stato
     * @param gate                  gate assegnato (facoltativo)
     * @throws IllegalArgumentException se il codice di stato non è supportato
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


    // --- Getter e Setter ---

    /**
     * Restituisce il codice del volo.
     *
     * @return String codice volo
     */
    public String getCodiceV() { return codicevolo; }

    /**
     * Restituisce la compagnia aerea.
     *
     * @return String compagnia
     */
    public String getCompagnia() { return compagnia; }

    /**
     * Restituisce l’aeroporto di partenza.
     *
     * @return String aeroporto di origine
     */
    public String getAeroportoOrigine() { return aeroportoOrigine; }

    /**
     * Restituisce l’aeroporto di arrivo.
     *
     * @return String aeroporto di destinazione
     */
    public String getAeroportoDestinazione() { return aeroportoDestinazione; }

    /**
     * Restituisce la data in formato stringa {@code dd/MM/yyyy}.
     *
     * @return String data del volo
     */
    public String getDataStr() { return data; }

    /**
     * Restituisce l’orario in formato stringa {@code HH:mm}.
     *
     * @return String orario previsto
     */
    public String getOrarioStr() { return orario; }

    /**
     * Restituisce il ritardo espresso in minuti.
     *
     * @return int ritardo in minuti
     */
    public int getRitardoMinuti() { return ritardoMinuti; }

    /**
     * Restituisce lo stato operativo del volo.
     *
     * @return {@code StatoVolo} stato
     */
    public StatoVolo getStato() { return stato; }

    /**
     * Restituisce il gate assegnato.
     *
     * @return String gate (se presente)
     */
    public String getGate() { return gate; }

    /**
     * Imposta il codice del volo.
     *
     * @param codice nuovo codice
     */
    public void setCodice(String codice) { this.codicevolo = codice; }

    /**
     * Imposta la compagnia aerea.
     *
     * @param compagnia nuova compagnia
     */
    public void setCompagnia(String compagnia) { this.compagnia = compagnia; }

    /**
     * Imposta l’aeroporto di partenza.
     *
     * @param aeroportoOrigine nuovo aeroporto di origine
     */
    public void setAeroportoOrigine(String aeroportoOrigine) { this.aeroportoOrigine = aeroportoOrigine; }

    /**
     * Imposta l’aeroporto di arrivo.
     *
     * @param aeroportoDestinazione nuovo aeroporto di destinazione
     */
    public void setAeroportoDestinazione(String aeroportoDestinazione) { this.aeroportoDestinazione = aeroportoDestinazione; }

    /**
     * Imposta la data del volo (formato {@code dd/MM/yyyy}).
     *
     * @param data nuova data
     */
    public void setData(String data) { this.data = data; }

    /**
     * Imposta l’orario previsto (formato {@code HH:mm}).
     *
     * @param orario nuovo orario
     */
    public void setOrario(String orario) { this.orario = orario; }

    /**
     * Imposta il ritardo in minuti.
     *
     * @param ritardoMinuti nuovo ritardo
     */
    public void setRitardoMinuti(int ritardoMinuti) { this.ritardoMinuti = ritardoMinuti; }

    /**
     * Imposta lo stato del volo.
     *
     * @param stato nuovo stato
     */
    public void setStato(StatoVolo stato) { this.stato = stato; }

    /**
     * Imposta il gate assegnato.
     *
     * @param gate nuovo gate
     */
    public void setGate(String gate) { this.gate = gate; }


    // --- Helper per arrivi/partenze ---

    /**
     * Indica se il volo parte da Napoli.
     *
     * @return boolean {@code true} se l’aeroporto di origine è “Napoli”
     */
// Helper per distinguere arrivi e partenze
    public boolean isPartenzaDaNapoli() { return "Napoli".equalsIgnoreCase(aeroportoOrigine); }

    /**
     * Indica se il volo arriva a Napoli.
     *
     * @return boolean {@code true} se l’aeroporto di destinazione è “Napoli”
     */
    public boolean isArrivoANapoli() { return "Napoli".equalsIgnoreCase(aeroportoDestinazione); }

    //Things

    /**
     * Converte l’orario in {@link LocalTime} usando il formato {@code HH:mm}.
     *
     * @return {@code LocalTime} l’orario convertito; se non interpretabile, valore mancante
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
     * Converte l’orario in {@link java.sql.Time}.
     *
     * @return {@code Time} l’orario in formato SQL
     * @throws NullPointerException se l’orario non è interpretabile
     */
    public Time getOrarioSql() {
        if (this.orario == null || this.orario.isEmpty()) {
            return null;
        }
        return Time.valueOf(Objects.requireNonNull(this.getOrarioLocalTime()));
    }


    /**
     * Converte la data in {@link java.util.Date} usando il formato {@code dd/MM/yyyy}.
     *
     * @return {@code java.util.Date} la data convertita; se non interpretabile, valore mancante
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
     * Converte la data in {@link java.sql.Date}.
     *
     * @return {@code java.sql.Date} la data in formato SQL
     * @throws NullPointerException se la data non è interpretabile
     */
    public java.sql.Date getDatasql() {
        if (this.data == null || this.data.isEmpty()) {
            return null;
        }
        return new java.sql.Date(this.getDataDate().getTime());
    }

    /**
     * Restituisce l’indice dello stato (ordinal) a partire da 0.
     *
     * @return int intero corrispondente alla posizione dell’enum
     */
    public int getStatoToInt() {
        if (this.stato == null) {
            return -1;
        }
        return stato.ordinal();
    }

    /**
     * Converte un intero nello {@link StatoVolo} corrispondente.
     * <p>Mappatura: 1→PROGRAMMATO, 2→DECOLLATO, 3→IN_RITARDO, 4→ATTERRATO, 5→CANCELLATO.</p>
     *
     * @param codice codice numerico dello stato
     * @return {@code StatoVolo} stato del volo
     * @throws IllegalArgumentException se il codice non è supportato
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
     * Imposta il numero/etichetta del gate.
     *
     *
     * @param nuovoGate valore da impostare
     */

    public void setNumeroGate(String nuovoGate) {
        this.gate = nuovoGate;
    }


}
