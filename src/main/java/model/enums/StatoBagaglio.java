package model.enums;

/**
 * Stati operativi del bagaglio nel ciclo di gestione.
 * <p>Dal check-in all’eventuale smarrimento/riconsegna.</p>
 */
public enum StatoBagaglio {
    /** Bagaglio registrato. */
    REGISTRATO,    // appena registrato al check-in
    /** Bagaglio caricato sull'aereo. */
    CARICATO,
    /** Bagaglio disponibile al ritiro a destinazione. */
    DISPONIBILE,
    /** Bagaglio segnalato come smarrito. */
    SMARRITO
}
