package model;

import model.enums.RuoloUtente;
import java.util.ArrayList;
import java.util.List;

public class UtenteAmministratore extends Utente {

    private List<Volo> voliGestiti;

    public UtenteAmministratore(String username, String password) {
        super(username, password, RuoloUtente.AMMINISTRATORE);
        this.voliGestiti = new ArrayList<>();
    }

    // --- Metodi di gestione voli ---
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
            if (v.getCodice().equals(codice)) {
                return v;
            }
        }
        return null;
    }

    public void modificaGate(Volo volo, Gate nuovoGate) {
        if (voliGestiti.contains(volo)) {
            volo.setNumeroGate(nuovoGate);
        }
    }

    @Override
    public String toString() {
        return "UtenteAmministratore{" + getUsername() + "}";
    }
}
