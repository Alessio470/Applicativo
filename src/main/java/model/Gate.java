package model;

public class Gate {
    private int numero;
    private String descrizione;

    public Gate(int numero, String descrizione) {
        this.numero = numero;
        this.descrizione = descrizione;
    }

    public int getNumero() { return numero; }
    public String getDescrizione() { return descrizione; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    @Override
    public String toString() {
        return "Gate{" + "numero=" + numero + ", descrizione='" + descrizione + "'}";
    }
}
