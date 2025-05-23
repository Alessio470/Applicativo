package controller;

import model.*;

import java.util.ArrayList;


public class Controller {
    ArrayList<Utente> utentiRegistratiRef;
    String username;

    public Controller(ArrayList<Utente> utentiRegistrati) {
this.utentiRegistratiRef = utentiRegistratiRef;

    }

    public String getUsername() {return username;}


    public static boolean loginValido(String username, String password) {
            for (Utente u : utentiRegistratiRef) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    this.username=username;
                    return true;
                }
            }
            return false;
        }


    }



