package model;

import model.enums.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // --- Creazione voli ---
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

        // --- Creazione utenti ---
        ArrayList<Utente> utenti = new ArrayList<>();

        UtenteGenerico utente1 = new UtenteGenerico("utente1", "pass1");
        UtenteAmministratore admin1 = new UtenteAmministratore("admin1", "adminpass");

        utenti.add(utente1);
        utenti.add(admin1);

        // --- Creazione passeggeri ---
        Passeggero passeggero1 = new Passeggero("Mario", "Rossi", "RSSMRA00A01H501Z");
        Passeggero passeggero2 = new Passeggero("Luca", "Bianchi", "BNCLCU00B02H501X");

        // --- Prenotazioni ---
        Prenotazione prenotazione1 = new Prenotazione(
                1,
                utente1.getUsername(),
                "AAA123",
                passeggero1.getNome(),
                "TCK001",
                "12A",
                StatoPrenotazione.CONFERMATA
        );

        Prenotazione prenotazione2 = new Prenotazione(
                2,
                null,  // prenotazione senza login
                "BBB456",
                passeggero2.getNome(),
                "TCK002",
                "14B",
                StatoPrenotazione.IN_ATTESA
        );

        // Associo le prenotazioni all'utente generico
        utente1.prenotaVolo(prenotazione1);
        // prenotazione2 non ha utente, quindi non la aggiungo a nessuno

        // --- Creazione bagagli ---
        Bagaglio bagaglio1 = new Bagaglio("BAG001", passeggero1, voli.get(0), StatoBagaglio.REGISTRATO);
        Bagaglio bagaglio2 = new Bagaglio("BAG002", passeggero2, voli.get(1), StatoBagaglio.DISPONIBILE);

        // --- Stampa voli ---
        System.out.println("Voli disponibili:");
        for (Volo v : voli) {
            System.out.println(v);
        }

        // --- Stampa utenti ---
        System.out.println("\nUtenti registrati:");
        for (Utente u : utenti) {
            System.out.println(u);
        }

        // --- Stampa prenotazioni ---
        System.out.println("\nPrenotazioni dell'utente " + utente1.getUsername() + ":");
        for (Prenotazione p : utente1.getPrenotazioni()) {
            System.out.println(p);
        }

        // --- Prenotazioni per volo ---
        System.out.println("\nPrenotazioni per il volo AAA123:");
        for (Prenotazione p : utente1.getPrenotazioniPerVolo(voli.get(0))) {
            System.out.println(p);
        }

        // --- Stampa bagagli ---
        System.out.println("\nBagagli registrati:");
        System.out.println(bagaglio1);
        System.out.println(bagaglio2);
    }
}
