package model;

import java.util.ArrayList;

public class UtenteAmministratore extends Utente {
    private ArrayList<Volo> voliGestiti;

    public UtenteAmministratore(String username, String password) {
        super(username, password);
        this.voliGestiti = new ArrayList<>();
    }

    // Inserisce un nuovo volo nell'elenco gestito
    public void inserisciVolo(Volo nuovoVolo) {
        voliGestiti.add(nuovoVolo);
    }

    // Aggiorna un volo esistente identificato dal codice
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

    // Ritorna l'elenco dei voli gestiti da questo admin
    public ArrayList<Volo> getVoliGestiti() {
        return voliGestiti;
    }

    // Modifica il gate di un volo in partenza da Napoli
    public void modificaGate(VoloPartenzaDaNapoli volo, Gate gate) {
        volo.setGate(gate);
        System.out.println("Gate modificato: " + gate.getNumeroGate());
    }

    // Metodo facoltativo per stampa console (debug/test)
    public void visualizzaVoli() {
        System.out.println("Visualizzazione voli per amministratore:");
        for (Volo v : voliGestiti) {
            System.out.println("Codice: " + v.getCodiceVolo() + " - Compagnia: " + v.getCompagniaAerea());
        }
    }
}
