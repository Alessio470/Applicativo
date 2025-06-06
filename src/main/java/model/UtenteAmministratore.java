package model;

import java.util.ArrayList;

/**
 * The type Utente amministratore.
 */
public class UtenteAmministratore extends Utente {
    private ArrayList<Volo> voliGestiti;

    /**
     * Instantiates a new Utente amministratore.
     *
     * @param username the username
     * @param password the password
     */
    public UtenteAmministratore(String username, String password) {
        super(username, password);
        this.voliGestiti = new ArrayList<>();
    }

    /**
     * Inserisci volo.
     *
     * @param nuovoVolo the nuovo volo
     */
// Inserisce un nuovo volo nell'elenco gestito
    public void inserisciVolo(Volo nuovoVolo) {
        voliGestiti.add(nuovoVolo);
    }

    /**
     * Aggiorna volo.
     *
     * @param codiceVolo     the codice volo
     * @param voloAggiornato the volo aggiornato
     */
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

    /**
     * Gets voli gestiti.
     *
     * @return the voli gestiti
     */
// Ritorna l'elenco dei voli gestiti da questo admin
    public ArrayList<Volo> getVoliGestiti() {
        return voliGestiti;
    }

    /**
     * Modifica gate.
     *
     * @param volo the volo
     * @param gate the gate
     */
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
