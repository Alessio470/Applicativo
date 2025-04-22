package model;

public abstract class Utente {
    protected String username;
    protected String password;

    //Costruttore
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void visualizzaVoli() {
        // Mostrare lista voli
        System.out.println("Visualizzazione voli disponibile.");
        //TODO
    }
}