package model;

public class UtenteAmministratore extends Utente {

    //Costruttore, username e password ereditati dalla superclasse Utente.
    public UtenteAmministratore(String username, String password) {
        super(username, password);
    }

    //Metodo per inserire un nuovo volo nel sistema
    public void inserisciVolo(Volo volo) {
        System.out.println("Volo inserito: " + volo.getCodiceVolo());
        //TODO
    }

    //Metodo per aggiornare le informazioni di un volo esistente
    public void aggiornaVolo(Volo volo) {
        System.out.println("Volo aggiornato: " + volo.getCodiceVolo());
        //TODO
    }

    //Metodo per modificare l'assegnazione del gate a un volo in partenza da Napoli
    public void modificaGate(VoloPartenzaDaNapoli volo, Gate nuovoGate) {
        volo.setGate(nuovoGate);
        System.out.println("Gate modificato per volo: " + volo.getCodiceVolo());
    }
}
