package model;

import controller.Controller;
import gui.*;
import model.enums.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Utente> utentiRegistrati  = new ArrayList<Utente>();int t;


        // Creazione di un volo in partenza da Napoli
        VoloPartenzaDaNapoli volo1 = new VoloPartenzaDaNapoli("NAP123", "ITA Airways", "2025-04-22", "14:30", "0 minuti", StatoVolo.programmato, "Roma Fiumicino");

        // Creazione di un gate
        Gate gate5 = new Gate(5);

        // Creazione di un amministratore e modifica del gate del volo
        UtenteAmministratore admin = new UtenteAmministratore("admin", "password");
        admin.modificaGate(volo1, gate5);

        // Creazione di un utente generico e prenotazione di un volo
        UtenteGenerico utente = new UtenteGenerico("utente1", "password1");
        UtenteGenerico utente2 = new UtenteGenerico("utente2", "password2");

        UtenteGenerico utente3 = new UtenteGenerico("utente3", "password3");
        UtenteGenerico utente4 = new UtenteGenerico("utente4", "password4");


        utentiRegistrati.add(utente);
        utentiRegistrati.add(utente2);
        utentiRegistrati.add(utente3);
        utentiRegistrati.add(utente4);



        Login login = new Login(new Controller(utentiRegistrati));

        login.setVisible(true);


        Prenotazione prenotazione = new Prenotazione("TCK123", "RSSMRA00A01H501Z", "Mario", "Rossi", "12A", StatoPrenotazione.confermata, volo1);


        // Effettuazione della prenotazione e visualizzazione
        utente.prenotaVolo(volo1, prenotazione);
        utente.visualizzaPrenotazioni();
    }
}
