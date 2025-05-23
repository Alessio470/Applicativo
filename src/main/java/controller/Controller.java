package controller;

import model.*;

import java.util.ArrayList;


public class Controller {
    ArrayList<Utente> utentiRegistratiRef;

    public Controller(ArrayList<Utente> utentiRegistrati) {
this.utentiRegistratiRef = utentiRegistratiRef;

    }


    public static boolean loginValido(String username, String password) {
        return username.equals("a") && password.equals("a");
    }
}


