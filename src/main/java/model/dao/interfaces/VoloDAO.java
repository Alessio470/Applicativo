package model.dao.interfaces;

import model.*;
import java.util.List;

public interface VoloDAO {
    void inserisciVolo(Volo volo);
    Volo trovaPerCodice(String codice);
    List<Volo> trovaTutti();
    void aggiornaStato(String codice, model.enums.StatoVolo stato);
    void eliminaVolo(String codice);
}

