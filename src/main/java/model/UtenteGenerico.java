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

    // --- Metodi di gestione prenotazioni ---
    public void prenotaVolo(Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
    }

    public void eliminaPrenotazione(Prenotazione prenotazione) {
        prenotazioni.remove(prenotazione);
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

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
