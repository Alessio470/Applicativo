package dao;

import model.Prenotazione;
import java.util.List;

public interface PrenotazioneDAO {
    void inserisciPrenotazione(Prenotazione p) throws Exception;
    List<Prenotazione> getPrenotazioniPerUtente(String username) throws Exception;
}
