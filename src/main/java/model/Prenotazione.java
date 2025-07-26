package model;

import model.enums.StatoPrenotazione;

public class Prenotazione {
    private int id;
    private Integer idUtente;       // può essere null se prenotazione fatta senza login
    private String codiceVolo;
    private String nomePasseggero;  // sempre valorizzato
    private String numeroBiglietto;
    private String posto;
    private StatoPrenotazione stato;

    public Prenotazione(int id, Integer idUtente, String codiceVolo, String nomePasseggero,
                        String numeroBiglietto, String posto, StatoPrenotazione stato) {
        this.id = id;
        this.idUtente = idUtente;
        this.codiceVolo = codiceVolo;
        this.nomePasseggero = nomePasseggero;
        this.numeroBiglietto = numeroBiglietto;
        this.posto = posto;
        this.stato = stato;
    }

    public int getId() { return id; }
    public Integer getIdUtente() { return idUtente; }
    public String getCodiceVolo() { return codiceVolo; }
    public String getNomePasseggero() { return nomePasseggero; }
    public String getNumeroBiglietto() { return numeroBiglietto; }
    public String getPosto() { return posto; }
    public StatoPrenotazione getStato() { return stato; }

    public void setId(int id) { this.id = id; }
    public void setIdUtente(Integer idUtente) { this.idUtente = idUtente; }
    public void setCodiceVolo(String codiceVolo) { this.codiceVolo = codiceVolo; }
    public void setNomePasseggero(String nomePasseggero) { this.nomePasseggero = nomePasseggero; }
    public void setNumeroBiglietto(String numeroBiglietto) { this.numeroBiglietto = numeroBiglietto; }
    public void setPosto(String posto) { this.posto = posto; }
    public void setStato(StatoPrenotazione stato) { this.stato = stato; }

    @Override
    public String toString() {
        return "Prenotazione{id=" + id + ", passeggero='" + nomePasseggero + "', stato=" + stato + '}';
    }
}
