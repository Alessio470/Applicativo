package controller;


import gui.HomeUtenteGenerico;
import gui.HomepageAmministratore;

import model.Volo;
import model.enums.*;

import DAO.UtenteDAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConnessioneDatabase;

import DAO.VoloDAO;
import model.Utente;


/**
 * Controller centrale del sistema.
 */
public class Controller {

    private Utente u=null;
        public Controller() {
        }



    public void doLogin(String user, String pass, JFrame frame) {


        UtenteDAO utenteDAO=null;

        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            utenteDAO = new UtenteDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }


        try {
             u = utenteDAO.login(user, pass);//TODO controllare questo perchè forse da null
            if (u == null) {
                JOptionPane.showMessageDialog(frame, "Credenziali non valide");
                return;
            }

            if (u.getRuolo() == RuoloUtente.AMMINISTRATORE) {
                new HomepageAmministratore(frame, this);
            } else if (u.getRuolo()== RuoloUtente.GENERICO) {
                new HomeUtenteGenerico(frame, this);
            }else {
                JOptionPane.showMessageDialog(frame, "Utente non valido");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore durante il login:\n" + ex.getMessage());
        }

        System.out.println("Loggato con -> Username: " + u.getUsername() + " | Password: " + u.getPassword());

    }//Parentesi doLogin


    //DA FINIRE
    public void onRegistrati(String username, String password, String conferma,String ruolo, JFrame frame ) {

            UtenteDAO utenteDAO=null;

            //Try del dao
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            utenteDAO = new UtenteDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore connessione DB:\n" + ex.getMessage());
        }


        // --- Validazioni base ---
        if (username.isEmpty() || password.isEmpty() || conferma.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Compila tutti i campi.");
            return;
        }
        if (!password.equals(conferma)) {
            JOptionPane.showMessageDialog(frame, "Le password non coincidono.");
            return;
        }
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(frame, "Username troppo corto (min 3).");
            return;
        }
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(frame, "Password troppo corta (min 4).");
            return;
        }

        try {
            // Controllo unicità username
            if (utenteDAO.usernameExists(username)) {
                JOptionPane.showMessageDialog(frame, "Username già in uso.");
                return;
            }

            utenteDAO.registraUtente(username, password, ruolo);

            JOptionPane.showMessageDialog(
                    frame,
                    "Registrazione completata!\nUsername: " + username + "\nRuolo: " + ruolo,
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Errore durante la registrazione:\n" + ex.getMessage());
        }

    }//Parentesi onRegistrati



    public void AddVoli(String compagniaaerea, String data, String orario, String aeroportoorigine,String aeroportodestinazione, String numerogate, JFrame frame, JFrame prevframe) {

        String codiceVolo = "Test123";



        //String codice, String compagnia, String aeroportoOrigine, String aeroportoDestinazione, Da
        //dataOra, int ritardoMinuti, StatoVolo stato, Gate gate


        if (compagniaaerea.isEmpty() || data == null || orario == null) {
            JOptionPane.showMessageDialog(frame, "Compila tutti i campi obbligatori.");
            return;
        }


        Volo volocreato = new Volo(codiceVolo, compagniaaerea, aeroportoorigine, aeroportodestinazione, data, orario, 0, StatoVolo.PROGRAMMATO, numerogate);

        //Connessione al db

        VoloDAO voloDAO=null;

        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            voloDAO = new VoloDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }


        try {

            if (voloDAO != null) {
                voloDAO.registraVolo(volocreato);
            }else {
                JOptionPane.showMessageDialog(frame, "Volo non valido.");
                return;
            }

            JOptionPane.showMessageDialog(frame, "Volo inserito con successo.");
            frame.dispose();
            prevframe.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Errore durante l inserimento volo:\n" + ex.getMessage());
        }





    }//Parentesi Finale AddVoli

    // Restituisce solo i voli prenotabili
    public List<Volo> getVoliPrenotabili() {

        List<Volo> resultDB = new ArrayList<>();
        VoloDAO voloDAO=null;


        //Connessione al db
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            voloDAO = new VoloDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }


        try {

             resultDB = voloDAO.getVoliPrenotabili();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultDB;

    }//Parentesi getVoliPrenotabilidaNapoli

/*
    private ArrayList<Utente> utentiRegistratiRef;
    private ArrayList<Volo> voliRegistratiRef;

    private UtenteGenerico utenteLoggato;
    private UtenteAmministratore adminLoggato;

    private VoloDAO voloDAO;
    private PrenotazioneDAO prenotazioneDAO;

    public Controller(ArrayList<Utente> utentiRegistrati, ArrayList<Volo> voliRegistrati) {
        this.utentiRegistratiRef = utentiRegistrati;
        this.voliRegistratiRef = voliRegistrati;

        try {
            this.voloDAO = new VoloImplDAO();
            this.prenotazioneDAO = new PrenotazioneImplDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB: " + e.getMessage());
        }
    }

    // --- LOGIN ---
    public boolean loginValido(String username, String password) {
        utenteLoggato = null;
        adminLoggato = null;

        for (Utente u : utentiRegistratiRef) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                if (u instanceof UtenteGenerico) utenteLoggato = (UtenteGenerico) u;
                else if (u instanceof UtenteAmministratore) adminLoggato = (UtenteAmministratore) u;
                return true;
            }
        }
        return false;
    }

    public String userType() {
        if (adminLoggato != null) return "Admin";
        if (utenteLoggato != null) return "Generico";
        return "Nessun utente loggato";
    }








    // --- TABLE MODEL PER SWING ---
    public DefaultTableModel getModelloTabellaVoli() {
        String[] colonne = {"Codice Volo", "Compagnia", "Origine", "Destinazione", "DataOra", "Ritardo", "Stato", "Gate"};
        DefaultTableModel model = new DefaultTableModel(colonne, 0);

        List<Volo> listaVoli = getVoli();

        for (Volo v : listaVoli) {
            Object[] riga = {
                    v.getCodice(),
                    v.getCompagnia(),
                    v.getAeroportoOrigine(),
                    v.getAeroportoDestinazione(),
                    v.getDataOra(),
                    v.getRitardoMinuti(),
                    v.getStato(),
                    v.getNumeroGate() != null ? v.getNumeroGate() : "-"
            };
            model.addRow(riga);
        }
        return model;
    }

 */
}
