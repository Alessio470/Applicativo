package model;

import model.enums.RuoloUtente;
import model.enums.StatoPrenotazione;
import model.enums.StatoVolo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Formattatore per data e ora
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Lista voli
        ArrayList<Volo> voli = new ArrayList<>();

        // Creo voli (arrivi e partenze unificati)
        voli.add(new Volo(
                "AAA123",
                "CompagniaAerea1",
                "Napoli",                  // aeroportoOrigine
                "Turchia",                 // aeroportoDestinazione
                LocalDateTime.parse("12/11/2025 12:00", dtf),
                0,
                StatoVolo.PROGRAMMATO,
                1                          // gate assegnato
        ));

        voli.add(new Volo(
                "BBB456",
                "CompagniaAerea2",
                "Napoli",
                "Germania",
                LocalDateTime.parse("12/11/2025 14:00", dtf),
                15,
                StatoVolo.IN_RITARDO,
                2
        ));

        voli.add(new Volo(
                "CCC789",
                "CompagniaAerea3",
                "Francia",
                "Napoli",
                LocalDateTime.parse("12/11/2025 16:00", dtf),
                0,
                StatoVolo.PROGRAMMATO,
                null                       // gate non assegnato ancora
        ));

        // Lista utenti registrati
        ArrayList<Utente> utenti = new ArrayList<>();

        // Creo utenti
        Utente utente1 = new Utente(1, "utente1", "pass1", RuoloUtente.UTENTE);
        Utente utente2 = new Utente(2, "admin1", "adminpass", RuoloUtente.AMMINISTRATORE);

        utenti.add(utente1);
        utenti.add(utente2);

        // Creo prenotazione (utente1 prenota il volo AAA123)
        Prenotazione prenotazione = new Prenotazione(
                1,
                utente1.getId(),
                "AAA123",
                "Mario Rossi",
                "TCK001",
                "12A",
                StatoPrenotazione.CONFERMATA
        );

        // Stampa dati
        System.out.println("Voli disponibili:");
        for (Volo v : voli) {
            System.out.println(v);
        }

        System.out.println("\nUtenti registrati:");
        for (Utente u : utenti) {
            System.out.println(u);
        }

        System.out.println("\nPrenotazione effettuata:");
        System.out.println(prenotazione);

        // Qui puoi aggiungere altre logiche come login, gestione prenotazioni, ecc.

    }
}
