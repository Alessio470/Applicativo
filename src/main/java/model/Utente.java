package model;

/**
 * The type Utente.
 */
public class Utente {
    /**
     * The Username.
     */
    protected String username;
    /**
     * The Password.
     */
    protected String password;

    /**
     * Instantiates a new Utente.
     *
     * @param username the username
     * @param password the password
     */
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Visualizza voli.
     */
    public void visualizzaVoli() {
        System.out.println("Visualizzazione voli disponibile.");
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {return username;}

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {return password;}
}