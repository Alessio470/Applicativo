package model;

import java.util.ArrayList;
import java.util.List;

public class Passeggero {
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private List<Prenotazione> prenotazioni;
    private List<Bagaglio> bagagli;

    public Passeggero(String nome, String cognome, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.prenotazioni = new ArrayList<>();
        this.bagagli = new ArrayList<>();
    }

    // --- Getter ---
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getCodiceFiscale() { return codiceFiscale; }
    public List<Prenotazione> getPrenotazioni() { return prenotazioni; }
    public List<Bagaglio> getBagagli() { return bagagli; }

    // --- Metodi utili ---
    public void aggiungiPrenotazione(Prenotazione p) { prenotazioni.add(p); }
    public void aggiungiBagaglio(Bagaglio b) { bagagli.add(b); }

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + codiceFiscale + ")";
    }
}
