package gui;

import controller.Controller;
import model.Prenotazione;
import model.Volo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The type Prenotazioni utente.
 */
public class PrenotazioniUtente extends JFrame {
    private JPanel contentPane;
    private JTable table1;
    private JButton buttonTornaIndietro;
    private JLabel labelTesto;
private Controller controller;

    /**
     * Instantiates a new Prenotazioni utente.
     *
     * @param controller     the controller
     * @param frameChiamante the frame chiamante
     */
    public PrenotazioniUtente(Controller controller, JFrame frameChiamante) {
        this.controller = controller;

        setTitle("Le tue prenotazioni");
        setContentPane(contentPane);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Titolo della finestra sopra la tabella
        labelTesto.setText("Lista voli prenotati");

        // Intestazioni delle colonne
        String[] colonne = {
                "Biglietto", "Nome", "Cognome", "Codice Fiscale", "Posto", "Stato", "Codice Volo"
        };

        DefaultTableModel model = new DefaultTableModel(colonne, 0);

        // Popola la tabella con le prenotazioni dell'utente loggato
        for (Prenotazione p : controller.getPrenotazioniUtenteGenerico()) {
            Volo v = p.getVolo();
            model.addRow(new Object[]{
                    p.getNumeroBiglietto(),
                    p.getNomePasseggero(),
                    p.getCognomePasseggero(),
                    p.getCodiceFiscalePasseggero(),
                    p.getNumeroPosto(),
                    p.getStatoPrenotazione(),
                    (v != null ? v.getCodiceVolo() : "N/A")
            });
        }

        table1.setModel(model);

        // Bottone per tornare alla homepage utente
        buttonTornaIndietro.addActionListener(e -> {
            frameChiamante.setVisible(true);
            dispose();
        });
    }
}
