package model.enums;

/**
 * The enum Stato bagaglio.
 */
public enum StatoBagaglio {
    /**
     * Registrato stato bagaglio.
     */
    REGISTRATO,    // appena registrato al check-in
    /**
     * Caricato stato bagaglio.
     */
    CARICATO,      // caricato sull'aereo
    /**
     * Disponibile stato bagaglio.
     */
    DISPONIBILE,   // pronto per il ritiro a destinazione
    /**
     * The Smarrito.
     */
    SMARRITO       // segnalato come smarrito
}
