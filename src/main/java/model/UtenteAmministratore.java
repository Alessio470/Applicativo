package model;

import java.util.ArrayList;

public class UtenteAmministratore extends Utente {

    private ArrayList<Volo> voliGestiti;

    public UtenteAmministratore(String username, String password) {
        super(username, password);
        this.voliGestiti = new ArrayList<>();
    }

    //TODO
    @Override
    public void visualizzaVoli() {
        System.out.println("Visualizzazione voli per amministratore:");
        for (Volo v : voliGestiti) {
            System.out.println("Codice: " + v.getCodiceVolo() + " - Compagnia: " + v.getCompagniaAerea());
        }
    }

    public void inserisciVolo(Volo nuovoVolo) {
        voliGestiti.add(nuovoVolo);
    }

    public void aggiornaVolo(String codiceVolo, Volo voloAggiornato) {
        for (int i = 0; i < voliGestiti.size(); i++) {
            if (voliGestiti.get(i).getCodiceVolo().equals(codiceVolo)) {
                voliGestiti.set(i, voloAggiornato);
                System.out.println("Volo aggiornato: " + codiceVolo);
                return;
            }
        }
        System.out.println("Volo da aggiornare non trovato: " + codiceVolo);
    }

    public ArrayList<Volo> getVoliGestiti() {
        return voliGestiti;
    }


    // Metodo per modificare il gate di imbarco del volo
    public void modificaGate(VoloPartenzaDaNapoli volo, Gate gate) {
        volo.setGate(gate);
        System.out.println("Gate modificato: " + gate.getNumeroGate());
    }

}