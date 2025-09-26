package model;

import model.enums.RuoloUtente;
import java.util.ArrayList;
import java.util.List;

public class UtenteAmministratore extends Utente {


    public UtenteAmministratore(String username, String password) {
        super(username, password, RuoloUtente.AMMINISTRATORE);
    }


//TODO
    public void modificaGate() {  }

    @Override
    public String toString() {
        return "UtenteAmministratore{" + getUsername() + "}";
    }
}
