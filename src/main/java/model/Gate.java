package model;

public class Gate {
    private final int numero;
    private Volo voloAssociato; // null se il gate è libero

    public Gate(int numero) {
        this.numero = numero;
        this.voloAssociato = null;
    }

    public int getNumero() { return numero; }
    public Volo getVoloAssociato() { return voloAssociato; }

    /**
     * Assegna un volo a questo gate mantenendo la consistenza bidirezionale.
     * Se il volo aveva già un altro gate, viene liberato.
     * Se questo gate è occupato da un altro volo, lancia IllegalStateException.
     */
    public void assegnaVolo(Volo volo) {
        if (volo == null) {
            throw new IllegalArgumentException("Il volo non può essere null.");
        }
        // Se questo gate è occupato da un volo diverso, non possiamo assegnare
        if (this.voloAssociato != null && this.voloAssociato != volo) {
            throw new IllegalStateException("Gate " + numero + " già occupato dal volo " + voloAssociato.getCodice());
        }

        // Se il volo aveva già un altro gate, liberalo
        Gate gatePrecedente = volo.getGate();
        if (gatePrecedente != null && gatePrecedente != this) {
            gatePrecedente.libera();
        }

        // Collega entrambe le parti
        this.voloAssociato = volo;
        volo.setGate(this);
    }

    /**
     * Libera questo gate e scollega il volo (se presente) in modo consistente.
     */
    public void libera() {
        if (this.voloAssociato != null) {
            Volo v = this.voloAssociato;
            this.voloAssociato = null;
            if (v.getGate() == this) {
                v.setGate(null);
            }
        }
    }

    public boolean isOccupato() {
        return voloAssociato != null;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "numero=" + numero +
                ", occupato=" + (voloAssociato != null ? voloAssociato.getCodice() : "LIBERO") +
                '}';
    }
}
