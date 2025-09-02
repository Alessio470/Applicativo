package model.dao.interfaces;

import model.*;
import java.util.List;

public interface BagaglioDAO {
    void inserisciBagaglio(Bagaglio bagaglio);
    List<Bagaglio> trovaPerPrenotazione(String codicePrenotazione);
    void aggiornaStato(int idBagaglio, model.enums.StatoBagaglio stato);
    void eliminaBagaglio(int idBagaglio);
}
