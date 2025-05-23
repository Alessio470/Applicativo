package controller;

import model.Utente;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Utente> utentiRegistratiRef;
    private String username;

    public Controller(ArrayList<Utente> utentiRegistrati) {
        this.utentiRegistratiRef = utentiRegistrati;
    }

    public boolean loginValido(String username, String password) {
        for (Utente u : utentiRegistratiRef) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                this.username = username; // salva l'utente che ha fatto login
                return true;
            }
        }
        return false;
    }

    public String getUsername() {
        return username;
    }
}
