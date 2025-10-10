package model.enums;

/**
 * Stati possibili di una prenotazione.
 * <p>Indica l’esito del processo di richiesta/acquisto.</p>
 */
public enum StatoPrenotazione {
    /** Prenotazione confermata. */
    CONFERMATA,
    /** Prenotazione registrata ma in attesa di conferma. */
    IN_ATTESA,
    /** Prenotazione annullata. */
    CANCELLATA
}
