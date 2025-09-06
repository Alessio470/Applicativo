package controller;

import model.*;
import model.enums.StatoVolo;
import model.dao.interfaces.VoloDAO;
import model.dao.interfaces.PrenotazioneDAO;
import model.dao.implementations.VoloImplDAO;
import model.dao.implementations.PrenotazioneImplDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller centrale del sistema.
 */
public class Controller {

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
}
