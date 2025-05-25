package controller;

import model.*;
import model.enums.StatoVolo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private ArrayList<Utente> utentiRegistratiRef;
    private ArrayList<Volo> voliRegistratiRef;

    private String username;
    private UtenteGenerico utenteLoggin;
    private UtenteAmministratore utenteAmministratore;

    public Controller(ArrayList<Utente> utentiRegistrati, ArrayList<Volo> voliRegistrati) {
        this.utentiRegistratiRef = utentiRegistrati;
        this.voliRegistratiRef = voliRegistrati;
    }

    public boolean loginValido(String username, String password) {

        this.utenteLoggin = null;
        this.utenteAmministratore = null;


        for (Utente u : utentiRegistratiRef) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                if (u instanceof UtenteGenerico) {this.utenteLoggin = (UtenteGenerico) u; }
                else if (u instanceof UtenteAmministratore){ this.utenteAmministratore = (UtenteAmministratore) u; }
                return true;

            }

        }
        return false;
    }


    public String userType() {
        if (utenteAmministratore != null) return "Admin";
        else if (utenteLoggin != null) return "Generico";

        else return "Nessun utente loggato";
    }


    public String getUsernameAdmin() {
        return (utenteAmministratore != null) ? utenteAmministratore.getUsername() : null;
    }


    public String getUsernameGenerico() {
        return (utenteLoggin != null) ? utenteLoggin.getUsername() : null;
    }


    public List<Prenotazione> getPrenotazioniUtenteGenerico() {
        if (utenteLoggin != null) {
            return utenteLoggin.getPrenotazioniL();
        } else {
            return new ArrayList<>();
        }
    }

    public void aggiungiVolo(Volo volo) {
        utenteAmministratore.inserisciVolo(volo);
    }

    public ArrayList<Volo> getVoliAmministratore() {
        return utenteAmministratore.getVoliGestiti();
    }

    public ArrayList<Volo> getVoli() {
        return voliRegistratiRef;
    }

    public ArrayList<Volo> getVoliAUtente() {
    ArrayList<Volo> voliReturn = new ArrayList<>();

    for(Volo v : voliRegistratiRef){
        if(v.getStato()==StatoVolo.PROGRAMMATO || v.getStato() == StatoVolo.IN_RITARDO){
            voliReturn.add(v);
        }
    }

        return voliReturn;
    }

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
