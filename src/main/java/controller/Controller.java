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
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import database.ConnessioneDatabase;

import DAO.VoloDAO;
import model.Utente;


/**
 * Controller dell’applicazione.
 * <p>Coordina GUI, DAO e modello dominio per login/registrazione, gestione voli e prenotazioni.</p>
 */
public class Controller {

    /** Utente attualmente autenticato (se presente). */
    private Utente u=null;

    /**
     * Costruttore predefinito del controller.
     */
    public Controller() {
    }

    /**
     * Esegue il logout azzerando l’utente corrente.
     */
    public void doLogoutUser() { u=null; }

    /**
     * Autentica un utente e apre la home corrispondente al ruolo.
     *
     * <p>In caso di credenziali non valide o errori DB mostra un {@link JOptionPane} con il messaggio.</p>
     *
     * @param user  username inserito
     * @param pass  password inserita
     * @param frame frame padre per i messaggi/modali
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
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore durante il login:\n" + ex.getMessage());
        }

        System.out.println("Loggato con -> Username: " + u.getUsername() + " | Password: " + u.getPassword());

    }//Parentesi doLogin


    /**
     * Gestisce la registrazione di un nuovo utente.
     *
     * <p>Esegue validazioni base, verifica unicità username e invoca {@link UtenteDAO#registraUtente(Utente)}.
     * Mostra messaggi d’errore/successo tramite {@link JOptionPane}.</p>
     *
     * @param username username desiderato
     * @param password password scelta
     * @param conferma conferma password
     * @param ruolo    ruolo selezionato (stringa che rappresenta l’enum {@link RuoloUtente})
     * @param frame    frame padre per i messaggi
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

    /*   public void AddVoli(String compagniaaerea, String data, String orario, String aeroportoorigine,String aeroportodestinazione, String numerogate, JFrame frame, JFrame prevframe) {

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
*/

    /**
     * Crea e registra un nuovo volo impostando lo stato da stringa.
     *
     * <p>Valida i campi obbligatori, converte lo stato in {@link StatoVolo} e invoca {@link VoloDAO#registraVolo(Volo)}.
     * In caso di errore mostra un messaggio all’utente.</p>
     *
     * @param compagniaaerea        compagnia aerea
     * @param data                  data (formato {@code dd/MM/yyyy})
     * @param orario                orario (formato {@code HH:mm})
     * @param aeroportoorigine      aeroporto di origine
     * @param aeroportodestinazione aeroporto di destinazione
     * @param numerogate            gate assegnato
     * @param ritardoMinuti         ritardo in minuti
     * @param statoVoloStr          stato del volo come stringa (es. {@code "PROGRAMMATO"})
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
     * Restituisce i voli prenotabili (stato programmato).
     *
     * <p>In caso di errore di connessione/lettura mostra un messaggio e restituisce una lista vuota.</p>
     *
     * @return lista (eventualmente vuota) di voli prenotabili
     */
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
     * Effettua una prenotazione confermata per l’utente loggato.
     *
     * <p>Genera l’oggetto {@link Prenotazione} con stato confermato e invoca
     * {@link PrenotazioneDAO#inserisciPrenotazione(Prenotazione)}.
     * Eventuali errori vengono loggati/mostrati.</p>
     *
     * @param codiceVolo codice del volo
     * @param nomePasseggero nome del passeggero
     * @param cognomePasseggero cognome del passeggero
     * @param posto posto assegnato
     * @param codicefiscalepasseggero codice fiscale del passeggero
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
            PrenotazioneDAO.inserisciPrenotazione(prenotazione);
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    /**
     * Cancella una prenotazione dell'utente attualmente loggato,
     * dato il numero di biglietto.
     *
     * <p>Delega a {@link PrenotazioneDAO#cancellaPrenotazione(String, String)}
     * passando lo username dell'utente corrente.</p>
     *
     * @param numeroBiglietto numero del biglietto da cancellare
     * @return true se è stata cancellata una riga, false altrimenti
     */
    public boolean cancellaPrenotazioneUtente(String numeroBiglietto) {

        PrenotazioneDAO prenotazioneDAO = null;

        // Connessione al DB
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            prenotazioneDAO = new PrenotazioneDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Errore connessione DB:\n" + ex.getMessage());
            return false;
        }

        try {
            int rows = prenotazioneDAO.cancellaPrenotazione(
                    numeroBiglietto,
                    this.getUsernameUtente()
            );

            if (rows == 0) {
                JOptionPane.showMessageDialog(null,
                        "Nessuna prenotazione trovata (forse già cancellata?).");
                return false;
            }

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Errore durante la cancellazione della prenotazione:\n" + e.getMessage());
            return false;
        }
    }


    /**
     * Restituisce lo username dell’utente attualmente autenticato.
     *
     * @return username dell’utente corrente
     */
    public String getUsernameUtente() {
            return u.getUsername();
    }

    /**
     * Restituisce l’elenco di tutti i voli.
     *
     * <p>In caso di errore di connessione/lettura mostra un messaggio e restituisce una lista vuota.</p>
     *
     * @return lista (eventualmente vuota) di voli
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
     * Conferma e salva le modifiche ai dati di un volo.
     *
     * <p>Crea un {@link Volo} con i nuovi valori e invoca {@link VoloDAO#updateVolo(Volo)}.
     * Eventuali errori vengono propagati come {@link RuntimeException}.</p>
     *
     * @param codiceVolo            codice del volo
     * @param compagnia             compagnia aerea
     * @param aeroportoOrigine      aeroporto di origine
     * @param aeroportoDestinazione aeroporto di destinazione
     * @param dataDDMMYYYY          data nel formato {@code dd/MM/yyyy}
     * @param orarioHHmm            orario nel formato {@code HH:mm}
     * @param ritardoMinuti         ritardo in minuti
     * @param statoVolo             stato del volo (stringa mappata all’enum)
     * @param gate                  gate assegnato (facoltativo)
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
     * Restituisce i voli (da/per Napoli) ordinati per suffisso numerico del codice.
     *
     * <p>Delega a {@link VoloDAO#getVoliDaPerNapoli()} e converte data/orario a stringa lato DAO.</p>
     *
     * @return lista (eventualmente vuota) di voli
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
     * Restituisce l’elenco dei gate disponibili.
     *
     * @return lista (eventualmente vuota) di etichette/numero gate
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

    /**
     * Restituisce le prenotazioni dell’utente autenticato (con volo associato).
     *
     * <p>Delega a {@link PrenotazioneDAO#getPrenotazioniUtente(String)} usando lo username corrente.</p>
     *
     * @return lista (eventualmente vuota) di prenotazioni
     */
    public List<Prenotazione> getPrenotazioniUtente() {

        PrenotazioneDAO prenotazioneDAO=null;

        //Connessione al db
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            prenotazioneDAO = new PrenotazioneDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }

        List<Prenotazione> resultDB= null;
        try {

            resultDB = prenotazioneDAO.getPrenotazioniUtente(this.getUsernameUtente());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultDB;

    }//Fine Parentesi getPrenotazioniUtente

}//Fine parentesi Finale
