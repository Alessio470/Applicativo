package model;


public class Gate {
    private String numeroGate;

    public Gate(int numero) {
        this.numeroGate = String.valueOf(numero);
    }

    public String getNumeroGate() {
        return numeroGate;
    }
}