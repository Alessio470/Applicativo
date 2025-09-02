package controller;

import model.*;
import model.enums.StatoVolo;
import model.dao.interfaces.VoloDAO;
import model.dao.interfaces.PrenotazioneDAO;
import model.dao.implementations.VoloPostgresDAO;
import model.dao.implementations.PrenotazionePostgresDAO;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Controller.
 */
public class Controller {
    private ArrayList<Utente> utentiRegistratiRef;
    private ArrayList<Volo> voliRegistratiRef;


    private UtenteGenerico utenteLoggin;
    private UtenteAmministratore utenteAmministratore;
    private VoloDAO voloDAO;
    private PrenotazioneDAO prenotazioneDAO;

    /**
     * Instantiates a new Controller.
     *
     * @param utentiRegistrati the utenti registrati
     * @param voliRegistrati   the voli registrati
     */
    public Controller(ArrayList<Utente> utentiRegistrati, ArrayList<Volo> voliRegistrati) {
        this.utentiRegistratiRef = utentiRegistrati;
        this.voliRegistratiRef = voliRegistrati;

        try {
            this.voloDAO = new VoloPostgresDAO();
            this.prenotazioneDAO = new PrenotazionePostgresDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nella connessione al database dei voli: " + e.getMessage());
        }

    }

    /**
     * Login valido boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     */
    public boolean loginValido(String username, String password) {

        this.utenteLoggin = null;
        this.utenteAmministratore = null;


        for (Utente u : utentiRegistratiRef) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                if (u instanceof UtenteGenerico) {
                    this.utenteLoggin = (UtenteGenerico) u;
                } else if (u instanceof UtenteAmministratore){
                    this.utenteAmministratore = (UtenteAmministratore) u;
                }
                return true;
            }
        }
        return false;
    }


    /**
     * User type string.
     *
     * @return the string
     */
    public String userType() {
        if (utenteAmministratore != null) return "Admin";
        else if (utenteLoggin != null) return "Generico";
        else return "Nessun utente loggato";
    }


    /**
     * Gets username admin.
     *
     * @return the username admin
     */
    public String getUsernameAdmin() {
        return (utenteAmministratore != null) ? utenteAmministratore.getUsername() : null;
    }


    /**
     * Gets username generico.
     *
     * @return the username generico
     */
    public String getUsernameGenerico() {
        return (utenteLoggin != null) ? utenteLoggin.getUsername() : null;
    }


    /**
     * Gets prenotazioni utente generico.
     *
     * @return the prenotazioni utente generico
     */
    public List<Prenotazione> getPrenotazioniUtenteGenerico() {
        if (utenteLoggin != null) {
            try {
                // qui uso il codice fiscale come identificativo univoco
                return prenotazioneDAO.getPrenotazioniPerUtente(utenteLoggin.getPrenotazioniL().isEmpty()
                        ? "" // fallback se l'utente non ha ancora prenotato nulla
                        : utenteLoggin.getPrenotazioniL().get(0).getCodiceFiscalePasseggero());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Errore durante il recupero delle prenotazioni: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    public void aggiungiPrenotazione(Prenotazione p) {
        try {
            prenotazioneDAO.inserisciPrenotazione(p);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore durante il salvataggio della prenotazione: " + e.getMessage());
        }
    }


    /**
     * Aggiungi volo.
     *
     * @param volo the volo
     */
    public void aggiungiVolo(Volo volo) {
        try {
            voloDAO.inserisciVolo(volo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nell'inserimento del volo: " + e.getMessage());
        }
    }

    /**
     * Gets voli amministratore.
     *
     * @return the voli amministratore
     */
    public ArrayList<Volo> getVoliAmministratore() {
        return utenteAmministratore.getVoliGestiti();
    }

    /**
     * Gets voli.
     *
     * @return the voli
     */
    public ArrayList<Volo> getVoli() {
        try {
            return new ArrayList<>(voloDAO.leggiTuttiIVoli());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nella lettura dei voli: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Gets voli a utente.
     *
     * @return the voli a utente
     */
    public ArrayList<Volo> getVoliAUtente() {
    ArrayList<Volo> voliReturn = new ArrayList<>();

    for(Volo v : voliRegistratiRef){
        if(v.getStato()==StatoVolo.PROGRAMMATO || v.getStato() == StatoVolo.IN_RITARDO){
            voliReturn.add(v);
        }
    }

        return voliReturn;
    }

    /**
     * Aggiorna volo.
     *
     * @param nuovoVolo the nuovo volo
     */
    public void aggiornaVolo(Volo nuovoVolo) {
        if(utenteAmministratore != null){
            utenteAmministratore.inserisciVolo(nuovoVolo);
        }
    }

    /**
     * Modifica gate.
     *
     * @param volo the volo
     * @param gate the gate
     */
    public void modificaGate(VoloPartenzaDaNapoli volo, Gate gate) {
        if(utenteAmministratore != null){
            utenteAmministratore.modificaGate(volo, gate);
        }
    }


    /**
     * Gets modello tabella voli.
     *
     * @return the modello tabella voli
     */
    public DefaultTableModel getModelloTabellaVoli() {


        String[] colonne = {
                "Codice Volo", "Compagnia Aerea", "Data Volo",
                "Orario Previsto", "Ritardo", "Stato"
        };
        DefaultTableModel model = new DefaultTableModel(colonne, 0);


        ArrayList<Volo> listaVoli;

        if(utenteAmministratore!=null){
            listaVoli = utenteAmministratore.getVoliGestiti();
        }else {
            listaVoli = new ArrayList<>();
        }


        for (Volo v : listaVoli) {
            Object[] riga = {
                    v.getCodiceVolo(),
                    v.getCompagniaAerea(),
                    v.getDataVolo(),
                    v.getOrarioPrevisto(),
                    v.getRitardo(),
                    (v.getStato() != null ? v.getStato().toString() : "N/A")
            };
            model.addRow(riga);
        }

        return model;

    }

}
