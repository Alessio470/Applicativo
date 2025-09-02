package model.dao.interfaces;

import model.*;
import java.util.List;

public interface GateDAO {
    void inserisciGate(Gate gate);
    Gate trovaPerCodice(String codice);
    List<Gate> trovaTutti();
    void aggiornaStato(String codice, model.enums.StatoGate stato);
}

