package model;

import model.enums.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        ArrayList<Volo> voli = new ArrayList<>();

        voli.add(new Volo(
                "AAA123",
                "CompagniaAerea1",
                "Napoli",
                "Turchia",
                LocalDateTime.parse("12/11/2025 12:00", dtf),
                0,
                StatoVolo.PROGRAMMATO,
                1
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
                null
        ));

        ArrayList<Utente> utenti = new ArrayList<>();

        Utente utente1 = new Utente("utente1", "pass1", RuoloUtente.UTENTE);
        Utente utente2 = new Utente("admin1", "adminpass", RuoloUtente.AMMINISTRATORE);

        utenti.add(utente1);
        utenti.add(utente2);

        // Prenotazione fatta da utente1 (username "utente1")
        Prenotazione prenotazione = new Prenotazione(
                1,
                "utente1",               // usernameUtente
                "AAA123",
                "Mario Rossi",
                "TCK001",
                "12A",
                StatoPrenotazione.CONFERMATA
        );

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

    }
}
