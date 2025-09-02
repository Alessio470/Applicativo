package model;

import model.enums.RuoloUtente;
import java.util.ArrayList;
import java.util.List;

public class UtenteGenerico extends Utente {

    private List<Prenotazione> prenotazioni;

    public UtenteGenerico(String username, String password) {
        super(username, password, RuoloUtente.GENERICO);
        this.prenotazioni = new ArrayList<>();
    }

    // aggiunge una prenotazione
    public void prenotaVolo(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
    }

    // rimuove una prenotazione
    public void eliminaPrenotazione(Prenotazione prenotazione) {
        prenotazioni.remove(prenotazione);
    }

    // restituisce tutte le prenotazioni
    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    // restituisce solo le prenotazioni relative a un volo specifico (confrontando il codice del volo)
    public List<Prenotazione> getPrenotazioniPerVolo(Volo volo) {
        List<Prenotazione> result = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            if (p.getCodiceVolo().equals(volo.getCodice())) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "UtenteGenerico{" + getUsername() + "}";
    }
}
