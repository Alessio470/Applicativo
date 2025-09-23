package controller;

import gui.HomeUtenteGenerico;
import gui.HomepageAmministratore;
import model.*;
import model.enums.StatoVolo;

import DAO.UtenteDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import database.ConnessioneDatabase;
import model.enums.*;
import DAO.UtenteDAO;
import database.ConnessioneDatabase;
import model.Utente;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Controller centrale del sistema.
 */
public class Controller {

    private UtenteDAO utenteDAO;

        public Controller() {
        }


    public void doLogin(String user, String pass, JFrame frame) {


        try {
            Connection conn = database.ConnessioneDatabase.getInstance().getConnection();
            utenteDAO = new DAO.UtenteDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }


        try {
            Utente u = utenteDAO.login(user, pass);
            if (u == null) {
                JOptionPane.showMessageDialog(frame, "Credenziali non valide");
                return;
            }

            if (u.getRuolo() == RuoloUtente.AMMINISTRATORE) {
                HomepageAmministratore admin = new HomepageAmministratore(frame);
                admin.setLocationRelativeTo(frame);
                admin.setVisible(true);
                frame.setVisible(false);
            } else {
                HomeUtenteGenerico home = new HomeUtenteGenerico(frame);
                home.setLocationRelativeTo(frame);
                home.setVisible(true);
                frame.setVisible(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore durante il login:\n" + ex.getMessage());
        }
    }


    //DA FINIRE
    public void onRegistrati(String username, String password, String conferma,String ruolo, JFrame frame ) {


        // --- Validazioni base ---
        if (username.isEmpty() || password.isEmpty() || conferma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Compila tutti i campi.");
            return;
        }
        if (!password.equals(conferma)) {
            JOptionPane.showMessageDialog(this, "Le password non coincidono.");
            return;
        }
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this, "Username troppo corto (min 3).");
            return;
        }
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password troppo corta (min 4).");
            return;
        }

        try {
            // Controllo unicità username
            if (utenteDAO.usernameExists(username)) {
                JOptionPane.showMessageDialog(this, "Username già in uso.");
                return;
            }

            utenteDAO.registraUtente(username, password, ruolo);

            JOptionPane.showMessageDialog(
                    this,
                    "Registrazione completata!\nUsername: " + username + "\nRuolo: " + ruolo,
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // torna al login
            dispose();
            if (loginFrame != null) {
                loginFrame.setVisible(true);
                loginFrame.toFront();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore durante la registrazione:\n" + ex.getMessage());
        }
    }//Parentesi onRegistrati

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

    public UtenteGenerico getUtenteLoggato() { return utenteLoggato; }
    public UtenteAmministratore getAdminLoggato() { return adminLoggato; }

    // --- PRENOTAZIONI ---
    public List<Prenotazione> getPrenotazioniUtenteLoggato() {
        if (utenteLoggato != null) {
            return utenteLoggato.getPrenotazioni();
        }
        return new ArrayList<>();
    }

    public void aggiungiPrenotazione(Prenotazione p) {
        try {
            prenotazioneDAO.inserisciPrenotazione(p);
            if (utenteLoggato != null) utenteLoggato.prenotaVolo(p);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore inserimento prenotazione: " + e.getMessage());
        }
    }

    public void eliminaPrenotazione(Prenotazione p) {
        try {
            prenotazioneDAO.eliminaPrenotazione(p.getId());
            if (utenteLoggato != null) utenteLoggato.eliminaPrenotazione(p);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore eliminazione prenotazione: " + e.getMessage());
        }
    }

    // --- VOLI ---
    public void aggiungiVolo(Volo v) {
        try {
            voloDAO.inserisciVolo(v);
            if (adminLoggato != null) adminLoggato.inserisciVolo(v);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore inserimento volo: " + e.getMessage());
        }
    }

    public List<Volo> getVoli() {
        try {
            return voloDAO.leggiTuttiIVoli();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore lettura voli: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Volo> getVoliDisponibiliPerUtente() {
        List<Volo> result = new ArrayList<>();
        for (Volo v : voliRegistratiRef) {
            if (v.getStato() == StatoVolo.PROGRAMMATO || v.getStato() == StatoVolo.IN_RITARDO) {
                result.add(v);
            }
        }
        return result;
    }

    public void aggiornaVolo(Volo volo) {
        try {
            voloDAO.aggiornaVolo(volo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore aggiornamento volo: " + e.getMessage());
        }
    }

    public void modificaGate(Volo volo, Integer numeroGate) {
        if (adminLoggato != null) {
            volo.setNumeroGate(numeroGate);
            aggiornaVolo(volo);
        }
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
