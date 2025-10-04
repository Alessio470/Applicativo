package controller;


import DAO.GateDAO;
import DAO.PrenotazioneDAO;
import gui.HomeUtenteGenerico;
import gui.HomepageAmministratore;

import model.Prenotazione;
import model.UtenteAmministratore;
import model.UtenteGenerico;
import model.Volo;
import model.enums.*;

import DAO.UtenteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.format.DateTimeFormatter;
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

    /**
     * Instantiates a new Controller.
     */
    public Controller() {
        }


    /**
     * Do login.
     *
     * @param user  the user
     * @param pass  the pass
     * @param frame the frame
     */
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
            }//TODO Controllare se è giusto

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore durante il login:\n" + ex.getMessage());
        }

        System.out.println("Loggato con -> Username: " + u.getUsername() + " | Password: " + u.getPassword());

    }//Parentesi doLogin


    /**
     * On registrati.
     *
     * @param username the username
     * @param password the password
     * @param conferma the conferma
     * @param ruolo    the ruolo
     * @param frame    the frame
     */
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

        //REGISTRAZIONE UTENTE
        Utente utenteRegistrato = null;

        if (ruolo.equals(RuoloUtente.GENERICO)) {
            utenteRegistrato = new UtenteGenerico(username, password);
        } else {
            utenteRegistrato = new UtenteAmministratore(username, password);
        }

        try {
            // Controllo unicità username
            if (utenteDAO.usernameExists(username)) {
                JOptionPane.showMessageDialog(frame, "Username già in uso.");
                return;
            }

            utenteDAO.registraUtente(utenteRegistrato);

            JOptionPane.showMessageDialog(frame, "Registrazione completata!\nUsername: " + username + "\nRuolo: " + ruolo, "OK", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore durante la registrazione:\n" + ex.getMessage());
        }

    }//Parentesi onRegistrati

    /**
     * Add voli.
     *
     * @param compagniaaerea        the compagniaaerea
     * @param data                  the data
     * @param orario                the orario
     * @param aeroportoorigine      the aeroportoorigine
     * @param aeroportodestinazione the aeroportodestinazione
     * @param numerogate            the numerogate
     * @param frame                 the frame
     * @param prevframe             the prevframe
     */
    public void AddVoli(String compagniaaerea, String data, String orario, String aeroportoorigine,String aeroportodestinazione, String numerogate, JFrame frame, JFrame prevframe) {

        String codiceVolo = "Test123";
        //TODO Generazione del codice volo



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

    /**
     * Add voli con stato.
     *
     * @param compagniaaerea        the compagniaaerea
     * @param data                  the data
     * @param orario                the orario
     * @param aeroportoorigine      the aeroportoorigine
     * @param aeroportodestinazione the aeroportodestinazione
     * @param numerogate            the numerogate
     * @param ritardoMinuti         the ritardo minuti
     * @param statoVoloStr          the stato volo str
     */
    public void AddVoliConStato(String compagniaaerea, String data, String orario, String aeroportoorigine, String aeroportodestinazione, String numerogate, int ritardoMinuti, String statoVoloStr) {

        // Validazioni base
        if (compagniaaerea == null || compagniaaerea.isBlank() || data == null || data.isBlank() || orario == null || orario.isBlank() || aeroportoorigine == null || aeroportoorigine.isBlank() || aeroportodestinazione == null || aeroportodestinazione.isBlank() || numerogate == null || numerogate.isBlank()) {
            throw new IllegalArgumentException("Compila tutti i campi obbligatori.");

        }
        if (aeroportoorigine.equalsIgnoreCase(aeroportodestinazione)) {
            throw new IllegalArgumentException("Aeroportoorigine non valido.");
        }

        // Stato enum dalla stringa della combo
        StatoVolo statoEnum;
        try {
            statoEnum = StatoVolo.valueOf(statoVoloStr.toUpperCase());
        } catch (IllegalArgumentException ex) {
            statoEnum = StatoVolo.PROGRAMMATO;
        }

        //Crea l'oggetto Volo
        Volo volo = new Volo(null, compagniaaerea, aeroportoorigine, aeroportodestinazione, data, orario, ritardoMinuti, statoEnum, numerogate);

        // DAO
        VoloDAO voloDAO = null;
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            voloDAO = new VoloDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
            return;
        }

        // Insert
        try {
            voloDAO.registraVolo(volo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Errore durante l'inserimento volo:\n" + ex.getMessage());
        }
    }

    /**
     * Gets voli prenotabili.
     *
     * @return the voli prenotabili
     */
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

    /**
     * Effettua prenotazione.
     *
     * @param codiceVolo              the codice volo
     * @param nomePasseggero          the nome passeggero
     * @param cognomePasseggero       the cognome passeggero
     * @param posto                   the posto
     * @param codicefiscalepasseggero the codicefiscalepasseggero
     */
    public void effettuaPrenotazione(String codiceVolo,String nomePasseggero,String cognomePasseggero,String posto, String codicefiscalepasseggero) {

            //String numeroBiglietto,String usernameUtente, String codiceVolo,
        // String nomePasseggero,String cognomePasseggero,String posto, StatoPrenotazione stato, String codicefiscalepasseggero


        //NB, il biglietto verrà generato nel db con una sequenza, quindi lasciare null
        //String numeroBiglietto = "AATest123";

        int stato= StatoPrenotazione.CONFERMATA.ordinal()+1;
        PrenotazioneDAO prenotazioneDAO=null;

        //Connessione al db

        PrenotazioneDAO PrenotazioneDAO=null;

        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            PrenotazioneDAO = new PrenotazioneDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }

        Prenotazione prenotazione=new Prenotazione(null,this.getUsernameUtente(),codiceVolo,nomePasseggero,cognomePasseggero,posto,stato,codicefiscalepasseggero);

        try{
            PrenotazioneDAO.InserisciPrenotazione(prenotazione);
        }catch (SQLException e){
            e.printStackTrace();
        }


        }

    /**
     * Gets username utente.
     *
     * @return the username utente
     */
    public String getUsernameUtente() {
            return u.getUsername();
    }

    /**
     * Gets voli.
     *
     * @return the voli
     */
    public List<Volo> getVoli() {
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

            resultDB = voloDAO.getVoli();
//
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultDB;

    }//Fine parentesi getVoli

    /**
     * Conferma modifica.
     *
     * @param codiceVolo            the codice volo
     * @param compagnia             the compagnia
     * @param aeroportoOrigine      the aeroporto origine
     * @param aeroportoDestinazione the aeroporto destinazione
     * @param dataDDMMYYYY          the data ddmmyyyy
     * @param orarioHHmm            the orario h hmm
     * @param ritardoMinuti         the ritardo minuti
     * @param statoVolo             the stato volo
     * @param gate                  the gate
     */
    public void confermaModifica(String codiceVolo, String compagnia, String aeroportoOrigine, String aeroportoDestinazione, String dataDDMMYYYY, String orarioHHmm, int ritardoMinuti, String statoVolo, String gate) {

        Volo voloAggiornato = new Volo(codiceVolo, compagnia, aeroportoOrigine, aeroportoDestinazione, dataDDMMYYYY, orarioHHmm, ritardoMinuti, model.enums.StatoVolo.valueOf(statoVolo.toUpperCase()), (gate == null || gate.isBlank()) ? null : gate.trim());

        //Istanzia il DAO con connessione
        VoloDAO voloDAO;
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            voloDAO = new VoloDAO(conn);
        } catch (Exception ex) {
            throw new RuntimeException("Errore connessione DB: " + ex.getMessage(), ex);
        }

        //update
        try {
            voloDAO.updateVolo(voloAggiornato);
        } catch (Exception ex) {
            throw new RuntimeException("Errore durante l'aggiornamento del volo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Gets voli da per napoli.
     *
     * @return the voli da per napoli
     */
    public List<Volo> getVoliDaPerNapoli() {

       DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/uuuu");
       DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");
       VoloDAO voloDAO=null;


        List<Volo> resultDB = new ArrayList<>();

        //Connessione al db
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            voloDAO = new VoloDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }

        try {

            resultDB = voloDAO.getVoliDaPerNapoli();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultDB;
//DAO



    }//Fine parentesi getVoliDaPerNapoli

    /**
     * Gets gates.
     *
     * @return the gates
     */
    public List<String> getGates() {

            GateDAO gateDAO =null;

        //Connessione al db
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            gateDAO = new GateDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }

        List<String> resultDB= null;
        try {

            resultDB = gateDAO.getGates();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultDB;

    }//Fine Parentesi getGates


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
