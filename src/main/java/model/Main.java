package model;

import model.enums.StatoBagaglio;
import model.enums.StatoPrenotazione;
import model.enums.StatoVolo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // --- Voli ---
        ArrayList<Volo> voli = new ArrayList<>();

        Volo voloA = new Volo(
                "AAA123",
                "CompagniaAerea1",
                "Napoli",
                "Turchia",
                LocalDateTime.parse("12/11/2025 12:00", dtf),
                0,
                StatoVolo.PROGRAMMATO,
                1
        );

        Volo voloB = new Volo(
                "BBB456",
                "CompagniaAerea2",
                "Napoli",
                "Germania",
                LocalDateTime.parse("12/11/2025 14:00", dtf),
                15,
                StatoVolo.IN_RITARDO,
                2
        );

        Volo voloC = new Volo(
                "CCC789",
                "CompagniaAerea3",
                "Francia",
                "Napoli",
                LocalDateTime.parse("12/11/2025 16:00", dtf),
                0,
                StatoVolo.PROGRAMMATO,
                null
        );

        voli.add(voloA);
        voli.add(voloB);
        voli.add(voloC);

        // --- Utenti ---
        ArrayList<Utente> utenti = new ArrayList<>();
        UtenteGenerico user = new UtenteGenerico("utente1", "pass1");
        UtenteAmministratore admin = new UtenteAmministratore("admin1", "adminpass");
        utenti.add(user);
        utenti.add(admin);

        // --- Passeggeri ---
        Passeggero p1 = new Passeggero("Mario", "Rossi", "RSSMRA00A01H501Z");
        Passeggero p2 = new Passeggero("Luca", "Bianchi", "BNCLCU00B02H501X");

        // --- Prenotazioni ---
        Prenotazione pr1 = new Prenotazione(
                1,
                user.getUsername(),              // usernameUtente (prenotazione con login)
                "AAA123",                        // codice volo
                p1.getNome() + " " + p1.getCognome(),
                "TCK001",
                "12A",
                StatoPrenotazione.CONFERMATA
        );

        Prenotazione pr2 = new Prenotazione(
                2,
                null,                            // prenotazione senza login
                "BBB456",
                p2.getNome() + " " + p2.getCognome(),
                "TCK002",
                "14B",
                StatoPrenotazione.IN_ATTESA
        );

        // Collego le prenotazioni all'utente generico (solo quelle fatte da lui)
        user.prenotaVolo(pr1);

        // (Opzionale) Collego anche lato volo per contare/prendere confermate
        voloA.aggiungiPrenotazione(pr1);
        voloB.aggiungiPrenotazione(pr2);

        // --- Bagagli ---
        Bagaglio b1 = new Bagaglio("BAG001", p1, voloA, StatoBagaglio.REGISTRATO);
        Bagaglio b2 = new Bagaglio("BAG002", p2, voloB, StatoBagaglio.DISPONIBILE);

        // Aggiorno stato bagaglio 1 per test
        b1.setStato(StatoBagaglio.CARICATO);

        // --- Stampe di test ---
        System.out.println("=== VOLI ===");
        for (Volo v : voli) System.out.println(v);

        System.out.println("\n=== UTENTI ===");
        for (Utente u : utenti) System.out.println(u);

        System.out.println("\n=== PRENOTAZIONI DELL'UTENTE " + user.getUsername() + " ===");
        for (Prenotazione p : user.getPrenotazioni()) System.out.println(p);

        System.out.println("\n=== PRENOTAZIONI DELL'UTENTE PER IL VOLO AAA123 ===");
        for (Prenotazione p : user.getPrenotazioniPerVolo(voloA)) System.out.println(p);

        System.out.println("\n=== BAGAGLI ===");
        System.out.println(b1);
        System.out.println(b2);

        System.out.println("\n=== PRENOTAZIONI CONFERMATE SU AAA123 (lato volo) ===");
        for (Prenotazione p : voloA.getPrenotazioniConfermate()) System.out.println(p);
    }
}
