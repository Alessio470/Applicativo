package model;

import model.enums.*;

public class Main {
    public static void main(String[] args) {

        // Creazione di un volo in partenza da Napoli
        VoloPartenzaDaNapoli volo1 = new VoloPartenzaDaNapoli("NAP123", "ITA Airways", "2025-04-22", "14:30", "0 minuti", StatoVolo.programmato, "Roma Fiumicino");

        // Creazione di un gate
        Gate gate5 = new Gate(5);

        // Creazione di un amministratore e modifica del gate del volo
        UtenteAmministratore admin = new UtenteAmministratore("admin", "password");
        admin.modificaGate(volo1, gate5);

        // Creazione di un utente generico e prenotazione di un volo
        UtenteGenerico utente = new UtenteGenerico("utente1", "password1");
        Prenotazione prenotazione = new Prenotazione("TCK123", "RSSMRA00A01H501Z", "Mario", "Rossi", "12A", StatoPrenotazione.confermata, volo1);

        // Effettuazione della prenotazione e visualizzazione
        utente.prenotaVolo(volo1, prenotazione);
        utente.visualizzaPrenotazioni();
    }
}
