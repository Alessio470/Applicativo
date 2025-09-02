package model.dao.interfaces;


import model.*;
import java.util.List;

public interface UtenteDAO {
    void inserisciUtente(Utente utente);
    Utente trovaPerUsername(String username);
    List<Utente> trovaTutti();
    void eliminaUtente(String username);
}
