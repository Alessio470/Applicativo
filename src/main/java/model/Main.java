package model;

import controller.Controller;
import gui.*;
import model.enums.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {



        Volo volo1 = new VoloPartenzaDaNapoli("aaa", "compagnia", "12/11/03", "12:00", "Nan", StatoVolo.programmato, "Turchia");
        Volo volo2 = new VoloPartenzaDaNapoli("bbb", "compagnia", "12/11/03", "12:00", "Nan", StatoVolo.programmato, "Turchia");


        ArrayList<Utente> utentiRegistrati  = new ArrayList<Utente>();

        // Creazione di un amministratore e modifica del gate del volo
        //admin.modificaGate(volo1, gate5);

        // Creazione di un utente generico e prenotazione di un volo
        UtenteGenerico utente = new UtenteGenerico("utente1", "password1");
        UtenteGenerico utente2 = new UtenteGenerico("utente2", "password2");

        UtenteGenerico utente3 = new UtenteGenerico("utente3", "password3");
        UtenteGenerico utente4 = new UtenteGenerico("utente4", "password4");

        UtenteAmministratore admin = new UtenteAmministratore("admin1", "password1");
        admin.inserisciVolo(volo1);
        admin.inserisciVolo(volo2);

        UtenteAmministratore admin2 = new UtenteAmministratore("admin2", "password2");
        admin2.inserisciVolo(volo1);
        admin2.inserisciVolo(volo2);


        utentiRegistrati.add(utente);
        utentiRegistrati.add(utente2);
        utentiRegistrati.add(utente3);
        utentiRegistrati.add(utente4);
        utentiRegistrati.add(admin);
        utentiRegistrati.add(admin2);

        Prenotazione prenotazione = new Prenotazione("TCK123", "RSSMRA00A01H501Z", "Mario", "Rossi", "12A", StatoPrenotazione.confermata, volo1);

        // Effettuazione della prenotazione e visualizzazione
        for(int i = 0; i < 20; i++){
        utente.prenotaVolo(volo1, prenotazione);}

        //utente.visualizzaPrenotazioni();


        Login login = new Login(new Controller(utentiRegistrati));

        login.setVisible(true);
    }
}
