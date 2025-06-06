package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Utente generico.
 */
public class UtenteGenerico extends Utente {
    // Lista delle prenotazioni effettuate dall'utente.
    private List<Prenotazione> prenotazioni;

    /**
     * Instantiates a new Utente generico.
     *
     * @param username the username
     * @param password the password
     */
// Costruttore
    public UtenteGenerico(String username, String password) {
        super(username, password);
        this.prenotazioni = new ArrayList<>();
    }

    /**
     * Prenota volo.
     *
     * @param volo         the volo
     * @param prenotazione the prenotazione
     */
// Metodo per effettuare una nuova prenotazione
    public void prenotaVolo(Volo volo, Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
        //System.out.println("Prenotazione effettuata per volo " + volo.getCodiceVolo());
    }

    /**
     * Modifica prenotazione.
     *
     * @param prenotazione the prenotazione
     */
// Metodo per simulare la modifica di una prenotazione esistente
    public void modificaPrenotazione(Prenotazione prenotazione) {
        System.out.println("Prenotazione modificata: " + prenotazione.getNumeroBiglietto());
        //TODO
    }

    /**
     * Cerca prenotazione prenotazione.
     *
     * @param criterio the criterio
     * @return the prenotazione
     */
// Metodo per cercare una prenotazione all'interno della lista in base al "criterio"
    public Prenotazione cercaPrenotazione(String criterio) {
        for (Prenotazione p : prenotazioni) {
            if (p.getNumeroBiglietto().equals(criterio) || p.getNomePasseggero().equals(criterio)) {
                return p;
            }
        }
        return null;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}

    /**
     * Visualizza prenotazioni.
     */
// Metodo per visualizzare tutte le prenotazioni effettuate dall'utente
    public void visualizzaPrenotazioni() {
        for (Prenotazione p : prenotazioni) {
            System.out.println(p);
        }
    }

    /**
     * Gets prenotazioni l.
     *
     * @return the prenotazioni l
     */
    public List<Prenotazione> getPrenotazioniL() {
        return this.prenotazioni;
    }
}