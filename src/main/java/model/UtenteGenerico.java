package model;

import model.enums.RuoloUtente;
import java.util.ArrayList;
import java.util.List;

public class UtenteGenerico extends Utente {

    public UtenteGenerico(String username, String password) {
        super(username, password, RuoloUtente.GENERICO);

    }




    @Override
    public String toString() {
        return "UtenteGenerico{" + getUsername() + "}";
    }
}
