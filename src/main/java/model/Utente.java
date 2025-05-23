package model;

public class Utente {
    protected String username;
    protected String password;

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void visualizzaVoli() {
        System.out.println("Visualizzazione voli disponibile.");
    }
}