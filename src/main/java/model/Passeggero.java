package model;

import java.util.ArrayList;
import java.util.List;

public class Passeggero {
    private String nome;
    private String cognome;
    private String codiceFiscale;


    public Passeggero(String nome, String cognome, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;

    }

    // --- Getter ---
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getCodiceFiscale() { return codiceFiscale; }


    // --- Metodi utili ---

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + codiceFiscale + ")";
    }
}
