package model;

public class Gate {
    private final String numero;

    public Gate(String numero) {
        this.numero = numero;
    }

    public String getNumero() { return numero; }

    /**
     * Assegna un volo a questo gate mantenendo la consistenza bidirezionale.
     * Se il volo aveva già un altro gate, viene liberato.
     * Se questo gate è occupato da un altro volo, lancia IllegalStateException.
     */


    /**
     * Libera questo gate e scollega il volo (se presente) in modo consistente.
     */


    @Override
    public String toString() {
        return "Gate{" +
                "numero=" + numero +
                '}';
    }
}
