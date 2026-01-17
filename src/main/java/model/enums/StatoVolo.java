package model.enums;

/**
 * Stati operativi di un volo.
 * <p>Coprono l’intero ciclo: pianificazione, partenza, eventuale ritardo, arrivo, cancellazione.</p>
 */
public enum StatoVolo {
    /**
     * Volo pianificato e non ancora partito.
     */
    PROGRAMMATO,
    /**
     * Volo decollato.
     */
    DECOLLATO,
    /**
     * Volo con ritardo comunicato.
     */
    IN_RITARDO,
    /**
     * Volo atterrato.
     */
    ATTERRATO,
    /**
     * Volo cancellato.
     */
    CANCELLATO
}
