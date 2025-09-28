package model;

import model.enums.RuoloUtente;


public class UtenteAmministratore extends Utente {


    public UtenteAmministratore(String username, String password) {
        super(username, password);

        this.setRuolo(RuoloUtente.AMMINISTRATORE);
    }


//TODO
    public void modificaGate() {  }

    @Override
    public String toString() {
        return "UtenteAmministratore{" + getUsername() + "}";
    }
}
