package model.dao.interfaces;

import model.Gate;
import java.util.List;

public interface GateDAO {
    void inserisciGate(Gate gate) throws Exception;
    Gate trovaPerNumero(int numero) throws Exception;
    List<Gate> trovaTutti() throws Exception;
    void aggiornaVoloAssegnato(int numeroGate, String codiceVolo) throws Exception;
}
