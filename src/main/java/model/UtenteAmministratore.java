package model;

import model.enums.RuoloUtente;
import java.util.ArrayList;
import java.util.List;

public class UtenteAmministratore extends Utente {

    private List<Volo> voliGestiti;

    public UtenteAmministratore(String username, String password) {
        super(username, password, RuoloUtente.AMMINISTRATORE);
        voliGestiti = new ArrayList<>();
    }

    public void inserisciVolo(Volo volo) {
        voliGestiti.add(volo);
    }

    public void rimuoviVolo(Volo volo) {
        voliGestiti.remove(volo);
    }

    public List<Volo> getVoliGestiti() {
        return voliGestiti;
    }

    public Volo cercaVolo(String codice) {
        for (Volo v : voliGestiti) {
            if (v.getCodice().equals(codice)) return v;
        }
        return null;
    }

    @Override
    public String toString() {
        return "UtenteAmministratore{" + getUsername() + "}";
    }
}
