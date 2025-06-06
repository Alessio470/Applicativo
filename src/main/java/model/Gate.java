package model;

/**
 * The type Gate.
 */
public class Gate {
    private String numeroGate;

    /**
     * Instantiates a new Gate.
     *
     * @param numero the numero
     */
    public Gate(int numero) {
        this.numeroGate = String.valueOf(numero);
    }

    /**
     * Gets numero gate.
     *
     * @return the numero gate
     */
    public String getNumeroGate() {
        return numeroGate;
    }

    
}