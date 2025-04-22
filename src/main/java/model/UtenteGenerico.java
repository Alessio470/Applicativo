package model;

import java.util.ArrayList;
import java.util.List;

public class UtenteGenerico extends Utente {
    //Lista delle prenotazioni effettuate dall'utente.
    private List<Prenotazione> prenotazioni;

    //Costruttore, username e password ereditati dalla superclasse Utente.
    public UtenteGenerico(String username, String password) {
        super(username, password);
        this.prenotazioni = new ArrayList<>();
    }

    //Metodo per effettuare una nuova prenotazione
    public void prenotaVolo(Volo volo, Prenotazione prenotazione) {
        prenotazioni.add(prenotazione);
        System.out.println("Prenotazione effettuata per volo " + volo.getCodiceVolo());
    }

    //Metodo per simulare la modifica di una prenotazione esistente
    public void modificaPrenotazione(Prenotazione prenotazione) {
        System.out.println("Prenotazione modificata: " + prenotazione.getNumeroBiglietto());
        //TODO
    }

    //Metodo per cercare una prenotazione all'interno della lista in base al "criterio" ovvero numeroBiglietto o nomePasseggero
    public Prenotazione cercaPrenotazione(String criterio) {
        for (Prenotazione p : prenotazioni) {
            if (p.getNumeroBiglietto().equals(criterio) || p.getNomePasseggero().equals(criterio)) {
                return p;
            }
        }
        return null;
    }

    //Metodo per visualizzare tutte le prenotazioni effettuate dall'utente
    public void visualizzaPrenotazioni() {
        for (Prenotazione p : prenotazioni) {
            System.out.println(p);
        }
    }
}
