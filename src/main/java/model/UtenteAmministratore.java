package model;

public class UtenteAmministratore extends Utente {
    public UtenteAmministratore(String username, String password) {
        super(username, password);
    }

    //TODO
    @Override
    public void visualizzaVoli() {
        System.out.println("Visualizzazione voli per amministratore.");
    }

    //TODO
    public void inserisciVolo() {
        System.out.println("Inserimento nuovo volo.");
    }

    //TODO
    public void aggiornaVolo() {
        System.out.println("Aggiornamento volo effettuato.");
    }

    // Metodo per modificare il gate di imbarco del volo
    public void modificaGate(VoloPartenzaDaNapoli volo, Gate gate) {
        volo.setGate(gate);
        System.out.println("Gate modificato: " + gate.getNumeroGate());
    }
}