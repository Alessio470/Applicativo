package controller;


import dao.GateDAO;
import dao.PrenotazioneDAO;
import dao.implementazioniPostgresDAO.PrenotazioneImplementazionePostgresDAO;
import dao.implementazioniPostgresDAO.GateImplementazionePostgresDAO;

import model.Prenotazione;
import model.Utente;
import model.UtenteAmministratore;
import model.UtenteGenerico;
import model.Volo;
import model.enums.*;

import dao.UtenteDAO;
import dao.implementazioniPostgresDAO.UtenteImplementazionePostgresDAO;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import dao.VoloDAO;
import dao.implementazioniPostgresDAO.VoloImplementazionePostgresDAO;


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
     * Autentica un utente sulla base delle credenziali fornite.
     *
     * <p>Il risultato dell’autenticazione viene restituito al login,
     * che provvede ad aggiornare l’interfaccia grafica e a mostrare la schermata appropriata.</p>
     *
     * @param user username inserito
     * @param pass password inserita
     * @return il ruolo dell’utente autenticato, oppure {@code null} se le credenziali non sono valide
     * @throws SQLException in caso di errore di accesso al database
     */
    public RuoloUtente doLogin(String user, String pass) throws SQLException {

        UtenteDAO utenteDAO = new UtenteImplementazionePostgresDAO();

        try {
            Utente logged = utenteDAO.login(user, pass);

            if (logged == null) {
                return null; // credenziali errate
            }

            this.u = logged;
            return logged.getRuolo();

        } catch (NullPointerException ex) {
            return null;
        }
    }



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

        // Connessione DB
        try {
            utenteDAO = new UtenteImplementazionePostgresDAO();
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

        RuoloUtente ruoloEnum;
        try {
            ruoloEnum = RuoloUtente.valueOf(ruolo);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, "Ruolo non valido.");
            return;
        }

        //REGISTRAZIONE UTENTE
        Utente utenteRegistrato = null;

        if (ruoloEnum == RuoloUtente.GENERICO) {
            utenteRegistrato = new UtenteGenerico(username, password);
        } else if (ruoloEnum == RuoloUtente.AMMINISTRATORE) {
            utenteRegistrato = new UtenteAmministratore(username, password);
        } else {
            JOptionPane.showMessageDialog(frame, "Ruolo utente non riconosciuto.");
            return;
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
            voloDAO = new VoloImplementazionePostgresDAO();
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
     * @return {@code List<Volo>} lista (eventualmente vuota) di voli prenotabili
     */
    public List<Volo> getVoliPrenotabili() {

        List<Volo> resultDB = new ArrayList<>();
        VoloDAO voloDAO=null;

        //Connessione al db
        try {
            voloDAO = new VoloImplementazionePostgresDAO();
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
     * <p>Genera l’oggetto {@link Prenotazione} con stato CONFERMATA e
     * lo inserisce tramite {@link PrenotazioneDAO#inserisciPrenotazione(Prenotazione)}.</p>
     *
     * @param codiceVolo              il codice volo
     * @param nomePasseggero          il nome del passeggero
     * @param cognomePasseggero       il cognome del passeggero
     * @param posto                   il codice del posto
     * @param codicefiscalepasseggero il codice fiscale del passeggero
     */
    public void effettuaPrenotazione(String codiceVolo, String nomePasseggero, String cognomePasseggero, String posto, String codicefiscalepasseggero) {

        // NB: il numero biglietto è generato dal DB (sequenza), quindi lo passiamo come null
        int stato = StatoPrenotazione.CONFERMATA.ordinal() + 1; // coerente con tuo uso altrove

        Prenotazione prenotazione = new Prenotazione(null, this.getUsernameUtente(), codiceVolo, nomePasseggero, cognomePasseggero, posto, stato, codicefiscalepasseggero);

        try {
            PrenotazioneDAO prenotazioneDAO = new PrenotazioneImplementazionePostgresDAO();
            prenotazioneDAO.inserisciPrenotazione(prenotazione);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore durante l'inserimento della prenotazione:\n" + ex.getMessage());
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
            prenotazioneDAO = new PrenotazioneImplementazionePostgresDAO();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());return false;
        }

        try {
            int rows = prenotazioneDAO.cancellaPrenotazione(
                    numeroBiglietto,
                    this.getUsernameUtente()
            );

            if (rows == 0) {
                JOptionPane.showMessageDialog(null, "Nessuna prenotazione trovata (forse già cancellata?).");
                return false;
            }

            return true; //Prenotazione cancellata con successo.

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante la cancellazione della prenotazione:\n" + e.getMessage());
            return false;
        }
    }


    /**
     * Restituisce lo username dell’utente attualmente autenticato.
     *
     * @return String username dell’utente corrente
     */
    public String getUsernameUtente() {
            return u.getUsername();
    }

    /**
     * Restituisce l’elenco di tutti i voli.
     *
     * <p>In caso di errore di connessione/lettura mostra un messaggio e restituisce una lista vuota.</p>
     *
     * @return {@code List<Volo>} lista (eventualmente vuota) di voli
     */
    public List<Volo> getVoli() {
        List<Volo> resultDB = new ArrayList<>();
        VoloDAO voloDAO=null;


        //Connessione al db
        try {
            voloDAO = new VoloImplementazionePostgresDAO();
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
            voloDAO = new VoloImplementazionePostgresDAO();
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
     * @return {@code List<Volo>} lista (eventualmente vuota) di voli
     */
    public List<Volo> getVoliDaPerNapoli() {

       DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/uuuu");
       DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");
       VoloDAO voloDAO=null;


        List<Volo> resultDB = new ArrayList<>();

        //Connessione al db
        try {
            voloDAO = new VoloImplementazionePostgresDAO();
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
     * @return {@code List<String>} lista (eventualmente vuota) di etichette/numero gate
     */
    public List<String> getGates() {

            GateDAO gateDAO =null;

        //Connessione al db
        try {
            gateDAO = new GateImplementazionePostgresDAO();
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
     * @return {@code List<Prenotazione>} lista (eventualmente vuota) di prenotazioni
     */
    public List<Prenotazione> getPrenotazioniUtente() {

        PrenotazioneDAO prenotazioneDAO=null;

        //Connessione al db
        try {
            prenotazioneDAO = new PrenotazioneImplementazionePostgresDAO();
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


    /**
     * Cerca i voli in base a diversi criteri.
     *
     * @param codiceVolo            Il codice del volo da cercare.
     * @param compagnia             La compagnia aerea del volo.
     * @param aeroportoOrigine      L'aeroporto di origine del volo.
     * @param aeroportoDestinazione L'aeroporto di destinazione del volo.
     * @param data                  La data del volo.
     * @param orario                L'orario del volo.
     * @param gate                  Il gate del volo.
     * @return {@code List<Volo>} una lista di voli che corrispondono ai criteri di ricerca.
     */
    public List<Volo> cercaVoli(String codiceVolo, String compagnia, String aeroportoOrigine, String aeroportoDestinazione, String data, String orario, String gate) {
        List<Volo> resultDB = new ArrayList<>();
        VoloDAO voloDAO = null;

        //Connessione al db
        try {
            voloDAO = new VoloImplementazionePostgresDAO();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }

        // Create a Volo object with the search criteria, setting null for empty strings
        Volo criteriVolo = new Volo(
                codiceVolo != null && !codiceVolo.trim().isEmpty() ? codiceVolo.trim() : null,
                compagnia != null && !compagnia.trim().isEmpty() ? compagnia.trim() : null,
                aeroportoOrigine != null && !aeroportoOrigine.trim().isEmpty() ? aeroportoOrigine.trim() : null,
                aeroportoDestinazione != null && !aeroportoDestinazione.trim().isEmpty() ? aeroportoDestinazione.trim() : null,
                data != null && !data.trim().isEmpty() ? data.trim() : null,
                orario != null && !orario.trim().isEmpty() ? orario.trim() : null,
                0, // Default value for ritardoMinuti
                null, // Default value for stato, as it's not a search criterion here
                gate != null && !gate.trim().isEmpty() ? gate.trim() : null
        );

        try {
            resultDB = voloDAO.cercaVoli(criteriVolo);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante la ricerca voli:\n" + e.getMessage());
        }

        return resultDB;
    }


    /**
     * Cerca le prenotazioni in base ai criteri specificati.
     *
     * @param p L'oggetto Prenotazione con i criteri di ricerca.
     * @return {@code List<Prenotazione>} una lista di prenotazioni che corrispondono ai criteri di ricerca.
     */
    public List<Prenotazione> getCercaPrenotazioni(Prenotazione p) {

        PrenotazioneDAO prenotazioneDAO=null;

        //Connessione al db
        try {
            prenotazioneDAO = new PrenotazioneImplementazionePostgresDAO();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }

        List<Prenotazione> resultDB= null;
        try {

            resultDB = prenotazioneDAO.getPrenotazioniUtenteSearch(p);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultDB;

    }//Fine Parentesi getPrenotazioniUtente

}//Fine parentesi Finale
