package model;

/**
 * Rappresenta un gate d’imbarco.
 * <p>È identificato da un numero/etichetta.
 * <p>
 * Assegna un volo a questo gate mantenendo la consistenza bidirezionale.
 * Se il volo aveva già un altro gate, viene liberato.
 * Se questo gate è occupato da un altro volo, lancia IllegalStateException.
 */
public class Gate {
    /** Numero/etichetta del gate. */
    private final String numero;

    /**
     * Crea un gate.
     *
     * @param numero identificativo del gate
     */
    public Gate(String numero) {
        this.numero = numero;
    }

    // --- Getter ---

    /**
     * Restituisce l'identificativo del gate.
     *
     * @return String numero del gate
     */
    public String getNumero() { return numero; }

    // --- toString ---

    @Override
    public String toString() {
        return "Gate{" +
                "numero=" + numero +
                '}';
    }
}
