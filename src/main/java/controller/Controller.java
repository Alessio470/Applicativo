package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private ArrayList<Utente> utentiRegistratiRef;
    private ArrayList<Volo> VoliRef;

    private String username;
    private UtenteGenerico utenteLoggin;
    private UtenteAmministratore utenteAmministratore;

    public Controller(ArrayList<Utente> utentiRegistrati, ArrayList<Volo> Voli) {
        this.utentiRegistratiRef = utentiRegistrati;
        this.VoliRef = Voli;
    }

    public boolean loginValido(String username, String password) {
        for (Utente u : utentiRegistratiRef) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {

                if (u instanceof UtenteGenerico) {this.utenteLoggin = (UtenteGenerico) u;}
                else if (u instanceof UtenteAmministratore) {this.utenteAmministratore = (UtenteAmministratore) u;}

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

    public String getUsernameAdmin(){
        return utenteAmministratore.getUsername();
    }

    public String getUsernameGenerico(){
        return utenteLoggin.getUsername();
    }
    /*

    public List<Prenotazione> getPrenotazioniUtenteGenerico() {
        if (utenteLoggin != null) {
            return utenteLoggin.getPrenotazioni();
        } else {
            return new ArrayList<>();
        }
    }

     */

    public void aggiungiVolo(Volo volo) {
        VoliRef.add(volo);
    }

    public ArrayList<Volo> getVoli() {
        return VoliRef;
    }


}
