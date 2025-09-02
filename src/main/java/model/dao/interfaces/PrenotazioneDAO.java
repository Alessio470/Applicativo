package model.dao.interfaces;

import model.*;
import java.util.List;

public interface PrenotazioneDAO {
    void inserisciPrenotazione(Prenotazione prenotazione);
    Prenotazione trovaPerCodice(String codice);
    List<Prenotazione> trovaPerVolo(String codiceVolo);
    void aggiornaStato(String codice, model.enums.StatoPrenotazione stato);
    void eliminaPrenotazione(String codice);
}
