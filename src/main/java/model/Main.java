package model;

import controller.Controller;
import gui.*;
import model.enums.*;

import java.util.ArrayList;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        ArrayList<Volo> voli = new ArrayList<>();
        Volo volo1 = new VoloPartenzaDaNapoli("aaa", "compagnia", "12/11/03", "12:00", "Nan", StatoVolo.PROGRAMMATO, "Turchia");
        Volo volo2 = new VoloPartenzaDaNapoli("bbb", "compagnia", "12/11/03", "12:00", "Nan", StatoVolo.PROGRAMMATO, "Turchia");
        Volo volo3 = new VoloPartenzaDaNapoli("ccc", "compagnia", "12/11/03", "12:00", "Nan", StatoVolo.IN_RITARDO, "Turchia");
        Volo volo4 = new VoloPartenzaDaNapoli("ddd", "compagnia", "12/11/03", "12:00", "Nan", StatoVolo.CANCELLATO, "Turchia");

        voli.add(volo1);
        voli.add(volo2);
        voli.add(volo3);
        voli.add(volo4);

        ArrayList<Utente> utentiRegistrati  = new ArrayList<Utente>();

        // Creazione di un utente generico e prenotazione di un volo
        UtenteGenerico utente = new UtenteGenerico("utente1", "password1");
        UtenteGenerico utente2 = new UtenteGenerico("utente2", "password2");

        UtenteGenerico utente3 = new UtenteGenerico("utente3", "password3");
        UtenteGenerico utente4 = new UtenteGenerico("utente4", "password4");

        UtenteAmministratore admin = new UtenteAmministratore("admin1", "password1");
        admin.inserisciVolo(volo1);
        admin.inserisciVolo(volo2);
        admin.inserisciVolo(volo3);
        admin.inserisciVolo(volo4);

        UtenteAmministratore admin2 = new UtenteAmministratore("admin2", "password2");
        admin2.inserisciVolo(volo3);
        admin2.inserisciVolo(volo4);


        utentiRegistrati.add(utente);
        utentiRegistrati.add(utente2);
        utentiRegistrati.add(utente3);
        utentiRegistrati.add(utente4);
        utentiRegistrati.add(admin);
        utentiRegistrati.add(admin2);

        Prenotazione prenotazione = new Prenotazione("TCK123", "RSSMRA00A01H501Z", "Vittoria", "oilgoG", "12A", StatoPrenotazione.CONFERMATA, volo1);

        // Effettuazione della prenotazione e visualizzazione
        for(int i = 0; i < 20; i++){
        utente.prenotaVolo(volo1, prenotazione);}

        Login login = new Login(new Controller(utentiRegistrati,voli));

        login.setVisible(true);

    }
}
