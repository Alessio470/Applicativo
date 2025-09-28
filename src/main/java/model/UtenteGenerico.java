package model;

import model.enums.RuoloUtente;


public class UtenteGenerico extends Utente {

    public UtenteGenerico(String username, String password) {
        super(username, password);
        this.setRuolo(RuoloUtente.GENERICO);

    }




    @Override
    public String toString() {
        return "UtenteGenerico{" + getUsername() + "}";
    }
}
